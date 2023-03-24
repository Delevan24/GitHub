package com.amy.common.core.util;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.util.List;

/**
 * Friendship is a sheltering tree.
 *
 * @author : chenjunjie
 * @date : 2019-03-19 11:47
 */
public class IpUtils {

    private static final Logger logger = LoggerFactory.getLogger(IpUtils.class);
    //    private static final String ipMatcher = "^(1\\d{2}|2[0-4]\\d|25[0-5]|0[1-9]\\d|0{2}[1-9])\\."
    //            + "(1\\d{2}|2[0-4]\\d|25[0-5]|0[1-9]\\d|0{2}\\d)\\."
    //            + "(1\\d{2}|2[0-4]\\d|25[0-5]|0[1-9]\\d|0{2}\\d)\\."
    //            + "(1\\d{2}|2[0-4]\\d|25[0-5]|0[1-9]\\d|0{2}\\d)$";
    private static final String ipMatcher = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
            + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
            + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
            + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";

    private static final String NULL_STRING = "null";


    /**
     * 判断ip是否合法
     *
     * @param ipStr ip地址
     * @return true（合法） false（不合法）
     */

    public static boolean isIpValid(String ipStr) {
        if (ipStr.matches(ipMatcher)) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 将传入的ip转换为标准的long格式的String类型ip
     *
     * @param value ip地址(现只接收long,String两种类型.String包含两种格式,一种为标准的long格式String,一种为192.168.1.1格式)
     * @return long格式String
     */
    public static String formatIpToStr(Object value) {
        try {
            if (null == value) {
                return null;
            }
            if (value instanceof Long) {
                return String.valueOf(value);
            }
            if (value instanceof String) {
                String valueStr = ((String) value).trim();
                if (StringUtils.isBlank(valueStr) || NULL_STRING.equals(valueStr)) {
                    return null;
                }
                if (valueStr.matches(ipMatcher)) {
                    Long ipValue = convertIPToLong(valueStr);
                    if (ipValue == null) {
                        return null;
                    }
                    return ipValue.toString();
                }
                return valueStr;
            }
            return String.valueOf(value);
        } catch (Exception e) {
            logger.error("格式化IP error,value:{}", JsonUtils.toJsonString(value), e);
        }
        return null;
    }

    /**
     * 将IP转成Long型
     *
     * @param ipStr IP地址
     * @return long型IP
     */
    public static Long convertIPToLong(String ipStr) {
        if (StringUtils.isBlank(ipStr)) {
            return null;
        }
        try {
            List<String> ipSegments = Lists.newArrayList(Splitter.on(".").omitEmptyStrings().trimResults().split(ipStr));
            long ipLong = 0L;
            for (String ipSegment : ipSegments) {
                ipLong = (ipLong << 8) + Long.valueOf(ipSegment);
            }
            return ipLong;
        } catch (Exception e) {
            logger.error("Convert ip " + ipStr + " to long failed: ", e);
        }
        return null;
    }

    /**
     * 获取本机IP
     *
     * @return
     */
    public static String getLocalIp() {
        try {
            InetAddress local = InetAddress.getLocalHost();
            return local.getHostAddress();
        } catch (Exception e) {
            logger.error("cannot get localhost ip, error:{}", ExceptionUtils.getFullStackTrace(e));
            return "127.0.0.1";
        }
    }

    public static String getLocalIpWithoutDot() {
        String ip = getLocalIp();
        return ip.replace(".", "");
    }

}
