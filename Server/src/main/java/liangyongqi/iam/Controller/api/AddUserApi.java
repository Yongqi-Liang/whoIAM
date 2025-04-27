package liangyongqi.iam.Controller.api;

import liangyongqi.iam.Ability.CreateUser;
import liangyongqi.iam.Util.LogTool;
import liangyongqi.iam.Util.PermissionManager;
import liangyongqi.iam.pojo.RequestBody.AddUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AddUserApi {
    @Autowired
    private PermissionManager permissionManager;
    @Autowired
    private CreateUser createUser;
    /**
     * 添加用户
     * @param addUserRequest
     * @return
     */
    @PostMapping("/api/AddUser")
    public ResponseEntity<Map<String, Object>> addUser(@RequestBody AddUserRequest addUserRequest) {
        LogTool.writelog("liangyongqi.iam.Controller.api.AddUser", "addUser", "/api/AddUser");
        Map<String, Object> responseMap = new HashMap<>();
        try {
            // 先验证管理员token
            String tokenAuthResult = permissionManager.checkAdminPermission(addUserRequest.getToken());
            if (tokenAuthResult != "0"){
                responseMap.put("code", "failed");
                responseMap.put("message", "管理员权限验证失败:" + tokenAuthResult);
                return ResponseEntity.ok(responseMap);
            }
            // 核心操作
            String key = createUser.createUser(addUserRequest.getId(), addUserRequest.getUsername(), addUserRequest.getEmail(), addUserRequest.getUsergroup());
            if (key == null){
                responseMap.put("code", "failed");
                responseMap.put("message", "用户已存在");
                return ResponseEntity.ok(responseMap);
            }
            responseMap.put("code", "success");
            responseMap.put("message", "用户创建成功");
            responseMap.put("key", key);
            return ResponseEntity.ok(responseMap);
        } catch (Exception e) {
            e.printStackTrace();
            responseMap.put("code", "error");
            responseMap.put("message", "用户创建失败");
            return ResponseEntity.ok(responseMap);
        }
    }
}
