package liangyongqi.iam.Controller.api;

import liangyongqi.iam.Ability.DeleteUserGroupApplication;
import liangyongqi.iam.Util.LogTool;
import liangyongqi.iam.Util.PermissionManager;
import liangyongqi.iam.pojo.RequestBody.DeleteUserGroupApplicationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class DeleteUserGroupApplicationApi {
    @Autowired
    private DeleteUserGroupApplication deleteUserGroupApplication;
    @Autowired
    private PermissionManager permissionManager;

    /**
     * 删除用户组应用
     * @param deleteUserGroupApplicationRequest
     * @return
     */
    @PostMapping("/api/deleteUserGroupApplication")
    public ResponseEntity<Map<String, Object>> deleteUserGroupApplication(@RequestBody DeleteUserGroupApplicationRequest deleteUserGroupApplicationRequest) {
        LogTool.writelog("liangyongqi.iam.Controller.api.DeleteUserGroupApplicationApi", "deleteUserGroupApplication", "/api/deleteUserGroupApplication");
        Map<String, Object> responseMap = new HashMap<>();
        try {
            // 先验证管理员token
            String tokenAuthResult = permissionManager.checkAdminPermission(deleteUserGroupApplicationRequest.getToken());
            if (tokenAuthResult != "0") {
                responseMap.put("code", "failed");
                responseMap.put("message", "管理员权限验证失败:" + tokenAuthResult);
                return ResponseEntity.ok(responseMap);
            }
            String value = deleteUserGroupApplication.deleteUserGroupApplication(deleteUserGroupApplicationRequest.getGroupId(), deleteUserGroupApplicationRequest.getApplicationId());
            if (value != "success") {
                responseMap.put("status", "failed");
                responseMap.put("message", "用户组应用删除失败");
                return ResponseEntity.ok(responseMap);
            } else {
                responseMap.put("status", "success");
                responseMap.put("message", "用户组应用删除成功");
                return ResponseEntity.ok(responseMap);
            }

        } catch (Exception e) {
            responseMap.put("status", "failed");
            responseMap.put("message", "用户组应用删除失败");
            return ResponseEntity.ok(responseMap);
        }
    }


}
