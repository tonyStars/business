package com.club.business.common.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis服务类
 * @author Tom
 * @date 2019-11-16
 */
@Component
public class RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 指定缓存失效时间
     * @param key 键
     * @param expireTime 时间(秒)
     * @return
     */
    public boolean expire(String key, long expireTime) {
        try {
            if (expireTime > 0) {
                redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取值
     * @param key
     * @return
     */
    public Object get(String key){
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 字符串写入rdis
     * @param key
     * @param value
     * @return
     */
    public boolean set(String key, Object value){
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 字符串写入rdis并设置过期时间
     * @param key
     * @param value
     * @param expireTime 过期时间
     * @return
     */
    public boolean set(String key, Object value, long expireTime){
        try {
            if (expireTime > 0) {
                set(key, value);
            } else {
                redisTemplate.opsForValue().set(key, value, expireTime, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取map的值
     * @param key
     * @param item
     * @return
     */
    public Object hget(String key, String item){
        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     * 设置map中键为item的值，如果不存在将创建
     * @param key
     * @param item
     * @param value
     * @return
     */
    public boolean hset(String key, String item, Object value){
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 设置map中键为item的值，如果不存在将创建
     * @param key
     * @param item
     * @param value
     * @param expireTime
     * @return
     */
    public boolean hset(String key, String item, Object value, long expireTime){
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if (expireTime > 0) {
                expire(key, expireTime);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取map
     * @param key
     * @return
     */
    public Map<Object, Object> hmget(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 设置map
     * @param key
     * @param map
     * @return
     */
    public boolean hmset(String key, Map<String, Object> map){
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 设置map,并设置过期时间
     * @param key
     * @param map
     * @param expireTime
     * @return
     */
    public boolean hmset(String key, Map<String, Object> map, long expireTime){
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (expireTime > 0) {
                expire(key, expireTime);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 删除map中的键对应的值
     * @param key
     * @param item
     * @return
     */
    public boolean hdel(String key, String...item){
        try {
            redisTemplate.opsForHash().delete(key, item);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取set集合
     * @param key
     * @return
     */
    public Set<Object> sget(String key){
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 设置set
     * @param key
     * @param value
     * @return
     */
    public long sset(String key, Set<Object> value){
        try {
            return redisTemplate.opsForSet().add(key, value);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 设置set,并生物质过期时间
     * @param key
     * @param expireTime
     * @param values
     * @return
     */
    public long sset(String key, long expireTime, Object...values){
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            if (expireTime > 0) {
                expire(key, expireTime);
            }
            return  count;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 删除set中的值
     * @param key
     * @param values
     * @return
     */
    public long sdel(String key, Object...values){
        try {
            return redisTemplate.opsForSet().remove(key, values);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 获取list
     * @param key
     * @return
     */
    public List<Object> lget(String key){
        try {
            return redisTemplate.opsForList().range(key, 0, -1);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取从start到end的list
     * @param key
     * @param start
     * @param end
     * @return
     */
    public List<Object> lget(String key, long start, long end){
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取索引处list的值
     * @param key
     * @param index
     * @return
     */
    public Object lgetByIndx(String key, long index){
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 将值放入list
     * @param key
     * @param value
     * @return
     */
    public boolean lset(String key, Object value){
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            return  false;
        }
    }

    /**
     * 将值放入list,并设置过期时间
     * @param key
     * @param value
     * @param expireTime
     * @return
     */
    public boolean lset(String key, Object value, long expireTime){
        try {
            redisTemplate.opsForList().rightPush(key, value);
            if (expireTime > 0) {
                expire(key, expireTime);
            }
            return true;
        } catch (Exception e) {
            return  false;
        }
    }

    /**
     * 将list放入redis
     * @param key
     * @param values
     * @return
     */
    public boolean lset(String key, List<Object> values){
        try {
            redisTemplate.opsForList().rightPushAll(key, values);
            return true;
        } catch (Exception e) {
            return  false;
        }
    }

    /**
     * 将list放入redis,并设置到期时间
     * @param key
     * @param values
     * @param expireTime
     * @return
     */
    public boolean lset(String key, List<Object> values, long expireTime){
        try {
            redisTemplate.opsForList().rightPushAll(key, values);
            if (expireTime > 0) {
                expire(key, expireTime);
            }
            return true;
        } catch (Exception e) {
            return  false;
        }
    }

    /**
     * 递增
     * @param key
     * @param delta
     * @return
     */
    public long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0！");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

}
