package liangyongqi.iam.Controller.api;

import liangyongqi.iam.Ability.ActiveUser;
import liangyongqi.iam.Data.Repository.UserRepository;
import liangyongqi.iam.Util.LogTool;
import liangyongqi.iam.Util.Param;
import liangyongqi.iam.Util.PermissionManager;
import liangyongqi.iam.pojo.RequestBody.ActiveUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ActiveUserApi {
    @Autowired
    private ActiveUser activeUser;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PermissionManager permissionManager;

    /**
     * 激活用户
     *
     * @param activeUserRequest
     * @return responseMap
     * 回参说明：
     * code: 返回码
     * message: 返回信息
     * randomKey: 用户随机密钥
     *
     */
    @PostMapping("/api/ActiveUser")
    public ResponseEntity<Map<String, Object>> activeUser(@RequestBody ActiveUserRequest activeUserRequest){
        LogTool.writelog("liangyongqi.iam.Controller.api.ActiveUser", "activeUser", "/api/ActiveUser");
        Map<String, Object> responseMap = new HashMap<>();
        try {
            String id = activeUserRequest.getId();
            String code = activeUserRequest.getCode();
            // 核心操作
            activeUser.activeUser(id, code);
            // 查询User表中此id条目的ramdomKey
            LogTool.writelog("liangyongqi.iam.Controller.api.ActiveUser", "activeUser", "查询User表中此id条目的ramdomKey");
            String randomKey = userRepository.findById(id).get().getRandomkey();
            responseMap.put("code", "success");
            responseMap.put("message", "用户激活成功");
            responseMap.put("randomKey", randomKey);
            return ResponseEntity.ok(responseMap);
        } catch (Exception e) {
            e.printStackTrace();
            responseMap.put("code", "error");
            responseMap.put("message", "服务器响应-用户激活失败:" + e.getMessage());
            return ResponseEntity.ok(responseMap);
        }

    }

}
