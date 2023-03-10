package com.estimulo.system.common.configuration;

import java.nio.charset.Charset;

import javax.servlet.Filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.estimulo.system.common.interceptor.LoggerInterceptor;
import com.estimulo.system.common.interceptor.LoginInterceptor;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer{

	
	@Override
	public void addInterceptors(InterceptorRegistry registry){
		registry.addInterceptor(new LoginInterceptor()) //로그인 인터셉터
			.addPathPatterns("/*")
		//	.addPathPatterns("/*/*.html")
			.excludePathPatterns("/*logout*")
			.excludePathPatterns("/*login*")
			.excludePathPatterns("/error");
		
		registry.addInterceptor(new LoggerInterceptor());
		
		WebMvcConfigurer.super.addInterceptors(registry);
	}

	// 2개의 빈은 인코딩 관련.
	@Bean
	public Filter characterEncodingFilter(){
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();    //CharacterEncodingFilter는 스프링이 제공하는 클래스로 웹에서 주고받는 데이터의 헤더값을 UTF-8로 인코딩 해줌.
		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceEncoding(true);  //기본값은 false로 설정되어 있음.
		
		return characterEncodingFilter;
	}
	
	@Bean
	public HttpMessageConverter<String> responseBodyConverter(){
		return new StringHttpMessageConverter(Charset.forName("UTF-8"));   //StringHttpMessageConverter는 @ResponseBody를 이용하여 결과를 출력할 때 결과를 UTF-8 로 설정함.
	}
	//���������� �⺻���� �����ϴ� MultipartResolve
	@Bean
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setDefaultEncoding("UTF-8"); // ���� ���ڵ� ����
		multipartResolver.setMaxUploadSizePerFile(5 * 1024 * 1024); // ���ϴ� ���ε� ũ�� ���� (5MB)
		return multipartResolver;
	  }

}
