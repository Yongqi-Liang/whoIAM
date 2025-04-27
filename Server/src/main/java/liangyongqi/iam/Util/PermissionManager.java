package liangyongqi.iam.Util;

import liangyongqi.iam.Data.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionManager {

    @Autowired
    private RedisTokenService redisTokenService;
    @Autowired
    private UserRepository userRepository;

    public String checkAdminPermission(String token) {
        // 先验证管理员token
        if (Param.isNull(token)){
            return "管理员token为空";
        }
        // 处理 userId
        String userId = redisTokenService.authToken("admin", token);
        if (Param.isNull(userId)){
            return "此token数据出错";
        }
        // 确认是否管理用户组
        if (!userRepository.findById(userId).get().getGroupId().equals("admingroup")){
            return "此用户不是管理员";
        }
        return "0";
    }
}
