package at.buc;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Application {
	
	public static void main(String[] args) {
//		ApplicationContext framework=SpringApplication.run(ApplicationFramework.class, args);
//		System.out.println("Let's inspect the beans provided by Spring Boot:");
//		String[] frameworkBeans = framework.getBeanDefinitionNames();
//		System.out.println("=== BUC Framework ====================");
//		Arrays.sort(frameworkBeans);
//		for (String beanName : frameworkBeans) {
//			System.out.println(beanName);
//		}
		
		ApplicationContext ctx = SpringApplication.run(Application.class, args);
//		System.out.println("=== Application ======================");
//		String[] applicationBeans = ctx.getBeanDefinitionNames();
//		Arrays.sort(applicationBeans);
//		for (String beanName : applicationBeans) {
//			System.out.println(beanName);
//		}
	}

}