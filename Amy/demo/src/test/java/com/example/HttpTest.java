package com.example;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Maps;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;

@SpringBootTest
public class HttpTest {

    @Test
    public void httpTest() {

        // long eventTime = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
        long eventTime = System.currentTimeMillis();
        // long eventTime = 1661916464323L;

        String REPORT_SIGN_TEMPLATE = "secret=%s|caller=%s|event_id=%s|event_type=%s|timestamp=%s";

        Long userId = 550803826L;
        String cityCode = "420100";
        String caller = "mapapp";
        String secret = "eea8aa7b11664c27";
        String orderId = "7140560988506488833";
        Integer eventType = 100080;



        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("caller", caller);
        paramMap.put("event_type", eventType);
        paramMap.put("event_id", orderId);
        paramMap.put("event_time", eventTime);
        paramMap.put("user_id", userId);
        paramMap.put("ad_code", cityCode);
        paramMap.put("qimei", "b51dcd53affb0c804a6ec7ad00001bf16808");

        String format = String.format(REPORT_SIGN_TEMPLATE, secret,
                caller, orderId, eventType, eventTime);

        String sign = MD5Encode(format, StandardCharsets.UTF_8.name());
        paramMap.put("sign", sign);

        System.out.println(JSONUtil.toJsonStr(paramMap));
        System.out.println(secret);
        String url = "https://maph5test1.sparta.html5.qq.com/event/v2/report/outmsg";

        String post = HttpUtil.post(url, JSONUtil.toJsonStr(paramMap));
        System.out.println(post);
    }

    public static String MD5Encode(String origin, String charsetname) {
        String resultString = null;

        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (charsetname != null && !"".equals(charsetname)) {
                resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetname)));
            } else {
                resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
            }
        } catch (Exception var4) {
        }

        return resultString;
    }

    private static String byteArrayToHexString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();

        for (int i = 0; i < b.length; ++i) {
            resultSb.append(byteToHexString(b[i]));
        }

        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (b < 0) {
            n = b + 256;
        }

        int d1 = n / 16;
        int d2 = n % 16;
        return HEX_DIGITS[d1] + HEX_DIGITS[d2];
    }

    private static final String[] HEX_DIGITS = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

}
