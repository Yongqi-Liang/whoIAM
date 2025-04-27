package liangyongqi.iam.Ability;

import liangyongqi.iam.Data.Repository.UserRepository;
import liangyongqi.iam.Data.Entity.User; // 假设 User 是对应用户表的实体类
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserList {
    @Autowired
    private UserRepository userRepository;

    /**
     * 获取用户列表
     * @return 用户列表
     */
    public Map<String, Object> getUserList() {
        // 获取所有用户数据
        List<User> users = userRepository.findAll();

        // 创建结果 Map
        Map<String, Object> result = new HashMap<>();

        // 遍历用户列表，组织数据
        for (User user : users) {
            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("username", user.getName());
            userInfo.put("email", user.getEmail());
            userInfo.put("ingroup", user.getGroupId());
            userInfo.put("status", user.getStatus());
            userInfo.put("create_time", user.getCreateTime());
            userInfo.put("update_time", user.getUpdateTime());
            result.put(user.getId().toString(), userInfo);
        }

        return result;
    }
    /**
     * 获取用户随机密钥
     * @param uid 用户id
     * @return 用户随机密钥
     */
    public String getRandomKey(String uid) {
        return userRepository.findRandomKeyById(uid);
    }
}
