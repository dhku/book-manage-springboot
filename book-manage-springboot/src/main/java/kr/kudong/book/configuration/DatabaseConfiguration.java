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
	@Autowired
	private ApplicationContext applicationContext;
	
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
	
	//SqlSessionFactory: SqlSession 객체를 생성하는 역할을 담당하는 인터페이스
	//SqlSession: MyBatis에서 데이터베이스와 상호작용(SQL 실행, 트랜잭션을 관리, ...)하는 인터페이스
	@Bean
	SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception{
		SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
		sessionFactoryBean.setDataSource(dataSource);
		
		//매퍼 로케이션 설정
		sessionFactoryBean.setMapperLocations(
			this.applicationContext.getResources("classpath:/mapper/**/sql-*.xml"));
		
		
		//마이바티스 configuration 설정 + 추가로 매퍼 카멜케이스
        org.apache.ibatis.session.Configuration configuration 
        = new org.apache.ibatis.session.Configuration();
	    configuration.setMapUnderscoreToCamelCase(true);
	    sessionFactoryBean.setConfiguration(configuration);
	    
		return sessionFactoryBean.getObject();
	}
	
    // SqlSessionTemplate: MyBatis와 스프링 프레임워크를 통합할 때 사용하는 클래스 
    //                     SqlSession 인터페이스를 구현 
    @Bean
    SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
