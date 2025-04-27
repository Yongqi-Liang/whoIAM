package liangyongqi.iam.Ability;

import liangyongqi.iam.Data.Entity.User;
import liangyongqi.iam.Data.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChangeUserInfo {
    @Autowired
    private UserRepository userRepository;
    /**
     * 修改用户信息
     * @param id
     * @param name
     * @param email
     * @param groupid
     * @return
     */
    @Transactional
    public Boolean changeUserInfo(String id, String name, String email, String groupid) {
        try {
            User user = userRepository.findById(id).get();
            // 数据库操作：将User表此id条目中的name改为name
            if (name != null && !name.isEmpty()) {
                userRepository.updateNameById(name, id);
            }
            // 数据库操作：将User表此id条目中的email改为email
            if (email != null && !email.isEmpty()) {
                userRepository.updateEmailById(email, id);
            }
            // 数据库操作：将User表此id条目中的groupid改为groupid
            if (groupid != null && !groupid.isEmpty()) {
                userRepository.updateGroupIdById(groupid, id);
            }
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}
