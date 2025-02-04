package kr.kudong.book;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.HiddenHttpMethodFilter;

@SpringBootApplication
public class BookManageSpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookManageSpringbootApplication.class, args);
	}
	
	//form에 get post 말고도 put delete를 가능하게 해주는 
	//form 안에 <input type="hidden" id="method" name="_method" /> 하면됨 
	@Bean
	public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
	    return new HiddenHttpMethodFilter();
	}
}
