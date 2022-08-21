package com.huawei.sic.icp.service;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Map;

/**
 * icp备案信息采集
 *
 * @since 2022-08-20
 */
@Slf4j
@Service
public class IcpCollectorServiceImpl implements IcpCollectorService{
    private static final String DOMAIN = "https://icp.chinaz.com";

    private static final String HOST_TOKEN = "389,754,906";

    private static final String PERMIT_TOKEN = "820,918,917,925,920,937,866,919,931,929";

    private static final String QIYE_DATA_TOKEN = "727,21998,20867,31061,24957,33320,36486,31912,25943,27104,39207,21571,22223";

    @Async
    @Override
    public void excute(String host) {
        log.info("线程ID：" + Thread.currentThread().getId() + "线程名字：" +Thread.currentThread().getName()+"执行异步任务:" + host);
        String permit = getPerimitByHost(host);
        String companyName = getCompanyName(host);
    }


    private String getPerimitByHost(String host) {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format("https://icp.chinaz.com/home/GetPerimitByHost?kw=%s&hostToken=%s&permitToken=%s",
                host, HOST_TOKEN, PERMIT_TOKEN);

        HttpHeaders headers = new HttpHeaders();
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseDTO response = restTemplate.postForObject(url, request, ResponseDTO.class);
        if (response.getCode() == 200) {
            log.info("response:"+response.getData());
            return response.getData();
        } else {
            log.error("request error:"+response);
            return null;
        }
    }

    private String getCompanyName(String host) {
        String url = String.format("https://icp.chinaz.com/%s", host);
        try {
            Document document = Jsoup.connect(url).timeout(3000).get();
            return document.getElementsByClass("IcpMain01").get(0).firstElementChild().lastElementChild().firstElementChild().ownText();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void getCompanyData(String companyName) {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format("https://icp.chinaz.com/home/QiYeData?kw=%s&permitToken=%s", companyName, QIYE_DATA_TOKEN);

        HttpHeaders headers = new HttpHeaders();
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            log.info("response:"+response.getBody());
        } else {
            log.error("request error:"+response);
        }
    }
}
