package com.estimulo;

import java.nio.charset.Charset;

import javax.servlet.http.HttpSessionListener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.estimulo.system.common.interceptor.SessionListener;
import com.estimulo.system.common.interceptor.SiteMeshFilter;


//@SpringBootApplication spring boot 의 핵심 어노테이션 
//@ImportResource("application.xml")
//@EnableAutoConfiguration
//@@ComponentScan 을 해준다
//또한 root 패키지 안에 어플리케이션을 위치시키는 규칙을 지킨다면, 
//@ComponentScan은 아규먼트 없이 잘 작동합니다. 모든 어플리케이션 컴포넌트(@Component,@Service,@Repository,@Controller)
//도 자동적으로 Spring Bean 으로 잘 등록됩니다.


@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class LogisticsApplication extends SpringBootServletInitializer{
	//@EnableAspectJAutoProxy(proxyTargetClass = true)
	// 기존에는 aop 를 bci 상식을 사용했지만 이 어노테이션을 사용하면 proxy 방식을 사용하게 해준다
	//@Aspect 어노테이션을 사용 가능 하게 해준다

	
	public static void main(String[] args) {
		SpringApplication.run(LogisticsApplication.class, args);
	}
	
		@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		
		return application.sources(LogisticsApplication.class);
	}

		//SiteMeshFilter web.xml 에 적용해주는거랑 같음
		@Bean
		public FilterRegistrationBean<SiteMeshFilter> siteMeshFilter() {
			FilterRegistrationBean<SiteMeshFilter> filter = new FilterRegistrationBean<SiteMeshFilter>();
			filter.setFilter(new SiteMeshFilter());
			return filter;
	
		}
		
		//SessionListener web.xml 에서 같은역할 하는 bean 
		@Bean
		public ServletListenerRegistrationBean<HttpSessionListener> sessionListener() {
			return new ServletListenerRegistrationBean<HttpSessionListener>(new SessionListener());
		}
		
		@Bean 
		public HttpMessageConverter<String> responseBodyConverter() {
			return new StringHttpMessageConverter(Charset.forName("UTF-8")); 
		} 
		
		@Bean 
		public CharacterEncodingFilter characterEncodingFilter() {
			CharacterEncodingFilter characterEncodingFilter = new
		    CharacterEncodingFilter(); characterEncodingFilter.setEncoding("UTF-8");
		    characterEncodingFilter.setForceEncoding(true); return
		    characterEncodingFilter; 
        } 
		 

}
