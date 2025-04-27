package liangyongqi.iam.Controller.api;

import liangyongqi.iam.Ability.ChangeUserGroupInfo;
import liangyongqi.iam.Ability.ChangeUserInfo;
import liangyongqi.iam.Data.Repository.UserGroupRepository;
import liangyongqi.iam.Util.LogTool;
import liangyongqi.iam.Util.PermissionManager;
import liangyongqi.iam.pojo.RequestBody.ChangeUserGroupInfoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ChangeUserGroupInfoApi {
    @Autowired
    private ChangeUserGroupInfo changeUserGroupInfo;
    @Autowired
    private PermissionManager permissionManager;

    /**
     * 修改用户组信息
     * @param changeUserGroupInfoRequest
     * @return
     */
    @PostMapping("/api/ChangeUserGroupInfo")
    public ResponseEntity<Map<String, Object>> changeUserGroupInfo(@RequestBody ChangeUserGroupInfoRequest changeUserGroupInfoRequest) {
        LogTool.writelog("ChangeUserGroupInfoApi", "changeUserGroupInfo","/api/ChangeUserGroupInfo");
        Map<String, Object> responseMap = new HashMap<>();

        try {
            // 先验证管理员token
            String tokenAuthResult = permissionManager.checkAdminPermission(changeUserGroupInfoRequest.getToken());
            if (tokenAuthResult != "0"){
                responseMap.put("code", "failed");
                responseMap.put("message", "管理员权限验证失败:" + tokenAuthResult);
                return ResponseEntity.ok(responseMap);
            }
            // 核心操作
            changeUserGroupInfo.changeUserGroupInfo(changeUserGroupInfoRequest.getGid(), changeUserGroupInfoRequest.getGname(), changeUserGroupInfoRequest.getGdesc());
            responseMap.put("code", "success");
            responseMap.put("message", "用户组信息修改成功");
            return ResponseEntity.ok(responseMap);
        } catch (Exception e) {
            responseMap.put("code", "failed");
            responseMap.put("message", "用户组信息修改失败:" + e.getMessage());
            return ResponseEntity.ok(responseMap);
        }

    }
}
