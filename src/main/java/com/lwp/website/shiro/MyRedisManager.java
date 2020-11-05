package com.lwp.website.shiro;

import com.lwp.website.config.SysConfig;
import com.lwp.website.utils.RedisUtil;
import com.lwp.website.utils.StringUtil;
import org.crazycake.shiro.RedisManager;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Base64;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/09/15/9:17
 * @Description:
 */
@Component
public class MyRedisManager extends RedisManager {

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private SysConfig sysConfig;

    @Override
    public byte[] get(byte[] key) {
        Object s = redisUtil.get(new String(key));
        if (s == null){
            return null;
        }

        return Base64.getDecoder().decode(s.toString());
    }

    @Override
    public byte[] set(byte[] key, byte[] value, int expire) {
        String val = Base64.getEncoder().encodeToString(value);
        expire= StringUtil.isNotNull(sysConfig.getShiroRedisSaveTime())?sysConfig.getShiroRedisSaveTime():12000;
        redisUtil.set(new String(key),val,expire);
        return value;
    }
}
