package liangyongqi.iam.Controller.api;

import liangyongqi.iam.Util.LogTool;
import liangyongqi.iam.Util.PermissionManager;
import liangyongqi.iam.Util.PropertiesEditor;
import liangyongqi.iam.pojo.RequestBody.SetPropRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class PropertiesApi {

    @Autowired
    private PermissionManager permissionManager;

    @Autowired
    private PropertiesEditor propertiesEditor; // 注入PropertiesEditor实例

    /**
     * 读取配置文件（所有项目）
     * @param token
     */
    @GetMapping("/api/getProp")
    public ResponseEntity<Map<String, Object>> getProp(@RequestParam String token) {
        LogTool.writelog("liangyongqi.iam.Controller.api.PropertiesApi", "getProp", "/api/getProp");
        Map<String, Object> responseMap = new HashMap<>();
        try {
            // 先验证管理员token
            String tokenAuthResult = permissionManager.checkAdminPermission(token);
            if (!"0".equals(tokenAuthResult)) { // 使用equals方法比较字符串
                responseMap.put("code", "failed");
                responseMap.put("message", "管理员权限验证失败:" + tokenAuthResult);
                return ResponseEntity.ok(responseMap);
            }
            // 核心操作
            Map<String, String> data = propertiesEditor.readProperties(); // 通过注入的实例调用方法
            responseMap.put("code", "success");
            responseMap.put("message", "获取配置文件成功");
            responseMap.put("data", data);
            return ResponseEntity.ok(responseMap);
        } catch (Exception e) {
            e.printStackTrace();
            responseMap.put("code", "error");
            responseMap.put("message", "获取配置文件失败");
            return ResponseEntity.ok(responseMap);
        }
    }

    /**
     * 修改配置文件
     * @param setPropRequest
     */
    @PostMapping("/api/setProp")
    public ResponseEntity<Map<String, Object>> setProp(@RequestBody SetPropRequest setPropRequest) {
        LogTool.writelog("liangyongqi.iam.Controller.api.PropertiesApi", "setProp", "/api/setProp");
        Map<String, Object> responseMap = new HashMap<>();
        try {
            // 先验证管理员token
            String tokenAuthResult = permissionManager.checkAdminPermission(setPropRequest.getToken());
            if (!"0".equals(tokenAuthResult)) { // 使用equals方法比较字符串
                responseMap.put("code", "failed");
                responseMap.put("message", "管理员权限验证失败:" + tokenAuthResult);
                return ResponseEntity.ok(responseMap);
            }
            // 核心操作
            String configName = setPropRequest.getConfigName();
            String configValue = setPropRequest.getConfigValue();
            boolean result = propertiesEditor.updateConfig(configName, configValue); // 通过注入的实例调用方法
            if (result) {
                responseMap.put("code", "success");
                responseMap.put("message", "修改配置文件成功");
                return ResponseEntity.ok(responseMap);
            } else {
                responseMap.put("code", "failed");
                responseMap.put("message", "修改配置文件失败");
                return ResponseEntity.ok(responseMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseMap.put("code", "error");
            responseMap.put("message", "修改配置文件失败");
            return ResponseEntity.ok(responseMap);
        }
    }
}