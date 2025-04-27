package liangyongqi.iam.Ability;

import liangyongqi.iam.Data.Entity.WaitActiveUser;
import liangyongqi.iam.Data.Repository.UserRepository;
import liangyongqi.iam.Data.Repository.WaitActiveUserRepository;
import liangyongqi.iam.Util.LogTool;
import liangyongqi.iam.Util.RandomNumberGenerator;
import org.apache.juli.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ActiveUser {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WaitActiveUserRepository waitActiveUserRepository;

    /**
     * 激活用户
     * @param id 用户ID
     * @param code 激活码
     * @return 是否激活成功
     */
    @Transactional
    public Boolean activeUser(String id, String code) {
        try {
            WaitActiveUser waitActiveUser = waitActiveUserRepository.findById(id).orElse(null);
            // 查询WaitActiveUser表中是否存在这个id
            if (waitActiveUser == null) {
                return false;
            }
            // 判断激活码是否正确
            if (!waitActiveUser.getActiveCode().equals(code)) {
                return false;
            }
            // 生成新的认证算法Code
            RandomNumberGenerator randomNumberGenerator = new RandomNumberGenerator();
            String newCode = randomNumberGenerator.generateFromBase(code);
            // 数据库操作1：将User表此id条目中的key改为新的认证算法key
            LogTool.writelog("liangyongqi.iam.Ability.ActiveUser", "activeUser", "1");
            userRepository.updateRandomkeyById(newCode, id);
            // 数据库操作2：将User表此id条目中的status改为active
            LogTool.writelog("liangyongqi.iam.Ability.ActiveUser", "activeUser", "2");
            userRepository.updateStatusById("active", id);
            // 数据库操作3：删除WaitActiveUser表中此id条目
            LogTool.writelog("liangyongqi.iam.Ability.ActiveUser", "activeUser", "3");
            waitActiveUserRepository.delete(waitActiveUser);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
}
