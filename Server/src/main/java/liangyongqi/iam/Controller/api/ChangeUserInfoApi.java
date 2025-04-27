package liangyongqi.iam.Controller.api;

import liangyongqi.iam.Ability.ChangeUserInfo;
import liangyongqi.iam.Util.PermissionManager;
import liangyongqi.iam.pojo.RequestBody.ChangeUserInfoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ChangeUserInfoApi {

    @Autowired
    private PermissionManager permissionManager;
    @Autowired
    private ChangeUserInfo changeUserInfo;

    @PostMapping("/api/ChangeUserInfo")
    public ResponseEntity<Map<String, Object>> changeUserInfo(@RequestBody ChangeUserInfoRequest changeUserInfoRequest) {
        Map<String, Object> responseMap = new HashMap<>();
        try {
            // 验证管理员权限
            String permissionAuthResult = permissionManager.checkAdminPermission(changeUserInfoRequest.getToken());
            if (!permissionAuthResult.equals("0")) {
                responseMap.put("code", "failed");
                responseMap.put("message", "用户信息修改失败：" + permissionAuthResult);
                return ResponseEntity.ok(responseMap);
            }
            changeUserInfo.changeUserInfo(changeUserInfoRequest.getId(), changeUserInfoRequest.getUsername(), changeUserInfoRequest.getEmail(), changeUserInfoRequest.getGroupid());
            responseMap.put("code", "success");
            responseMap.put("message", "用户信息修改成功");
            return ResponseEntity.ok(responseMap);

        } catch (Exception e) {
            responseMap.put("code", "error");
            responseMap.put("message", "用户信息修改失败：" + e.getMessage());
            return ResponseEntity.ok(responseMap);
        }
        
    }
}
