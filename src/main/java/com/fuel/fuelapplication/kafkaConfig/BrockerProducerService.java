package com.fuel.fuelapplication.kafkaConfig;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@Service
@AllArgsConstructor
public class BrockerProducerService {

    private final KafkaTemplate<Integer, String> kafkaTemplate;

//    public BrockerProducerService(KafkaTemplate<Integer, String> kafkaTemplate){
//        this.kafkaTemplate = kafkaTemplate;
//    }


    public void sendMessage(String topic, String message){
        ListenableFuture<SendResult<Integer, String>> future = kafkaTemplate.send(topic, message);

        future.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {
            @Override
            public void onFailure(Throwable ex) {
                log.info("unable to send message='{}'", message, ex);
            }

            @Override
            public void onSuccess(SendResult<Integer, String> result) {
                log.info("sent message='{}' with offset={}", message, result.getRecordMetadata().offset());
            }
        });
    }


}
