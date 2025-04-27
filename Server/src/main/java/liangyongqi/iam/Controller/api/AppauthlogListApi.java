package liangyongqi.iam.Controller.api;

import liangyongqi.iam.Ability.AppauthlogList;
import liangyongqi.iam.Data.Entity.Appauthlog;
import liangyongqi.iam.Data.Repository.AppauthlogRepository;
import liangyongqi.iam.Util.PermissionManager;
import liangyongqi.iam.pojo.RequestBody.AppauthlogRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
public class AppauthlogListApi {
    @Autowired
    private AppauthlogList appauthlogList;
    @Autowired
    private PermissionManager permissionManager;
    @Autowired
    private AppauthlogRepository appauthlogRepository;

    /**
     * 获取应用授权日志列表
     *
     * @param appauthlogRequest 应用授权日志请求
     * @return 应用授权日志列表
     */
    @PostMapping("/api/appauthlog/list")
    public ResponseEntity<Map<String, Object>> getAppauthlogList(@RequestBody AppauthlogRequest appauthlogRequest) {
        Map<String, Object> responseMap = new HashMap<>();
        try {
            // 先验证管理员token
            String tokenAuthResult = permissionManager.checkAdminPermission(appauthlogRequest.getToken());
            String uid = appauthlogRequest.getUid();
            String appid = appauthlogRequest.getAppid();
            String authdatetime = appauthlogRequest.getAuthdatetime();
            if (tokenAuthResult != "0"){
                responseMap.put("code", "failed");
                responseMap.put("message", "管理员权限验证失败:" + tokenAuthResult);
                return ResponseEntity.ok(responseMap);
            }
            // 动态构建查询条件
            Specification<Appauthlog> spec = Specification.where(null);
            if (uid != null && !uid.isEmpty()) {
                spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("uid"), uid));
            }
            if (appid != null && !appid.isEmpty()) {
                spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("appid"), appid));
            }
            if (authdatetime != null && !authdatetime.isEmpty()) {
                spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(criteriaBuilder.function("date", String.class, root.get("authdatetime")), authdatetime));
            }

            // 执行查询
            List<Appauthlog> data = appauthlogRepository.findAll(spec);

            responseMap.put("code", "success");
            responseMap.put("message", "获取应用授权日志列表成功");
            responseMap.put("data", data);
        } catch (Exception e) {
            responseMap.put("code", "error");
            responseMap.put("message", "获取应用授权日志列表失败");
        }
        // 使用 ResponseEntity 包装返回值
        return ResponseEntity.ok(responseMap);
    }
}
