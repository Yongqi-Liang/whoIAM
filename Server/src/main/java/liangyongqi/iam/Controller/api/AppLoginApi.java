package liangyongqi.iam.Controller.api;

import liangyongqi.iam.Ability.AddAppauthlog;
import liangyongqi.iam.Data.Entity.Application;
import liangyongqi.iam.Data.Entity.User;
import liangyongqi.iam.Data.Entity.UserApplicationView;
import liangyongqi.iam.Data.Repository.ApplicationRepository;
import liangyongqi.iam.Data.Repository.UserApplicationViewRepo;
import liangyongqi.iam.Data.Repository.UserRepository;
import liangyongqi.iam.pojo.RequestBody.LoginRequest;
import liangyongqi.iam.Util.RedisTokenService;
import liangyongqi.iam.Util.otp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class AppLoginApi {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private UserApplicationViewRepo userApplicationRepository;
    @Autowired
    private RedisTokenService redisTokenService;
    @Autowired
    private AddAppauthlog addAppauthlog;

    /**
     * IAM用户跳转登录应用
     *
     * @param loginRequest
     * @return responseMap
     * 回参说明：
     * code: 返回码
     * message: 返回信息
     * URI: 应用URI
     * token: 用户令牌
     *
     */
    @PostMapping("/api/AppLogin")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest loginRequest) {
        Map<String, Object> responseMap = new HashMap<>();
        try {
            // 查询用户和应用权限
            Optional<User> userOptional = userRepository.findById(loginRequest.getUserId());
            Optional<Application> applicationOptional = applicationRepository.findById(loginRequest.getApplicationId());
            Optional<UserApplicationView> userApplicationViewOptional = userApplicationRepository
                    .findByUserIdAndApplicationId(loginRequest.getUserId(), loginRequest.getApplicationId());

            // 检查用户是否存在且状态为 active
            if (!userOptional.isPresent() || !userOptional.get().getStatus().equals("active")) {
                responseMap.put("code", "failed");
                responseMap.put("message", "用户不存在");
                return ResponseEntity.ok(responseMap);
            }

            // 用户无应用访问权限
            if (!userApplicationViewOptional.isPresent()) {
                responseMap.put("code", "failed");
                responseMap.put("message", "用户无权限访问该应用或无此应用");
                return ResponseEntity.ok(responseMap);
            }

            // 校验OTP
            String key = loginRequest.getUserId() + userOptional.get().getRandomkey() + String.valueOf(System.currentTimeMillis() / 1000 / 60);
            String otpCurrent = otp.generateOtp(key);
            // 分别在控制台上打印输入的otp和正确的otp
            System.out.println(key);
            System.out.println("输入的otp：" + loginRequest.getOtp());
            System.out.println("正确的otp：" + otpCurrent);
            // 如果输入的otp和正确的otp不相等，返回错误信息
            if (!loginRequest.getOtp().equals(otpCurrent)) {
                responseMap.put("code", "failed");
                responseMap.put("message", "OTP错误");
                return ResponseEntity.ok(responseMap);
            }

            // 添加应用授权日志
            String AddLogStatus = addAppauthlog.addAppauthlog(loginRequest.getUserId(), loginRequest.getApplicationId());
            if (!AddLogStatus.equals("success")) {
                responseMap.put("code", "failed");
                responseMap.put("message", AddLogStatus);
                return ResponseEntity.ok(responseMap);
            }
            responseMap.put("code", "success");
            responseMap.put("URI", applicationOptional.get().getUri());
            // IAM系统验证成功后，生成一个随机的token，返回给用户，用户在访问应用时，携带token，应用系统验证token是否有效
            responseMap.put("token", redisTokenService.setToken(loginRequest.getUserId(), loginRequest.getApplicationId()));
            return ResponseEntity.ok(responseMap);

        } catch (Exception e) {
            responseMap.put("code", "error");
            responseMap.put("message", "系统错误：" + e.getMessage());
            return ResponseEntity.status(500).body(responseMap);
        }
    }


}
