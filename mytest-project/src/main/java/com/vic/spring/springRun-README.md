
SpringApplication spa = new SpringApplication();


public ConfigurableApplicationContext run(String... args) {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		ConfigurableApplicationContext context = null;
		Collection<SpringBootExceptionReporter> exceptionReporters = new ArrayList<>();
		configureHeadlessProperty();
		
		//spring-boot-autoconfigure 下有配置文件
		//通过 META-INF/spring.factories 获得其中的 SpringApplicationRunListener 的实现类，使用参数构造器反射实例化
		SpringApplicationRunListeners listeners = getRunListeners(args);
		
		//排序之后
		listeners.starting();
		try {
			ApplicationArguments applicationArguments = new DefaultApplicationArguments(
					args);
			ConfigurableEnvironment environment = prepareEnvironment(listeners,
					applicationArguments);
			configureIgnoreBeanInfo(environment);
			Banner printedBanner = printBanner(environment);
			
			//按情况创建三种类型的环境中的一个
		    并注册一些 对象 到 beanFactory中
		    e.g springBootBanner 、springbootarugments
			context = createApplicationContext();
			
			//注册了一个异常分析上报类，到beanfactory中
			exceptionReporters = getSpringFactoriesInstances(
					SpringBootExceptionReporter.class,
					new Class[] { ConfigurableApplicationContext.class }, context);
					
			/*1.把环境参数设置到context中
			  2.如果有的话，设置 beanNameGenerator 或者 resourceLoader 到context中
			  3.context作为参数执行Initializers器，Initializers 来自new SpringApplication 中
			  4.load and create   BeanDefinitionLoader
			*/ 
			prepareContext(context, environment, listeners, applicationArguments,
					printedBanner);
            
			refreshContext(context);
			afterRefresh(context, applicationArguments);
			stopWatch.stop();
			if (this.logStartupInfo) {
				new StartupInfoLogger(this.mainApplicationClass)
						.logStarted(getApplicationLog(), stopWatch);
			}
			listeners.started(context);
			callRunners(context, applicationArguments);
		}
		catch (Throwable ex) {
			handleRunFailure(context, ex, exceptionReporters, listeners);
			throw new IllegalStateException(ex);
		}

		try {
			listeners.running(context);
		}
		catch (Throwable ex) {
			handleRunFailure(context, ex, exceptionReporters, null);
			throw new IllegalStateException(ex);
		}
		return context;
	}