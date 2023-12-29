package com.github.dostonhamrakulov;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.github.dostonhamrakulov.ProducerConfiguration.TOPIC;

/**
 * Consumer Listener class to listen Kafka updates and
 *
 * @author Doston Hamrakulov
 */
@Service
@Slf4j
public class KafkaConsumerListener {

    /**
     * Consumes an update from Kafka
     * @param username the username
     */
    @KafkaListener(topics = TOPIC, groupId = "Consumer_Group")
    public void consume(final String username) {
        log.info("Picked message from kafka ::"+username);
    }

    /**
     * Consumes {@link UserDto} as JSON
     * @param userDto the {@link UserDto}
     */
    @KafkaListener(topics = TOPIC, groupId = "Consumer_Group")
    public void consumeJson(UserDto userDto) {
        log.info("Picked message from kafka ::"+ userDto.toString());
    }
}
