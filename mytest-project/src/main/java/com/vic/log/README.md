编译时：PluginProcessor: 处理所以有@Plugin的类，保存在 "META-INF/org/apache/logging/log4j/core/config/plugins/Log4j2Plugins.dat"目录下
        启动的时候用加载器，扫描所有jar包下的 META-INF/文件 找到这个 .dat。优势加快启动时间。


启动时：
普通main函数启动， static Logger log = LogManager.getLogger(XXX.class); 静态的 开始启动 log4j2

LogManager#getContext(final boolean currentContext)

        ILoggerFactory
        通过 selector 选择合适的 LoggerContext，会先 new 一个 默认的 LoggerContext 及 默认属性 DefaultConfiguration
        -> Log4jContextFactory#getContext(final String fqcn, final ClassLoader loader, final Object externalContext,
                                            final boolean currentContext, final URI configLocation, final String name)

        用默认的 LoggerContext 去start 加载配置
        -> LoggerContext#start()
        -> LoggerContext#reconfigure(final URI configURI)，configURI ==null,得到log4j2的 XmlConfiguration类，并继续下去 -> setConfiguration(final Configuration config)

            解析成配置类
            -> XmlConfiguration#start()
              根据xml 把每个节点配置成对象#递归】
            -> XmlConfiguration#doConfigure() -> createConfiguration(child, null); -> node

        -> firePropertyChangeEvent ->  Server.reregisterMBeansAfterReconfigure();



运行时：根据 strategy 选择合适 LoggerConfig -> LoggerConfig#log() -> LoggerConfig#processLogEvent()

学习：
1）找栈的方式 ，通过 fqcn [The fully qualified class name of the caller.]
MutableLogEvent#getSource()

need learn point：
LoggerContext#updateLoggers();
LoggerContext#onChange(final Reconfigurable reconfigurable);
