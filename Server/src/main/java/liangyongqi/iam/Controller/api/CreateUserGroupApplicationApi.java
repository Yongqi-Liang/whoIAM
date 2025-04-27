package liangyongqi.iam.Controller.api;

import liangyongqi.iam.Util.LogTool;
import liangyongqi.iam.Util.PermissionManager;
import liangyongqi.iam.pojo.RequestBody.CreateUserGroupApplicationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import liangyongqi.iam.Ability.CreateUserGroupApplication;

import java.util.HashMap;
import java.util.Map;

@RestController
public class CreateUserGroupApplicationApi {
    @Autowired
    private CreateUserGroupApplication createUserGroupApplication;
    @Autowired
    private PermissionManager permissionManager;

    /**
     * 创建用户组应用
     * @param createUserGroupApplicationRequest
     * @return
     */
    @PostMapping("/api/createUserGroupApplication")
    public ResponseEntity<Map<String, Object>> createUserGroupApplication(@RequestBody CreateUserGroupApplicationRequest createUserGroupApplicationRequest) {
        LogTool.writelog("liangyongqi.iam.Controller.api.CreateUserGroupApplicationApi", "createUserGroupApplication", "/api/createUserGroupApplication");
        Map<String, Object> responseMap = new HashMap<>();
        try {
            // 先验证管理员token
            String tokenAuthResult = permissionManager.checkAdminPermission(createUserGroupApplicationRequest.getToken());
            if (tokenAuthResult != "0"){
                responseMap.put("code", "failed");
                responseMap.put("message", "管理员权限验证失败:" + tokenAuthResult);
                return ResponseEntity.ok(responseMap);
            }
            String value = createUserGroupApplication.createUserGroupApplication(createUserGroupApplicationRequest.getGroupId(), createUserGroupApplicationRequest.getApplicationId());
            if (value != "success") {
                responseMap.put("status", "failed");
                responseMap.put("message", "用户组应用创建失败");
                return ResponseEntity.ok(responseMap);
            } else {
                responseMap.put("status", "success");
                responseMap.put("message", "用户组应用创建成功");
                return ResponseEntity.ok(responseMap);
            }
        } catch (Exception e) {
            responseMap.put("status", "failed");
            responseMap.put("message", "用户组应用创建失败");
            return ResponseEntity.ok(responseMap);
        }

    }

}
