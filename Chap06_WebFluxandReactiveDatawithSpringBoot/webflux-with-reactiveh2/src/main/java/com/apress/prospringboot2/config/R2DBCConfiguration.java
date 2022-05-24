package com.apress.prospringboot2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

import io.r2dbc.h2.H2ConnectionConfiguration;
import io.r2dbc.h2.H2ConnectionFactory;
import io.r2dbc.h2.H2ConnectionOption;

@Configuration
@EnableR2dbcRepositories
public class R2DBCConfiguration extends AbstractR2dbcConfiguration {
    @Bean
    public H2ConnectionFactory connectionFactory() {
//    	ConnectionFactoryOptions options = ConnectionFactoryOptions.builder()
//        	    .option(ConnectionFactoryOptions.DRIVER, "h2")
//        	    .option(ConnectionFactoryOptions.PROTOCOL, "mem")  // file, mem
//        	    .option(ConnectionFactoryOptions.HOST, "localhost")
//        	    .option(ConnectionFactoryOptions.USER, "sa")
//        	    .option(ConnectionFactoryOptions.PASSWORD, "")
//        	    .option(ConnectionFactoryOptions.DATABASE, "testdb")
//        	    .build();
//    	
//    	return ConnectionFactories.get(options);
        return new H2ConnectionFactory(
                H2ConnectionConfiguration
                	.builder()
                	.inMemory("testdb")
                	.property(H2ConnectionOption.DB_CLOSE_DELAY, "-1")
                	//.url("mem:testdb;DB_CLOSE_DELAY=-1;") //r2dbc:h2:mem:default;DB_CLOSE_DELAY=-1;
                    .username("sa")
                    .build()
        );
        
       
    }
}
