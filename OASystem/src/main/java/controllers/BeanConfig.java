package controllers;

import javax.sql.DataSource;

import jdbcTemplates.EventJDBCTemplate;
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

	// <!-- Definition for the specific jdbc bean -->
	// <bean id="UserJDBCTemplate" class="jdbcTemplates.UserJDBCTemplate">
	// <property name="dataSource" ref="dataSource" />
	// </bean>
	// <!-- Definition for the specific jdbc bean -->
	// <bean id="EventJDBCTemplate" class="jdbcTemplates.EventJDBCTemplate">
	// <property name="dataSource" ref="dataSource" />
	// </bean>

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
}
