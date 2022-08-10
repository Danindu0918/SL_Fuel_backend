package com.fuel.fuelapplication.kafkaConfig;

import lombok.AllArgsConstructor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
@AllArgsConstructor
public class KafkaProducerConfiguration {

    private final Environment env;

    @Bean
    public Map<String, Object> producerConfigs(){

        Map<String, Object> prop = new HashMap<>();

        prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, env.getProperty("spring.kafka.boostrap-servise"));
        prop.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 101680096);

        prop.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
        prop.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        prop.put(ProducerConfig.MAX_BLOCK_MS_CONFIG,5000);

        return prop;
    }

    @Bean
    public ProducerFactory<Integer, String> producerFactory(){
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    public KafkaTemplate<Integer, String> kafkaTemplate(){
        return new KafkaTemplate<>(producerFactory());
    }
}
