package liangyongqi.iam.Controller.api;

import liangyongqi.iam.Ability.GetUserGroupApplication;
import liangyongqi.iam.Data.Entity.UserGroupApplication;
import liangyongqi.iam.Util.LogTool;
import liangyongqi.iam.Util.PermissionManager;
import liangyongqi.iam.pojo.RequestBody.GetUserGroupApplicationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class GetUserGroupApplicationApi {
    @Autowired
    private GetUserGroupApplication getUserGroupApplication;
    @Autowired
    private PermissionManager permissionManager;

    /**
     * 获取用户组应用
     * @param getUserGroupApplicationRequest
     * @return
     */
    @PostMapping("/api/getUserGroupApplication")
    public ResponseEntity<Map<String, Object>> getUserGroupApplication(@RequestBody GetUserGroupApplicationRequest getUserGroupApplicationRequest) {
        LogTool.writelog("liangyongqi.iam.Controller.api.GetUserGroupApplicationApi", "getUserGroupApplication", "/api/getUserGroupApplication");
        Map<String, Object> responseMap = new HashMap<>();
        try {
            // 先验证管理员token
            String tokenAuthResult = permissionManager.checkAdminPermission(getUserGroupApplicationRequest.getToken());
            if (tokenAuthResult != "0") {
                responseMap.put("code", "failed");
                responseMap.put("message", "管理员权限验证失败:" + tokenAuthResult);
                return ResponseEntity.ok(responseMap);
            }
            List<UserGroupApplication> data = getUserGroupApplication.getUserGroupApplication(getUserGroupApplicationRequest.getGroupId());
            if (data == null) {
                responseMap.put("status", "failed");
                responseMap.put("message", "用户组应用获取失败");
                return ResponseEntity.ok(responseMap);
            } else {
                responseMap.put("status", "success");
                responseMap.put("message", "用户组应用获取成功");
                responseMap.put("data", data);
                return ResponseEntity.ok(responseMap);
            }
        } catch (Exception e) {
            responseMap.put("status", "failed");
            responseMap.put("message", "用户组应用获取失败");
            return ResponseEntity.ok(responseMap);
        }
    }
}
