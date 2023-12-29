package com.github.dostonhamrakulov;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

/**
 * Kafka Producer Configuration
 *
 * @author Doston Hamrakulov
 */

@EnableKafka
@Configuration
public class ProducerConfiguration {

    private static final String KAFKA_HOSTNAME = "localhost:29092";

    private static final String GROUP_ID_CONFIG = "Consumer_Group";
    public static final String TOPIC = "first_topic";

    @Bean
    public ProducerFactory<String, UserDto> producerFactory() {
        Map<String, Object> userConfig = new HashMap<>();

        userConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_HOSTNAME);
        userConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        userConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(userConfig);
    }

    @Bean
    public ProducerFactory<String, String> producerFactorySS() {
        Map<String, Object> userConfig = new HashMap<>();

        userConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_HOSTNAME);
        userConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        userConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        return new DefaultKafkaProducerFactory<>(userConfig);
    }

    @Bean
    public ConsumerFactory<String, String> consumerFactorySS() {
        Map<String, Object> userConfig = new HashMap<>();
        userConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_HOSTNAME);
        userConfig.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID_CONFIG);
        userConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        userConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(userConfig);
    }

    @Bean
    public ConsumerFactory<String, UserDto> consumerFactory() {
        Map<String, Object> userConfig = new HashMap<>();
        userConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_HOSTNAME);
        userConfig.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID_CONFIG);
        userConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        userConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(userConfig);
    }

    @Bean
    public KafkaTemplate<String, UserDto> kafkaTemplateSS() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplateSU() {
        return new KafkaTemplate<>(producerFactorySS());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory((consumerFactorySS()));
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, UserDto> kafkaListenerContainerFactorySO() {
        ConcurrentKafkaListenerContainerFactory<String, UserDto> factorySO = new ConcurrentKafkaListenerContainerFactory<>();
        factorySO.setConsumerFactory((consumerFactory()));
        return factorySO;
    }
}
