package liangyongqi.iam.Ability;

import liangyongqi.iam.Data.Entity.UserGroupApplication;
import liangyongqi.iam.Data.Repository.UserGroupApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateUserGroupApplication {
    @Autowired
    private UserGroupApplicationRepository userGroupApplicationRepository;

    /**
     * 创建用户组应用
     * @param groupId
     * @param applicationId
     * @return
     */
    public String createUserGroupApplication(String groupId, String applicationId) {
        try {
            // 检查是否已经存在记录
            if (userGroupApplicationRepository.findByGroupIdAndApplicationId(groupId, applicationId).isPresent()) {
                return "failed"; // 记录已存在，返回失败
            } else {
                // 创建新的记录
                UserGroupApplication userGroupApplication = new UserGroupApplication();
                userGroupApplication.setGroupId(groupId);
                userGroupApplication.setApplicationId(applicationId);
                userGroupApplicationRepository.save(userGroupApplication);
                return "success"; // 创建成功
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "failed"; // 发生异常，返回失败
        }
    }
}
