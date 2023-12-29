package com.github.dostonhamrakulov;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.github.dostonhamrakulov.ProducerConfiguration.TOPIC;

@Service
public class KafkaConsumerListener {



    @KafkaListener(topics = TOPIC, groupId = "Consumer_Group")
    public void Consume(String Name)
    {
        System.out.println("Message Pickeed ::"+Name);
    }

    @KafkaListener(topics = TOPIC, groupId = "Consumer_Group")
    public void ConsumeJson(UserDto userDto)
    {
        System.out.println("Message Picked ::" + userDto.toString());
    }

}
