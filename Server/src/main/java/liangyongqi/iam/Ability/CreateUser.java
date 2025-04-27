package liangyongqi.iam.Ability;

import liangyongqi.iam.Data.Entity.User;
import liangyongqi.iam.Data.Entity.UserGroup;
import liangyongqi.iam.Data.Entity.WaitActiveUser;
import liangyongqi.iam.Data.Repository.UserGroupRepository;
import liangyongqi.iam.Data.Repository.UserRepository;
import liangyongqi.iam.Data.Repository.WaitActiveUserRepository;
import liangyongqi.iam.Util.RandomNumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateUser {

    private final UserRepository userRepository;
    private final UserGroupRepository userGroupRepository;
    private final WaitActiveUserRepository waitActiveUserRepository;
    private final AccountActiveMailSender accountActiveMailSender;

    @Autowired
    public CreateUser(UserRepository userRepository,
                      UserGroupRepository userGroupRepository,
                      WaitActiveUserRepository waitActiveUserRepository,
                      AccountActiveMailSender accountActiveMailSender) {
        this.userRepository = userRepository;
        this.userGroupRepository = userGroupRepository;
        this.waitActiveUserRepository = waitActiveUserRepository;
        this.accountActiveMailSender = accountActiveMailSender;
    }

    /**
     * 创建用户
     * @param id 用户ID
     * @param name 用户名
     * @param email 用户邮箱
     * @param groupid 用户组ID
     * @return 是否创建成功
     */
    @Transactional
    public String createUser(String id, String name, String email, String groupid) {
        try {
            // 查询User表中是否存在这个id
            if (userRepository.findById(id).isPresent()) {
                return null;
            }
            // 判断参数groupid是否为空
            if (groupid != null) {
                // 查询UserGroup表中是否不存在这个groupid
                if (!userGroupRepository.findById(groupid).isPresent()) {
                    return null;
                }
            }
            // 执行插入操作
            User user = new User();
            user.setId(id);
            user.setName(name);
            user.setEmail(email);
            user.setGroupId(groupid);
            user.setStatus("wait_active");
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            user.setCreateTime(timestamp);
            user.setUpdateTime(timestamp);

            String key = RandomNumberGenerator.generateRandomNumber();
            WaitActiveUser waitActiveUser = new WaitActiveUser();
            waitActiveUser.setId(id);
            waitActiveUser.setActiveCode(key);
            // expire_time : 2天
            waitActiveUser.setExpireTime(new Timestamp(System.currentTimeMillis() + 2 * 24 * 60 * 60 * 1000));

            userRepository.save(user);
            waitActiveUserRepository.save(waitActiveUser);

            // 给注册用户发激活邮件
            accountActiveMailSender.sendAccountActiveMail(email, id, groupid, key);
            System.out.println("用户创建成功:id+" + id + ";key:" + key);
            return key;
        } catch (Exception e) {
            e.printStackTrace();
            // 事务回滚会由 Spring 自动完成
            return null;
        }
    }
}
