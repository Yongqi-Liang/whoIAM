package liangyongqi.iam.Controller.api;

import liangyongqi.iam.Ability.UserGroupList;
import liangyongqi.iam.Util.LogTool;
import liangyongqi.iam.Util.PermissionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserGroupListApi {
    @Autowired
    private UserGroupList userGroupList;
    @Autowired
    private PermissionManager permissionManager;
    /**
     * 获取用户组列表
     * @param token 管理员token
     * @return 用户组列表
     *
     */
    @GetMapping("/api/UserGroupList")
    public ResponseEntity<Map<String, Object>> userGroupList(@RequestParam String token) {
        LogTool.writelog("UserGroupListApi", "userGroupList", "/api/UserGroupList");
        Map<String, Object> responseMap = new HashMap<>();
        try {
            // 先验证管理员token
            String tokenAuthResult = permissionManager.checkAdminPermission(token);
            if (tokenAuthResult != "0"){
                responseMap.put("code", "failed");
                responseMap.put("message", "管理员权限验证失败:" + tokenAuthResult);
                return ResponseEntity.ok(responseMap);
            }

            responseMap.put("code", "success");
            responseMap.put("message", "用户组列表获取成功");
            responseMap.put("data", userGroupList.getUserGroupList());
            return ResponseEntity.ok(responseMap);
        } catch (Exception e) {
            responseMap.put("code", "failed");
            responseMap.put("message", "用户组列表获取失败:" + e.getMessage());
            return ResponseEntity.ok(responseMap);
        }
    }


}
