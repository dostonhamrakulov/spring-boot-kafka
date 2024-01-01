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
     * @param object the message
     */
    @KafkaListener(topics = TOPIC, groupId = "Consumer_Group-1")
    public void consume(final Object object) {

        if (object instanceof String) {
            log.info("Picked message from kafka :: " + (String) object);
        }

        if (object instanceof UserDto) {
            log.info("Picked message from kafka ::"+ ((UserDto) object).toString());
        }

        log.info("Picked message from kafka ::" + object.toString());
    }
}
