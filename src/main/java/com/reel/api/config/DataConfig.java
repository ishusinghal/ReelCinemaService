package com.reel.api.config;

import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author Ishu
 * Using embedded mongo db
 */
@Configuration
@Import(EmbeddedMongoAutoConfiguration.class)
public class DataConfig {
	
	

}
