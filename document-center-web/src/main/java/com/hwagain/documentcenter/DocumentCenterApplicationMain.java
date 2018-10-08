package com.hwagain.documentcenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.env.SimpleCommandLinePropertySource;
import org.springframework.scheduling.annotation.EnableAsync;

import com.hwagain.framework.web.common.conf.ForameworkBeanDefinitionRegistryPostProcessor;

@SpringBootApplication(scanBasePackages = { "com.hwagain" })
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableAsync
@MapperScan({ "com.hwagain.documentcenter.**.mapper*" })
@ImportResource({"classpath:dubboConsumer.xml"})
public class DocumentCenterApplicationMain {

	public static void main(String[] args) throws Exception {
//		SpringApplication.run(FrameworkApplicationMain.class, args);
		SpringApplication springApplication = new SpringApplication(new Object[] { DocumentCenterApplicationMain.class });
		springApplication.addInitializers(new ApplicationContextInitializer<ConfigurableApplicationContext>() {
			@Override
			public void initialize(ConfigurableApplicationContext applicationContext) {
				SimpleCommandLinePropertySource ps = new SimpleCommandLinePropertySource(args);
				ForameworkBeanDefinitionRegistryPostProcessor processor = new ForameworkBeanDefinitionRegistryPostProcessor(
						applicationContext, ps);
				applicationContext.addBeanFactoryPostProcessor(processor);
			}
		});
		springApplication.run(args);
		System.err.println("启动成功");
	}

}