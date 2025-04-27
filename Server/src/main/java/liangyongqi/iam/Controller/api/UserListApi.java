package liangyongqi.iam.Controller.api;

import liangyongqi.iam.Ability.UserList;
import liangyongqi.iam.Util.LogTool;
import liangyongqi.iam.Util.PermissionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserListApi {
    @Autowired
    private UserList userList;
    @Autowired
    private PermissionManager permissionManager;

    @GetMapping("/api/UserListApi")
    public ResponseEntity<Map<String, Object>> getUserList(@RequestParam String token) {
        LogTool.writelog("liangyongqi.iam.Controller.api.UserListApi", "getUserList", "/api/UserListApi");
        Map<String, Object> responseMap = new HashMap<>();
        try {
            // 先验证管理员token
            String tokenAuthResult = permissionManager.checkAdminPermission(token);
            if (tokenAuthResult != "0"){
                responseMap.put("code", "failed");
                responseMap.put("message", "管理员权限验证失败:" + tokenAuthResult);
                return ResponseEntity.ok(responseMap);
            }
            // 核心操作
            Map<String, Object> data = userList.getUserList();
            responseMap.put("code", "success");
            responseMap.put("message", "获取用户列表成功");
            responseMap.put("data", data);
            return ResponseEntity.ok(responseMap);
        } catch (Exception e) {
            responseMap.put("code", "error");
            responseMap.put("message", "获取用户列表失败：" + e.getMessage());
            return ResponseEntity.ok(responseMap);
        }
    }




}
