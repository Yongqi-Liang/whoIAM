package liangyongqi.iam.Controller.api;

import liangyongqi.iam.Ability.CreateApp;
import liangyongqi.iam.Util.LogTool;
import liangyongqi.iam.Util.PermissionManager;
import liangyongqi.iam.pojo.RequestBody.CreateAppRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class CreateAppApi {
    @Autowired
    private CreateApp createApp;
    @Autowired
    private PermissionManager permissionManager;
    /**
     * 创建App
     * @param createAppRequest
     * @return
     */
    @PostMapping("/api/createApp")
    public ResponseEntity<Map<String, Object>> createApp(@RequestBody CreateAppRequest createAppRequest) {
        LogTool.writelog("liangyongqi.iam.Controller.api.CreateAppApi", "createApp", "/api/createApp");
        Map<String, Object> responseMap = new HashMap<>();
        try {
            // 先验证管理员token
            String tokenAuthResult = permissionManager.checkAdminPermission(createAppRequest.getToken());
            if (tokenAuthResult != "0"){
                responseMap.put("code", "failed");
                responseMap.put("message", "管理员权限验证失败:" + tokenAuthResult);
                return ResponseEntity.ok(responseMap);
            }

            String value = createApp.createApp(createAppRequest.getId(), createAppRequest.getName(), createAppRequest.getDesc(), createAppRequest.getUri());
            if (value == null) {
                responseMap.put("status", "failed");
                responseMap.put("message", value);
                return ResponseEntity.ok(responseMap);
            } else {
                responseMap.put("status", "success");
                responseMap.put("message", "App创建成功");
                return ResponseEntity.ok(responseMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseMap.put("status", "failed");
            responseMap.put("message", "App创建失败");
            return ResponseEntity.ok(responseMap);
        }

    }

}
