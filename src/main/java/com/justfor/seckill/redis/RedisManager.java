package com.justfor.seckill.redis;

import cn.hutool.core.lang.ClassScaner;
import com.alibaba.fastjson.JSON;
import com.justfor.seckill.domain.MiaoshaUser;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.Redefinable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author lth
 * @date 2019年10月10日 14:29
 */

@Component
@Slf4j
public class RedisManager {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * @Description: 设置对象
     * @Param: keyPrefix: 前缀
     * @return: 是否设置成功
     * @Author: Tonghuan
     * @Date: 2019/10/10
     */

    public <T> boolean set(KeyPrefix keyPrefix, String key, T value) {
        int seconds = keyPrefix.exprireSeonds();
        String reallyKey = keyPrefix.getPrefix() + key;
        String json = JSON.toJSONString(value);
        if (seconds <= 0) {
            redisTemplate.opsForValue().set(reallyKey, json);
        } else {
            redisTemplate.opsForValue().set(reallyKey, json, seconds, TimeUnit.SECONDS);
        }
        return true;
    }

    /**
     * @Description:
     * @Param:
     * @return:
     * @Author: Tonghuan
     * @Date: 2019/10/10
     */
    public <T> T get(MiaoShaUserKey preFix, String token, Class<T> miaoshaUserClass) {
        String prefix = preFix.getPrefix();
        String reallyKey = prefix + ":" + token;
        String json = redisTemplate.opsForValue().get(reallyKey);
        try {
            return stringToBean(json, miaoshaUserClass);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("json解析格式错误{}", e.getMessage());
        }
        return null;
    }

    /**
     * @Description: 根据传入的clazz 进行解析
     * @Param:
     * @return:
     * @Author: Tonghuan
     * @Date: 2019/10/10
     */
    @SuppressWarnings("unchecked")
    private <T> T stringToBean(String str, Class<T> clazz) {
        if (str == null || str.length() <= 0 || clazz == null) {
            return null;
        }
        if (clazz == int.class || clazz == Integer.class) {
            return (T) Integer.valueOf(str);
        } else if (clazz == String.class) {
            return (T) str;
        } else if (clazz == long.class || clazz == Long.class) {
            return (T) Long.valueOf(str);
        } else {
            return JSON.toJavaObject(JSON.parseObject(str), clazz);
        }
    }
}
