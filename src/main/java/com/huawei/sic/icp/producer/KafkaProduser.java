package com.huawei.sic.icp.producer;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.kafka.core.KafkaSendCallback;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

/**
 *
 * @since 2022-08-20
 */
@Component
@RequiredArgsConstructor
public class KafkaProduser {

    private final KafkaTemplate template;

    public void sendToKafka(final String data) {
        final ProducerRecord<String, String> record = createRecord(data);

        ListenableFuture<SendResult<Integer, String>> future = template.send(record);
        future.addCallback(new KafkaSendCallback<Integer, String>() {

            @Override
            public void onSuccess(SendResult<Integer, String> result) {
                handleSuccess(data);
            }

            @Override
            public void onFailure(KafkaProducerException ex) {
                handleFailure(data, record, ex);
            }
        });
    }

    private ProducerRecord<String, String> createRecord(String data) {
        return new ProducerRecord<>("test", "foo");
    }

    private void handleSuccess(String data) {

    }

    private void handleFailure(String data, ProducerRecord<String, String> record, KafkaProducerException ex) {

    }
}
