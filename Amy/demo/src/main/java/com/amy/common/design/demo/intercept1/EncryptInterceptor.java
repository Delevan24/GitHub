package com.amy.common.design.demo.intercept1;


import com.amy.common.core.annotation.FieldEncrypt;
import com.amy.common.design.demo.encrypt.AlgorithmHandler;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.List;


/**
 * @author xuqingxin
 */
@Intercepts(
        {
                @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
                @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class,
                        RowBounds.class, ResultHandler.class}),
                @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class,
                        RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
        }
)
@Component
public class EncryptInterceptor implements Interceptor, ApplicationContextAware {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object target = invocation.getTarget();
        Object[] args = invocation.getArgs();
        Object parameter = args[1];
        boolean isUpdate = args.length == 2;
        if (isUpdate) {
            // 判断是否修改方法
            Field[] fields = parameter.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(FieldEncrypt.class)) {
                    FieldEncrypt fieldEncrypt = field.getDeclaredAnnotation(FieldEncrypt.class);
                    AlgorithmHandler algorithmHandler = applicationContext.getBean(fieldEncrypt.algorithm().name() + "AlgorithmHandler", AlgorithmHandler.class);
                    String oldValue = (String) field.get(parameter);
                    field.set(parameter, algorithmHandler.encryptStr(fieldEncrypt.password(), oldValue));
                }
            }
        } else {
            final Executor executor = (Executor) target;
            MappedStatement ms = (MappedStatement) args[0];
            RowBounds rowBounds = (RowBounds) args[2];
            ResultHandler resultHandler = (ResultHandler) args[3];
            BoundSql boundSql;
            if (args.length == 4) {
                boundSql = ms.getBoundSql(parameter);
            } else {
                boundSql = (BoundSql) args[5];
            }
            CacheKey cacheKey = executor.createCacheKey(ms, parameter, rowBounds, boundSql);
            List list = executor.query(ms, parameter, rowBounds, resultHandler, cacheKey, boundSql);
            if (list != null) {
                for (Object object : list) {
                    Field[] fields = object.getClass().getDeclaredFields();
                    for (Field field : fields) {
                        field.setAccessible(true);
                        if (field.isAnnotationPresent(FieldEncrypt.class)) {
                            String oldValue = (String) field.get(object);
                            FieldEncrypt fieldEncrypt = field.getDeclaredAnnotation(FieldEncrypt.class);
                            AlgorithmHandler algorithmHandler = applicationContext.getBean(fieldEncrypt.algorithm().name() + "AlgorithmHandler", AlgorithmHandler.class);
                            field.set(object, algorithmHandler.decryptStr(fieldEncrypt.password(), oldValue));
                        }
                    }
                }
            }
            return list;
        }
        return invocation.proceed();
    }

    ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}

