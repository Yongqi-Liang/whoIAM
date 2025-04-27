package liangyongqi.iam.Controller.api;

import liangyongqi.iam.Ability.SetFaceID;
import liangyongqi.iam.pojo.RequestBody.SetFaceidRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
public class SetFaceidApi {
    @Autowired
    private SetFaceID setFaceID;
    @PostMapping("/api/faceid/set")
    public ResponseEntity<Map<String, Object>> setFaceID(@RequestBody SetFaceidRequest setFaceidRequest) {
        Map<String, Object> responseMap = new HashMap<>();
        try {
            String uid = setFaceidRequest.getUid();
            byte[] featureVector = setFaceidRequest.getFeatureVector();
            if (Objects.isNull(uid) || Objects.isNull(featureVector)) {
                responseMap.put("status", "failed");
                responseMap.put("message", "参数错误");
                return ResponseEntity.ok(responseMap);
            } else {
                String result = setFaceID.setFaceID(uid, featureVector);
                if (result.equals("success")) {
                    responseMap.put("status", "success");
                    responseMap.put("message", "设置成功");
                    return ResponseEntity.ok(responseMap);
                } else {
                    // setFaceID 函数返回错误信息
                    responseMap.put("status", "error");
                    responseMap.put("message", result);
                    return ResponseEntity.ok(responseMap);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseMap.put("status", "error");
            responseMap.put("message", "操作失败: " + e.getMessage());
            return ResponseEntity.ok(responseMap);
        }
    }
}
