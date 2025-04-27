package liangyongqi.iam.Controller.api;

import liangyongqi.iam.Util.LogTool;
import liangyongqi.iam.pojo.RequestBody.LoginRequest;
import liangyongqi.iam.Util.RedisTokenService;
import liangyongqi.iam.pojo.RequestBody.TokenAuthRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AppReadTokenApi {

    @Autowired
    private RedisTokenService redisTokenService;

    /**
     * Token验证
     * 用途：验证用户Token是否有效
     * @param tokenAuthRequest
     * @return responseMap
     * 回参说明：
     * code: 返回码
     * message: 返回信息
     * userId: 用户ID
     */
    @PostMapping("/api/AppLogin/TokenAuth")
    public ResponseEntity<Map<String, Object>> login(@RequestBody TokenAuthRequest tokenAuthRequest) {
        LogTool.writelog("liangyongqi.iam.Controller.api.AppReadTokenApi", "login", "/api/AppLogin/TokenAuth");
        Map<String, Object> responseMap = new HashMap<>();
        try {
            // 验证 Token
            String appId = tokenAuthRequest.getAppId();
            String token = tokenAuthRequest.getToken();
            String userId = redisTokenService.authToken(appId, token);
            if (userId != null) {
                responseMap.put("code", "success");
                responseMap.put("message", "Token验证成功");
                responseMap.put("userId", userId);
                return ResponseEntity.ok(responseMap);
            } else {
                responseMap.put("code", "failed");
                responseMap.put("message", "Token验证失败");
                return ResponseEntity.ok(responseMap);
            }

        } catch (Exception e) {
            responseMap.put("code", "error");
            responseMap.put("message", "系统错误:" + e.getMessage());
            return ResponseEntity.ok(responseMap);
        }
    }
}
