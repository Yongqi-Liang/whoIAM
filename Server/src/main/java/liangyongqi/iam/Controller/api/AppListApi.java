package liangyongqi.iam.Controller.api;

import liangyongqi.iam.Ability.AppList;
import liangyongqi.iam.Util.LogTool;
import liangyongqi.iam.Util.PermissionManager;
import liangyongqi.iam.pojo.RequestBody.appListRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AppListApi {
    @Autowired
    private AppList appList;
    @Autowired
    private PermissionManager permissionManager;
    /**
     * 获取应用列表
     * @return
     */
    @PostMapping("/api/appList")
    public ResponseEntity<Map<String, Object>> appList() {
        LogTool.writelog("liangyongqi.iam.Controller.api.AppListApi.appList", "appList", "/api/appList");
        Map<String, Object> responseMap = new HashMap<>();
        try {
            responseMap.put("code", "success");
            responseMap.put("message", "应用列表获取成功");
            responseMap.put("data", appList.getAppList());
            return ResponseEntity.ok(responseMap);
        } catch (Exception e) {
            responseMap.put("code", "failed");
            responseMap.put("message", "应用列表获取失败:" + e.getMessage());
            return ResponseEntity.ok(responseMap);
        }

    }

}
