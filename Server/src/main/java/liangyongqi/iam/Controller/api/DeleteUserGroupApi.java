package liangyongqi.iam.Controller.api;

import jakarta.persistence.Entity;
import liangyongqi.iam.Ability.DeleteUserGroup;
import liangyongqi.iam.Util.LogTool;
import liangyongqi.iam.Util.PermissionManager;
import liangyongqi.iam.pojo.RequestBody.DeleteUserGroupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class DeleteUserGroupApi {
    @Autowired
    private DeleteUserGroup deleteUserGroup;
    @Autowired
    private PermissionManager permissionManager;
    /**
     * 删除用户组
     *
     * @param deleteUserGroupRequest
     * @return responseMap
     * 回参说明：
     * code: 返回码
     * message: 返回信息
     *
     */
    @PostMapping("/api/DeleteUserGroup")
    public ResponseEntity<Map<String, Object>> deleteUserGroup(@RequestBody DeleteUserGroupRequest deleteUserGroupRequest) {
        LogTool.writelog("DeleteUserGroupApi", "deleteUserGroup", deleteUserGroupRequest.toString());
        Map<String, Object> responseMap = new HashMap<>();
        try {
            // 先验证管理员token
            String tokenAuthResult = permissionManager.checkAdminPermission(deleteUserGroupRequest.getToken());
            if (tokenAuthResult != "0"){
                responseMap.put("code", "failed");
                responseMap.put("message", "管理员权限验证失败:" + tokenAuthResult);
                return ResponseEntity.ok(responseMap);
            }

            String result = deleteUserGroup.deleteUserGroup(deleteUserGroupRequest.getGid());
            if (result != "success") {
                responseMap.put("code", "failed");
                responseMap.put("message", "用户组删除失败:" + result);
                return ResponseEntity.ok(responseMap);
            }
            responseMap.put("code", "success");
            responseMap.put("message", "用户组删除成功");
            return ResponseEntity.ok(responseMap);
        } catch (Exception e) {
            responseMap.put("code", "failed");
            responseMap.put("message", "用户组删除失败:" + e.getMessage());
            return ResponseEntity.ok(responseMap);
        }
    }
}
