package liangyongqi.iam.Controller.api;

import liangyongqi.iam.Data.Repository.UserRepository;
import liangyongqi.iam.Util.LogTool;
import liangyongqi.iam.Util.Param;
import liangyongqi.iam.Util.RedisTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserHomeInfo {
    @Autowired
    private RedisTokenService redisTokenService;
    private Param param;
    @Autowired
    private UserRepository userRepository;
    /**
     * 获取用户首页信息
     *
     * @param token 用户令牌
     * @return responseMap
     * 回参说明：
     * code: 返回码
     * message: 返回信息
     * userName: 用户名
     */
    @PostMapping("/api/UserHomeInfo")
    public ResponseEntity<Map<String, Object>> getUserHomeInfo(@RequestParam(required = false) String token) {
        LogTool.writelog("liangyongqi.iam.Controller.api.AdminHomeInfo", "getAdminHomeInfo", "/api/AdminHomeInfo");
        Map<String, Object> responseMap = new HashMap<>();
        try {
            // 处理 token
            String RequestToken = token;
            if (Param.isNull(RequestToken)) {
                responseMap.put("code", "failed");
                responseMap.put("message", "用户令牌为空");
                return ResponseEntity.ok(responseMap);
            }
            // 处理 userId
            String userId = redisTokenService.authToken("user", RequestToken);
            if (Param.isNull(userId)) {
                responseMap.put("code", "failed");
                responseMap.put("message", "用户不存在");
                return ResponseEntity.ok(responseMap);
            }
            // 获取用户信息
            String userName = userRepository.findById(userId).get().getName();

            Map<String, Object> data = new HashMap<>();
            data.put("userId", userId);
            data.put("userName", userName);

            responseMap.put("data", data);
            responseMap.put("code", "success");
            responseMap.put("message", "获取成功");
            return ResponseEntity.ok(responseMap);
        } catch (Exception e) {
            responseMap.put("code", "error");
            responseMap.put("message", "系统错误:" + e.getMessage());
            return ResponseEntity.ok(responseMap);
        }
    }
}
