package com.amy.common.designm.demo2;

import cn.hutool.http.HttpUtil;
import com.amy.common.core.util.JsonUtils;

import java.util.Map;

/**
 * @author xuqingxin
 */
public class HttpUtils {

    public static void main(String[] args) {
        post();
    }
    public static void post(){
        String aa = "{\n" +
                "    \"gray\": {\n" +
                "        \"base\": {\n" +
                "            \"guid\": \"xxxxxxxx\",\n" +
                "            \"platform\": \"android\",\n" +
                "            \"version\": \"9.18.0\",\n" +
                "            \"city\": \"北京市\",\n" +
                "            \"lng\": \"116.40\",\n" +
                "            \"lat\": \"39.90\"\n" +
                "        }\n" +
                "    },\n" +
                "    \"businessIds\": [],\n" +
                "    \"moduleIds\": [],\n" +
                "    \"configIds\": [\n" +
                "        \"1703\"\n" +
                "    ]\n" +
                "}";
        Map params = JsonUtils.parseObject(aa, Map.class);
        String url = "https://apollopreview.map.qq.com/api/v1/05872c3/get-config";
        String post = HttpUtil.post(url, params);
        System.out.println(post);
    }
}
