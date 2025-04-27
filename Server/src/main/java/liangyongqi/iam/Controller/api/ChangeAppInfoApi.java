package liangyongqi.iam.Controller.api;

import liangyongqi.iam.Ability.ChangeAppInfo;
import liangyongqi.iam.Util.LogTool;
import liangyongqi.iam.Util.PermissionManager;
import liangyongqi.iam.pojo.RequestBody.ChangeAppInfoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ChangeAppInfoApi {
    @Autowired
    private ChangeAppInfo changeAppInfo;
    @Autowired
    private PermissionManager permissionManager;
    /**
     * 修改应用信息
     * @param changeAppInfoRequest
     * @return
     */
    @PostMapping("/api/changeAppInfo")
    public ResponseEntity<Map<String, Object>> changeAppInfo(@RequestBody ChangeAppInfoRequest changeAppInfoRequest) {
        LogTool.writelog("liangyongqi.iam.Controller.api.ChangeAppInfoApi.changeAppInfo", "changeAppInfo", "/api/changeAppInfo");
        Map<String, Object> responseMap = new HashMap<>();
        try {
            // 先验证管理员token
            String tokenAuthResult = permissionManager.checkAdminPermission(changeAppInfoRequest.getToken());
            if (tokenAuthResult != "0"){
                responseMap.put("code", "failed");
                responseMap.put("message", "管理员权限验证失败:" + tokenAuthResult);
                return ResponseEntity.ok(responseMap);
            }

            // 核心操作
            String result = changeAppInfo.changeAppInfo(changeAppInfoRequest.getAppid(), changeAppInfoRequest.getName(), changeAppInfoRequest.getDesc(), changeAppInfoRequest.getUri());
            if (result == "success") {
                responseMap.put("code", "success");
                responseMap.put("message", "修改成功");
                return ResponseEntity.ok(responseMap);
            } else {
                responseMap.put("code", "failed");
                responseMap.put("message", "修改失败：" + result);
                return ResponseEntity.ok(responseMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseMap.put("code", "error");
            responseMap.put("message", "修改失败：" + e.getMessage());
            return ResponseEntity.ok(responseMap);
        }

    }

}
