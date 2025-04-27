package liangyongqi.iam.Controller.api;

import liangyongqi.iam.Ability.DisableUser;
import liangyongqi.iam.Util.PermissionManager;
import liangyongqi.iam.pojo.RequestBody.DisableUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class DisableUserApi {
    @Autowired
    private PermissionManager permissionManager;
    @Autowired
    DisableUser disableUser;

    @PostMapping("/api/disableUser")
    public ResponseEntity<Map<String, Object>> disableUser(@RequestBody DisableUserRequest disableUserRequest) {
        Map<String, Object> responseMap = new HashMap<>();
        try {
            // 验证管理员权限
            String permissionAuthResult = permissionManager.checkAdminPermission(disableUserRequest.getToken());
            if (!permissionAuthResult.equals("0")) {
                responseMap.put("code", "failed");
                responseMap.put("message", "用户禁用失败：" + permissionAuthResult);
                return ResponseEntity.ok(responseMap);
            }
            // 禁用用户
            disableUser.disableUser(disableUserRequest.getId());
            responseMap.put("code", "success");
            responseMap.put("message", "用户禁用成功");
            return ResponseEntity.ok(responseMap);
        } catch (Exception e) {
            e.printStackTrace();
            responseMap.put("code", "error");
            responseMap.put("message", "用户禁用失败：" + e.getMessage());
            return ResponseEntity.ok(responseMap);
        }
    }
}
