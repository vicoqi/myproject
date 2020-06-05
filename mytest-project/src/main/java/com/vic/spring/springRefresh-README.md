阅读代码：

接口 ApplicationContext 承上启下，

![avator](com/vic/spring/IApplicationContext.png）


#refresh 核心流程

public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext

@Override
	public void refresh() throws BeansException, IllegalStateException {
		synchronized (this.startupShutdownMonitor) {
            ## 准备此上下文以进行刷新，设置其启动日期和活动标志以及执行属性源的任何初始化。
            ###1. 在上下文环境中初始化任何占位符属性源
               2. 验证标记为必需的所有属性都是可解析的，请参见ConfigurablePropertyResolver＃setRequiredProperties
               3. 一旦 multicaster 可用，就允许收集早期的ApplicationEvents进行发布
			prepareRefresh();

            ## 告诉子类刷新内部bean工厂
			### 控制变量保证只有一个 GenericApplicationContext 在刷新,及返回 beanfactory
			ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();
            
            ## 准备 beanFactory 为了使用 context  
			### 为beanfactory 设置各种东西 ** 比较重要，注册或者或者设置了一些东西
			    1）、设置BeanFactory的类加载器、支持表达式解析器...
            	2）、添加部分BeanPostProcessor【ApplicationContextAwareProcessor】
            	3）、设置忽略的自动装配的接口EnvironmentAware、EmbeddedValueResolverAware、xxx；
            	4）、注册可以解析的自动装配；我们能直接在任何组件中自动注入：
            			BeanFactory、ResourceLoader、ApplicationEventPublisher、ApplicationContext
            	5）、添加BeanPostProcessor【ApplicationListenerDetector】
            	6）、添加编译时的AspectJ；
            	7）、给BeanFactory中注册一些能用的组件；
            		environment【ConfigurableEnvironment】、
            		systemProperties【Map<String, Object>】、
            		systemEnvironment【Map<String, Object>】
			prepareBeanFactory(beanFactory);

			try {
				## BeanFactory准备工作完成后进行的后置处理工作；
                	1）、子类通过重写这个方法来在BeanFactory创建并预准备完成以后做进一步的设置
				postProcessBeanFactory(beanFactory);
				

				## Invoke factory processors registered as beans in the context.
				### 执行BeanFactoryPostProcessor的方法；
                	BeanFactoryPostProcessor：BeanFactory的后置处理器。在BeanFactory标准初始化之后执行的；
                	两个接口：BeanFactoryPostProcessor、BeanDefinitionRegistryPostProcessor
                ### ConfigurationClassPostProcessor 解析一些配置的注解或者抽象类 成BeanDefinition，然后注册到 beanFactory 中
                e.g @PropertySource、@ComponentScan、@Import、@ImportResource、 @Bean、
				invokeBeanFactoryPostProcessors(beanFactory);

				// Register bean processors that intercept bean creation.
				registerBeanPostProcessors(beanFactory);

				// Initialize message source for this context.
				initMessageSource();

				// Initialize event multicaster for this context.
				initApplicationEventMulticaster();

				// Initialize other special beans in specific context subclasses.
				onRefresh();

				// Check for listener beans and register them.
				registerListeners();

				// Instantiate all remaining (non-lazy-init) singletons.
				finishBeanFactoryInitialization(beanFactory);

				// Last step: publish corresponding event.
				finishRefresh();
			}

			catch (BeansException ex) {
				if (logger.isWarnEnabled()) {
					logger.warn("Exception encountered during context initialization - " +
							"cancelling refresh attempt: " + ex);
				}

				// Destroy already created singletons to avoid dangling resources.
				destroyBeans();

				// Reset 'active' flag.
				cancelRefresh(ex);

				// Propagate exception to caller.
				throw ex;
			}

			finally {
				// Reset common introspection caches in Spring's core, since we
				// might not ever need metadata for singleton beans anymore...
				resetCommonCaches();
			}
		}
	}



