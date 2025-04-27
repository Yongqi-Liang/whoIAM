package liangyongqi.iam.Util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class RedisTokenService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    // Token 的有效期，单位为秒
    private static final long TOKEN_EXPIRATION = 3600;

    /**
     * 生成 Token 并存入 Redis
     *
     * @param userId 用户登录 ID
     * @param appId  应用 ID
     * @return token
     */
    public String setToken(String userId, String appId) {
        LogTool.writelog("liangyongqi.iam.Util.RedisTokenService", "setToken", "生成 Token 并存入 Redis");
        try {
            // 查询redis中此userId和appId是否有其他的token，如果有则删除
            stringRedisTemplate.delete("auth:token:" + userId + ":" + appId);
        } catch (Exception e) {
            LogTool.writeError(e.getMessage());
        }


        // 生成唯一的 Token
        String token = UUID.randomUUID().toString();

        // Redis 中存储的键是 token，值是 JSON 格式，包含 userId 和 appId
        String redisKey = "auth:token:" + token;
        String redisValue = String.format("{\"userId\":\"%s\",\"appId\":\"%s\"}", userId, appId);

        // 使用 Redis 的 set 操作，并设置过期时间
        stringRedisTemplate.opsForValue().set(redisKey, redisValue, TOKEN_EXPIRATION, TimeUnit.SECONDS);

        // 返回生成的 Token
        return token;
    }

    /**
     * 验证 Token 并返回 userId
     * @param appId
     * @param token
     * @return userId
     */
    public String authToken(String appId, String token) {
        // Redis 中存储的键是 token，值是 JSON 格式
        String redisKey = "auth:token:" + token;
        // 从 Redis 获取存储的值
        String redisValue = stringRedisTemplate.opsForValue().get(redisKey);

        if (redisValue != null) {
            // 解析 Redis 存储的 JSON，提取 userId 和 appId
            try {
                Map<String, String> data = new ObjectMapper().readValue(redisValue, new TypeReference<Map<String, String>>() {});
                String storedAppId = data.get("appId");
                String userId = data.get("userId");

                // 验证 appId 是否匹配
                if (appId.equals(storedAppId)) {
                    // 重新设置 token 的过期时间
                    stringRedisTemplate.expire(redisKey, TOKEN_EXPIRATION, TimeUnit.SECONDS);
                    return userId; // 返回有效的 userId
                }
            } catch (JsonProcessingException e) {
                LogTool.writeError(e.getMessage());
            }
        }
        return null; // token 无效或 appId 不匹配
    }

}

