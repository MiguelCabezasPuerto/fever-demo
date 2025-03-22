package com.fever.event.test.fever_demo_miguelcabezas;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.ImmutableMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;

@Configuration
@EnableReactiveMongoRepositories
public class MongoConfig extends AbstractReactiveMongoConfiguration {

    private MongodExecutable mongodExecutable;

    @Bean
    public MongoClient reactiveMongoClient() {
        try {
            String ip = "localhost";
            int port = 27017;
            ImmutableMongodConfig mongodConfig = MongodConfig.builder()
                    .version(Version.Main.PRODUCTION)
                    .net(new Net(ip, port, Network.localhostIsIPv6()))
                    .build();
            mongodExecutable = MongodStarter.getDefaultInstance().prepare(mongodConfig);
            mongodExecutable.start();
            return MongoClients.create("mongodb://" + ip + ":" + port);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    public ReactiveMongoTemplate reactiveMongoTemplate() {
        return new ReactiveMongoTemplate(reactiveMongoClient(), getDatabaseName());
    }

    @Override
    protected String getDatabaseName() {
        return "events";
    }
}
