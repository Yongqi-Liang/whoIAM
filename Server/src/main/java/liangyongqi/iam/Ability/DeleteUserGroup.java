package liangyongqi.iam.Ability;

import liangyongqi.iam.Data.Repository.UserApplicationViewRepo;
import liangyongqi.iam.Data.Repository.UserGroupRepository;
import liangyongqi.iam.Data.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteUserGroup {
    @Autowired
    private UserGroupRepository userGroupRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserApplicationViewRepo userApplicationViewRepo;
    /**
     * 删除用户组
     * @param gid 用户组id
     * @return 删除结果
     */
    public String deleteUserGroup(String gid) {
        try {
            if (userApplicationViewRepo.existsByGroupId(gid).isPresent() || userRepository.existsByGroupId(gid)) {
                return "用户组下存在用户或应用绑定，无法删除";
            }

            userGroupRepository.deleteById(gid);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}
