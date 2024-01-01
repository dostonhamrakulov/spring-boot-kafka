package com.github.dostonhamrakulov;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.github.dostonhamrakulov.ProducerConfiguration.TOPIC;

/**
 * Rest controller to publish a message to Kafka
 *
 * @author Doston Hamrakulov
 */
@RestController
@RequestMapping("/kafka")
@Slf4j
public class KafkaPublisherController {

    private final KafkaTemplate<String, UserDto> kafkaTemplateSU;
    private final KafkaTemplate<String, String> kafkaTemplateSS;

    @Autowired
    public KafkaPublisherController(final KafkaTemplate<String, UserDto> kafkaTemplateSU, final KafkaTemplate<String, String> kafkaTemplateSS) {
        this.kafkaTemplateSU = kafkaTemplateSU;
        this.kafkaTemplateSS = kafkaTemplateSS;
    }

    /**
     * Creates a user, aka - published a message with the given userName
     * @param userName the username
     * @return
     */
    @GetMapping("/createUser/{userName}")
    public ResponseEntity<String> publishUsername(@PathVariable("userName") final String userName) {
        kafkaTemplateSS.send(TOPIC, UUID.randomUUID().toString(), userName + " value");
        log.info("Pushed data {} to Kafka", userName);
        return ResponseEntity.ok(userName + " Pushed to Kafka");
    }

    /**
     * Publishes the given {@link UserDto} to the kafka
     * @param userDto the {@link UserDto}
     * @return
     */
    @PostMapping("/createUser")
    public ResponseEntity<String> publishUserDto(@RequestBody final UserDto userDto) {
        try {
            kafkaTemplateSU.send(TOPIC, UUID.randomUUID().toString(), userDto).get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("Cannot send and get a message to kafka.", e);
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(userDto.getName() + " Pushed to Kafka");
    }
}
