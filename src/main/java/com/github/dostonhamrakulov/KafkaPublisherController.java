package com.github.dostonhamrakulov;

import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.github.dostonhamrakulov.KafkaConsumerListener.TOPIC;

@RestController
@RequestMapping("/kafka")
public class KafkaPublisherController {


    @Autowired
    KafkaTemplate<String, UserDto> kafkaTemplateSU;

    @Autowired
    KafkaTemplate<String, String> kafkaTemplateSS;

    Logger logger = LoggerFactory.getLogger(KafkaPublisherController.class.getName());

    /**
     * @param userName
     * @return
     */
    @GetMapping("/createUser/{userName}")
    public String createUser(@PathVariable("userName") String userName)
    {
        UserDto userDto = new UserDto("sajith", "userName", "Name");
        kafkaTemplateSS.send(TOPIC,userName);
        logger.info("Pushing data to Kafka");
        return userName+" Pushed to Kafka";

    }

    /**
     * @param userDto
     * @return
     */
    @PostMapping("/createUser")
    public String postUsertoKafka(@RequestBody UserDto userDto)
    {
        System.out.println("UserDto Details" + userDto.toString() );

        //Types of Sends
        //		Fire and Forget
        kafkaTemplateSU.send(TOPIC, userDto.getName(), userDto);
        //		Synchronous send
        try {
            kafkaTemplateSU.send(TOPIC, userDto.getName(), userDto).get();
        } catch (InterruptedException | ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //		Asynchronous Send
        return userDto.getName() + " Pushed to Kafka";
    }
}
