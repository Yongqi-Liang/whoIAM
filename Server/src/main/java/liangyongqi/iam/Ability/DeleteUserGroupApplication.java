package liangyongqi.iam.Ability;

import liangyongqi.iam.Data.Repository.UserGroupApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteUserGroupApplication {
    @Autowired
    private UserGroupApplicationRepository userGroupApplicationRepository;

    /**
     * 删除用户组应用
     * @param groupId
     * @param applicationId
     * @return
     */
    public String deleteUserGroupApplication(String groupId, String applicationId) {
        try {
            userGroupApplicationRepository.deleteByGroupIdAndApplicationId(groupId, applicationId);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "failed";
        }
    }
}
