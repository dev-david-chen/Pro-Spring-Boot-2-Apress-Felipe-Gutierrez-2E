package com.apress.todo.config;

import com.apress.todo.repository.ToDoRepository;
import com.mongodb.WriteConcern;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.DependsOn;
//import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactoryBean;

@Configuration
public class ToDoConfig {
	
@Configuration
static class MongoConfiguration implements InitializingBean, DisposableBean {

     MongodExecutable executable;

    @Override
    public void afterPropertiesSet() throws Exception {
            int port = 27019;

            MongodStarter starter = MongodStarter.getDefaultInstance();

            MongodConfig mongodConfig = MongodConfig.builder()
                    .version(Version.Main.V4_0)
                    .net(new Net(port, Network.localhostIsIPv6()))
                    .build();
            ;
            executable = starter.prepare(mongodConfig);
            executable.start();
    }


	@Bean
	public MongoDatabaseFactory factory() {
		return new SimpleMongoClientDatabaseFactory("mongodb://localhost:27019/imager200_test");
	}
	
	
	@Bean
	public MongoTemplate mongoTemplate(MongoDatabaseFactory mongoDbFactory) {
		MongoTemplate template = new MongoTemplate(mongoDbFactory);
		template.setWriteConcern(WriteConcern.ACKNOWLEDGED);
		return template;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	public MongoRepositoryFactoryBean<?,?,?> mongoFactoryRepositoryBean(MongoTemplate template) {
		MongoRepositoryFactoryBean<?,?,?> mongoDbFactoryBean = new MongoRepositoryFactoryBean(ToDoRepository.class);
		
		mongoDbFactoryBean.setMongoOperations(template);
		
		return mongoDbFactoryBean;
	}	

    @Override
    public void destroy() throws Exception {
        executable.stop();
    }
}
}