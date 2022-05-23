package com.apress.prospringboot2.config;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactoryBean;

import com.apress.prospringboot2.repository.ToDoRepository;
import com.mongodb.WriteConcern;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class ToDoConfig {
	MongodExecutable executable;

	@PostConstruct
	void initMongoServer() {
		try {
			int port = 27019;
			MongodStarter starter = MongodStarter.getDefaultInstance();

			MongodConfig mongodConfig;

			mongodConfig = MongodConfig.builder().version(Version.Main.V4_0)
					.net(new Net(port, Network.localhostIsIPv6())).build();
			executable = starter.prepare(mongodConfig);
			executable.start();
		} catch (Exception e) {
			log.error("Error starting embedded MongoDB server!", e);
			// e.printStackTrace();
		}
	}

	@PreDestroy
	void shudDownMongoServer() {
		executable.stop();
	}

	@Bean
	public MongoDatabaseFactory factory() {
		return new SimpleMongoClientDatabaseFactory("mongodb://localhost:27019/testdb");
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
}
