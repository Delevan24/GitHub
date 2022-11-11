package com.example;


import cn.hutool.json.JSONUtil;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Objects;

@Slf4j
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@ContextConfiguration
public class HttpTest1 {

    @Autowired
    private RestTemplate restTemplate;

    // @Test
    // public void test() {
    //
    //     long eventTime = 1661916464323L;
    //
    //     String REPORT_SIGN_TEMPLATE = "secret=%s|caller=%s|event_id=%s|event_type=%s|timestamp=%s";
    //
    //     Long userId = 550804382L;
    //     String cityCode = "";
    //     String caller = "mapapp";
    //     String secret = "eea8aa7b11664c27";
    //     String orderId = "abcde";
    //     Integer eventType = 100080;
    //
    //
    //     Map<String, Object> paramMap = Maps.newHashMap();
    //     paramMap.put("caller", caller);
    //     paramMap.put("event_type", eventType);
    //     paramMap.put("event_id", "7137874744160878592");
    //     paramMap.put("event_time", eventTime);
    //     paramMap.put("user_id", userId);
    //     paramMap.put("ad_code", cityCode);
    //     paramMap.put("qimei", "oSc2TjqSI2rrdxU1bnsmO8c9GsrQ");
    //
    //     paramMap.put("sign", "2130ddd807cb377fed516ff7243232ab");
    //
    //
    //     String url = "https://maph5test1.sparta.html5.qq.com/event/v2/report/outmsg";
    //
    //     //1.请求头
    //     HttpHeaders httpHeaders = new HttpHeaders();
    //     //httpHeaders.setContentType(MediaType.valueOf(MediaType.APPLICATION_JSON_UTF8_VALUE));
    //     httpHeaders.setContentType(MediaType.parseMediaType("application/json; charset=UTF-8"));
    //     httpHeaders.add("Accept", MediaType.APPLICATION_JSON.toString());
    //
    //     //2.请求体
    //     HttpEntity<Void> httpEntity = new HttpEntity(JSONUtil.toJsonStr(paramMap), httpHeaders);
    //
    //     //3.响应体
    //     ResponseEntity<OrderReportActivityResponse> responseEntity = null;
    //
    //     //4.发送get请求
    //     try {
    //         responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, OrderReportActivityResponse.class);
    //     } catch (RestClientException e) {
    //         log.error("[RestTemplateTest-test] http request error", e);
    //     }
    //
    //     //5.数据处理
    //     if (Objects.nonNull(responseEntity)) {
    //         OrderReportActivityResponse body = responseEntity.getBody();
    //         if (Objects.isNull(body)) {
    //             log.info("返回错误的响应的状态码");
    //         } else {
    //             Integer retCode = body.getRetCode();
    //             if (!Objects.equals(0, retCode)) {
    //                 log.info("返回错误的响应的状态码");
    //             }
    //
    //             //数据进行业务处理 略
    //         }
    //     }
    //
    // }
}
