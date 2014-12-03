package controllers;

import javax.sql.DataSource;

import jdbcTemplates.AnswerJDBCTemplate;
import jdbcTemplates.EventJDBCTemplate;
import jdbcTemplates.GeneralInfoJDBCTemplate;
import jdbcTemplates.QuestionJDBCTemplate;
import jdbcTemplates.UserJDBCTemplate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@ComponentScan
// spring boot configuration
@EnableAutoConfiguration
// file that contains the properties
@PropertySource("classpath:application.properties")
public class BeanConfig {

	/**
	 * Load the properties
	 */
	@Value("${database.driver}")
	private String databaseDriver;
	@Value("${database.url}")
	private String databaseUrl;
	@Value("${database.username}")
	private String databaseUsername;
	@Value("${database.password}")
	private String databasePassword;

	/**
	 * As data source we use an external database
	 *
	 * @return
	 */

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(databaseDriver);
		dataSource.setUrl(databaseUrl);
		dataSource.setUsername(databaseUsername);
		dataSource.setPassword(databasePassword);
		return dataSource;
	}

	@Bean
	public UserJDBCTemplate userJDBCTemplate() {
		UserJDBCTemplate jd = new UserJDBCTemplate();
		jd.setDataSource(dataSource());
		return jd;
	}

	@Bean
	public EventJDBCTemplate eventJDBCTemplate() {
		EventJDBCTemplate jd = new EventJDBCTemplate();
		jd.setDataSource(dataSource());
		return jd;
	}
	
	@Bean
	public GeneralInfoJDBCTemplate generalInfoJDBCTemplate() {
		GeneralInfoJDBCTemplate jd = new GeneralInfoJDBCTemplate();
		jd.setDataSource(dataSource());
		return jd;
	}
	
	@Bean
	public QuestionJDBCTemplate questionJDBCTemplate() {
		QuestionJDBCTemplate jd = new QuestionJDBCTemplate();
		jd.setDataSource(dataSource());
		return jd;
	}
	
	@Bean
	public AnswerJDBCTemplate answerJDBCTemplate() {
		AnswerJDBCTemplate jd = new AnswerJDBCTemplate();
		jd.setDataSource(dataSource());
		return jd;
	}
}
