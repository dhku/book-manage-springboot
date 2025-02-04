package kr.kudong.book.configuration;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@PropertySource("classpath:/application.properties")
public class DatabaseConfiguration
{
	//설정정보를 읽어와서 HikariConfig에 setter로 값을 하고 그 값을 가지고 있는 객체를 bean으로 등록
	@Bean
	@ConfigurationProperties(prefix="spring.datasource.hikari")
	HikariConfig hikariConfig() {
		return new HikariConfig();
	}
	
	@Bean
	DataSource dataSource() {
		DataSource dataSource = new HikariDataSource(hikariConfig());
		System.out.println(dataSource);
		return dataSource;
	}
	
}
