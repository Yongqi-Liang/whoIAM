package liangyongqi.iam.Controller.api;

import liangyongqi.iam.Ability.CreateUserGroup;
import liangyongqi.iam.Util.LogTool;
import liangyongqi.iam.Util.PermissionManager;
import liangyongqi.iam.pojo.RequestBody.CreateUserGroupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class CreateUserGroupApi {
    @Autowired
    private CreateUserGroup createUserGroup;
    @Autowired
    private PermissionManager permissionManager;

    @PostMapping("/api/CreateUserGroup")
    public ResponseEntity<Map<String, Object>> createUserGroup(@RequestBody CreateUserGroupRequest createUserGroupRequest) {
        LogTool.writelog("liangyongqi.iam.Controller.api.CreateUserGroup", "createUserGroup", "/api/CreateUserGroup");
        Map<String, Object> responseMap = new HashMap<>();
        try {
            // 先验证管理员token
            String tokenAuthResult = permissionManager.checkAdminPermission(createUserGroupRequest.getToken());
            if (tokenAuthResult != "0"){
                responseMap.put("code", "failed");
                responseMap.put("message", "管理员权限验证失败:" + tokenAuthResult);
                return ResponseEntity.ok(responseMap);
            }
            // 核心操作
            String result = createUserGroup.createUserGroup(createUserGroupRequest.getGid(), createUserGroupRequest.getGname(), createUserGroupRequest.getGdesc());
            if (result == "success") {
                responseMap.put("code", "success");
                responseMap.put("message", "用户组创建成功");
                return ResponseEntity.ok(responseMap);
            } else {
                responseMap.put("code", "failed");
                responseMap.put("message", "用户组创建失败:" + result);
                return ResponseEntity.ok(responseMap);
            }
        } catch (Exception e) {
            responseMap.put("code", "failed");
            responseMap.put("message", "用户组创建失败:" + e.getMessage());
            return ResponseEntity.ok(responseMap);
        }
    }
}
