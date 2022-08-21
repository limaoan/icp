package com.huawei.sic.icp.consumer;

import com.huawei.sic.icp.service.IcpCollectorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 *
 * @since 2022-08-20
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaConsumer {
    private final IcpCollectorService icpCollectorService;

//    @KafkaListener(id = "foo", topics = "test", clientIdPrefix = "myClientId")
    @KafkaListener(topics = "test")
    public void listen(String data) {
        log.info("recive:"+data);
        icpCollectorService.excute(data);
    }

}
