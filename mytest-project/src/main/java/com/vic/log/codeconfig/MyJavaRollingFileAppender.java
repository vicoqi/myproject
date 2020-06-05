package com.vic.log.codeconfig;

import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.appender.AbstractOutputStreamAppender;
import org.apache.logging.log4j.core.appender.RollingFileAppender;
import org.apache.logging.log4j.core.appender.rolling.*;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderFactory;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.zip.Deflater;

/**
 * @description:
 * @author: wangqp
 * @create: 2020-05-06 18:07
 **/
//@Plugin(name = MyJavaRollingFileAppender.PLUGIN_NAME, category = Core.CATEGORY_NAME, elementType = Appender.ELEMENT_TYPE, printObject = true)
public class MyJavaRollingFileAppender extends AbstractOutputStreamAppender<RollingFileManager> {

    public static final String PLUGIN_NAME = "MyJavaRollingFile";


    private final String fileName;
    private final String filePattern;

    public static class Builder<B extends MyJavaRollingFileAppender.Builder<B>> extends AbstractOutputStreamAppender.Builder<B>
            implements org.apache.logging.log4j.core.util.Builder<MyJavaRollingFileAppender> {

        private String fileName;

        @Required
        private String filePattern;

        private boolean append = true;

        private boolean locking;

        @Required
        private TriggeringPolicy policy;

        private RolloverStrategy strategy;

        private boolean advertise;

        private String advertiseUri;

        private boolean createOnDemand;

        private String filePermissions;

        private String fileOwner;

        private String fileGroup;

        @Override
        public MyJavaRollingFileAppender build() {
            // Even though some variables may be annotated with @Required, we must still perform validation here for
            // call sites that build builders programmatically.
            final boolean isBufferedIo = isBufferedIo();
            final int bufferSize = getBufferSize();
            if (getName() == null) {
                LOGGER.error("RollingFileAppender '{}': No name provided.", getName());
                return null;
            }

            if (!isBufferedIo && bufferSize > 0) {
                LOGGER.warn("RollingFileAppender '{}': The bufferSize is set to {} but bufferedIO is not true", getName(), bufferSize);
            }

            if (filePattern == null) {
                LOGGER.error("RollingFileAppender '{}': No file name pattern provided.", getName());
                return null;
            }

            if (policy == null) {
                LOGGER.error("RollingFileAppender '{}': No TriggeringPolicy provided.", getName());
                return null;
            }

            if (strategy == null) {
                if (fileName != null) {
                    strategy = DefaultRolloverStrategy.newBuilder()
                            .withCompressionLevelStr(String.valueOf(Deflater.DEFAULT_COMPRESSION))
                            .withConfig(getConfiguration())
                            .build();
                } else {
                    strategy = DirectWriteRolloverStrategy.newBuilder()
                            .withCompressionLevelStr(String.valueOf(Deflater.DEFAULT_COMPRESSION))
                            .withConfig(getConfiguration())
                            .build();
                }
            } else if (fileName == null && !(strategy instanceof DirectFileRolloverStrategy)) {
                LOGGER.error("RollingFileAppender '{}': When no file name is provided a DirectFilenameRolloverStrategy must be configured");
                return null;
            }

            final Layout<? extends Serializable> layout = getOrCreateLayout();
            final RollingFileManager manager = RollingFileManager.getFileManager(fileName, filePattern, append,
                    isBufferedIo, policy, strategy, advertiseUri, layout, bufferSize, isImmediateFlush(),
                    createOnDemand, filePermissions, fileOwner, fileGroup, getConfiguration());
            if (manager == null) {
                return null;
            }

            manager.initialize();

            return new MyJavaRollingFileAppender(getName(), layout, getFilter(),isIgnoreExceptions(), isImmediateFlush(), manager,
                    fileName, filePattern);
        }

        public String getAdvertiseUri() {
            return advertiseUri;
        }

        public String getFileName() {
            return fileName;
        }

        public boolean isAdvertise() {
            return advertise;
        }

        public boolean isAppend() {
            return append;
        }

        public boolean isCreateOnDemand() {
            return createOnDemand;
        }

        public boolean isLocking() {
            return locking;
        }

        public String getFilePermissions() {
            return filePermissions;
        }

        public String getFileOwner() {
            return fileOwner;
        }

        public String getFileGroup() {
            return fileGroup;
        }

//        public B withAdvertise(final boolean advertise) {
//            this.advertise = advertise;
//            return asBuilder();
//        }
//
//        public B withAdvertiseUri(final String advertiseUri) {
//            this.advertiseUri = advertiseUri;
//            return asBuilder();
//        }

        public B withCreateOnDemand(final boolean createOnDemand) {
            this.createOnDemand = createOnDemand;
            return asBuilder();
        }

        public B withLocking(final boolean locking) {
            this.locking = locking;
            return asBuilder();
        }

        public String getFilePattern() {
            return filePattern;
        }

        public TriggeringPolicy getPolicy() {
            return policy;
        }

        public RolloverStrategy getStrategy() {
            return strategy;
        }

        public B withFilePermissions(final String filePermissions) {
            this.filePermissions = filePermissions;
            return asBuilder();
        }

        public B withFileOwner(final String fileOwner) {
            this.fileOwner = fileOwner;
            return asBuilder();
        }

        public B withFileGroup(final String fileGroup) {
            this.fileGroup = fileGroup;
            return asBuilder();
        }

        public B withFileName(final String fileName) {
            this.fileName = fileName;
            return asBuilder();
        }

        public B withFilePattern(final String filePattern) {
            this.filePattern = filePattern;
            return asBuilder();
        }

        public B withAppend(final boolean append) {
            this.append = append;
            return asBuilder();
        }

        public B withPolicy(final TriggeringPolicy policy) {
            this.policy = policy;
            return asBuilder();
        }

        public B withStrategy(final RolloverStrategy strategy) {
            this.strategy = strategy;
            return asBuilder();
        }

    }

    @PluginBuilderFactory
    public static <B extends MyJavaRollingFileAppender.Builder<B>> B newBuilder() {
        return new MyJavaRollingFileAppender.Builder<B>().asBuilder()
                .withName("MY_APPENDER").withFileName("logs/myAppender.log")
                .withFilePattern("logs/myAppender.log.%d{yyyy-MM-dd-HH}-%i.gz");

    }

    /**
     * Instantiates a WriterAppender and set the output destination to a new {@link OutputStreamWriter}
     * initialized with <code>os</code> as its {@link OutputStream}.
     *
     * @param name             The name of the Appender.
     * @param layout           The layout to format the message.
     * @param filter
     * @param ignoreExceptions
     * @param immediateFlush
     * @param manager          The OutputStreamManager.
     */
    private MyJavaRollingFileAppender(String name, Layout<? extends Serializable> layout, Filter filter, boolean ignoreExceptions
                                        , boolean immediateFlush, RollingFileManager manager
                                        ,final String fileName, final String filePattern) {
        super(name, layout, filter, ignoreExceptions, immediateFlush, manager);
        this.fileName = fileName;
        this.filePattern = filePattern;
    }

    @Override
    public void append(final LogEvent event) {
        getManager().checkRollover(event);
        super.append(event);
    }

    /**
     * Returns the File name for the Appender.
     * @return The file name.
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Returns the file pattern used when rolling over.
     * @return The file pattern.
     */
    public String getFilePattern() {
        return filePattern;
    }

    /**
     * Returns the triggering policy.
     * @param <T> TriggeringPolicy type
     * @return The TriggeringPolicy
     */
    public <T extends TriggeringPolicy> T getTriggeringPolicy() {
        return getManager().getTriggeringPolicy();
    }

}
