package liangyongqi.iam.Controller.api;

import liangyongqi.iam.Ability.AuthFaceID;
import liangyongqi.iam.pojo.RequestBody.AuthFaceidRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthFaceidApi {
    @Autowired
    private AuthFaceID authFaceID;
    @PostMapping("/api/faceid/auth")
    public ResponseEntity<Map<String, Object>> authFaceID(@RequestBody AuthFaceidRequest authFaceidRequest) {
        Map<String, Object> responseMap = new HashMap<>();
        try {
            byte[] featureVector = authFaceidRequest.getFeatureVector();
            if (featureVector == null) {
                responseMap.put("status", "failed");
                responseMap.put("message", "参数为空");
                return ResponseEntity.ok(responseMap);
            } else {
                Boolean result = authFaceID.authFaceID(featureVector);
                if (result) {
                    responseMap.put("status", "success");
                    responseMap.put("message", "验证成功");
                    return ResponseEntity.ok(responseMap);
                } else {
                    responseMap.put("status", "failed");
                    responseMap.put("message", "验证失败");
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
