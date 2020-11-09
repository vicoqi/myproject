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

                //在这里就把@component等注解扫描成 mdb
                //可以看@【10】
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




@【10】
PostProcessorRegistrationDelegate#invokeBeanFactoryPostProcessors(91)#invokeBeanDefinitionRegistryPostProcessors(currentRegistryProcessors, registry);

通过具体的postProcessor（currentRegistryProcessors == ConfigurationClassPostProcessor）,得到扫描包下等等所有 mdb.

详细步骤如下，以启动类作为configura类，然后扫描得到我们自己的 所有的 beanDefinition （带注解的,包括自定义的postProcessor,需要带注解，要不然扫不到，下一步不起作用）
主要是 ConfigurationClassPostProcessor#postProcessBeanDefinitionRegistry#processConfigBeanDefinitions#parser.parse(candidates);（candidates == 启动类即springStart）->
 ConfigurationClassParser (Parse each @Configuration class)#parse#processConfigurationClass#doProcessConfigurationClass#Set<BeanDefinitionHolder> # scannedBeanDefinitions =  this.componentScanParser.parse(componentScan, sourceClass.getMetadata().getClassName());




AbstractBeanFactory#getBean#doGetBean ->

singletonObject = singletonFactory.getObject(); 回调上面触发下面 ->

AbstractAutowireCapableBeanFactory#createBean#doCreateBean ->

AbstractAutowireCapableBeanFactory#createBeanInstance(beanName, mbd, args) 通过反射生成 bean实例，BeanUtils.instantiateClass(constructorToUse); ->

AbstractAutowireCapableBeanFactory#populateBean  DI,注入实现，通过BeanPostProcessor，defaultListBeanfactory ,主要是 CommonAnnotationBeanPostProcessor#postProcessPropertyValues  ->

AbstractAutowireCapableBeanFactory#initializeBean -> 接口实现类，接口方法afterPropertity

构造函数 = AbstractAutowireCapableBeanFactory#createBeanInstance
接下来，初始化的几步 postProcessBeforeInitialization == @PostConstruct。实现了注入applicationContext的类，是通过(ApplicationContextAwareProcessor#postProcessBeforeInitialization)
-> InitializingBean == InitializingBean
-> postProcessAfterInitialization == 貌似等于@Component之类的注解还是@Resource注入？？？

感悟:
0.理解几个 几个 postProcessor比较重要。前期是几个 BeanFactoryPostProcessor,后期主要是BeanPostProcessor。
1.BeanPostProcessor#postProcessBeforeInitialization,用于提前注入，比如实现了某个接口，然后注入某个类，或者类似@PostConstruct注解，早早的起作用，即构造函数完之后。







