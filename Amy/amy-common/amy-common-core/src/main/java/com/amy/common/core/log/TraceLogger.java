package com.amy.common.core.log;

import com.amy.common.core.util.IpUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 * TraceLogger 工具类
 * @author xuqingxin
 */
public class TraceLogger {
    public static Logger logger = LoggerFactory.getLogger("TraceLogger");
    public static void main(String[] args) {
        TraceLogger._default(logger).msg("test").info();
        TraceLogger._default(logger).addKV("key","value").info();

    }
    public static _DEFAULT _default(Logger logger) {
        return new _DEFAULT(logger);
    }

    public static _COM_REQUEST_IN _com_request_in(Logger logger) {
        return new _COM_REQUEST_IN(logger);
    }

    public static _COM_REQUEST_OUT _com_request_out(Logger logger) {
        return new _COM_REQUEST_OUT(logger);
    }

    /***
     * request_in 日志
     */
    public static class _COM_REQUEST_IN extends BaseLog {

        public _COM_REQUEST_IN(Logger logger) {
            super(logger);
            msgBuilder.append("||hostIp=").append(IpUtils.getLocalIp());
        }

        @Override
        public String getDtag() {
            return "_com_request_in";
        }

        public _COM_REQUEST_IN uri(String uri) {
            msgBuilder.append("||uri=").append(uri);
            return this;
        }

        public _COM_REQUEST_IN method(String method) {
            msgBuilder.append("||method=").append(method);
            return this;
        }

        public _COM_REQUEST_IN request(String request) {
            msgBuilder.append("||request=").append(request);
            return this;
        }

        public _COM_REQUEST_IN request(Object[] request) {
            if (request != null) {
                if (request.length == 1) {
                    msgBuilder.append("||request=").append(request[0]);
                } else {
                    msgBuilder.append("||request=").append(request);
                }
            } else {
                msgBuilder.append("||request=null");
            }
            return this;
        }

        public _COM_REQUEST_IN clientIp(String clientIp) {
            msgBuilder.append("||clientIp=").append(clientIp);
            return this;
        }
    }

    /**
     * _com_request_out
     */
    public static class _COM_REQUEST_OUT extends BaseLog {

        public _COM_REQUEST_OUT(Logger logger) {
            super(logger);
        }

        @Override
        public String getDtag() {
            return "_com_request_out";
        }

        public _COM_REQUEST_OUT uri(String uri) {
            msgBuilder.append("||uri=").append(uri);
            return this;
        }

        public _COM_REQUEST_OUT method(String method) {
            msgBuilder.append("||method=").append(method);
            return this;
        }

        public _COM_REQUEST_OUT request(String request) {
            msgBuilder.append("||request=").append(request);
            return this;
        }

        public _COM_REQUEST_OUT response(String response) {
            msgBuilder.append("||response=").append(response);
            return this;
        }

        public _COM_REQUEST_OUT errno(String errno) {
            msgBuilder.append("||errno=").append(errno);
            return this;
        }

        public _COM_REQUEST_OUT errmsg(String errmsg) {
            msgBuilder.append("||errmsg=").append(errmsg);
            return this;
        }

        public _COM_REQUEST_OUT proc_time(long proc_time) {
            msgBuilder.append("||proc_time=").append(proc_time);
            return this;
        }

        public _COM_REQUEST_OUT clientIp(String clientIp) {
            msgBuilder.append("||request=").append(clientIp);
            return this;
        }
    }

    /**
     * _default
     */
    public static class _DEFAULT extends BaseLog {

        public _DEFAULT(Logger logger) {
            super(logger);
        }

        @Override
        public String getDtag() {
            return "_default";
        }

        public _DEFAULT msg(String msg) {
            msgBuilder.append("||msg=").append(msg);
            return this;
        }

        public _DEFAULT addKV(String key, String value) {
            msgBuilder.append("||").append(key).append("=").append(value);
            return this;
        }
    }

    /**
     * 基础
     */
    public static abstract class BaseLog {
        private Logger logger;
        protected StringBuilder msgBuilder = new StringBuilder();

        public BaseLog(Logger logger) {
            this.logger = logger;
        }

        public void info() {
            logger.info(buildMsg());
        }

        public void error() {
            logger.error(buildMsg());
        }

        public void debug() {
            if (logger.isDebugEnabled()) {
                logger.debug(buildMsg());
            }
        }

        public void warn() {
            logger.warn(buildMsg());
        }

        private String buildMsg() {
            StringBuilder traceBuilder = new StringBuilder();
            traceBuilder.append(getDtag()).append("||traceid=").append(MDC.get(LogConstants.TRACEID));
            traceBuilder.append("||spanid=").append(MDC.get(LogConstants.SPANID));
            if (StringUtils.isNotEmpty(MDC.get(LogConstants.CSPANID))) {
                traceBuilder.append("||cspanid=").append(MDC.get(LogConstants.CSPANID));
            }
            traceBuilder.append(msgBuilder);
            return traceBuilder.toString();
        }

        public abstract String getDtag();
    }
}
