package liangyongqi.iam.Ability;

import liangyongqi.iam.Data.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DisableUser {
    @Autowired
    private UserRepository userRepository;

    /**
     * 禁用用户
     * @param id 用户id
     * @return 是否禁用成功
     */
    public Boolean disableUser(String id) {
        try {
            // 数据库操作：将User表此id条目中的status改为disable
            userRepository.updateStatusById("disabled", id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
