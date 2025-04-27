package liangyongqi.iam.Ability;

import liangyongqi.iam.Data.Entity.UserGroupApplication;
import liangyongqi.iam.Data.Repository.UserGroupApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class GetUserGroupApplication {
    @Autowired
    private UserGroupApplicationRepository userGroupApplicationRepository;

    /**
     * 获取用户组应用
     * @param groupId
     * @return
     */
    public List<UserGroupApplication> getUserGroupApplication(String groupId) {
        try {
            return userGroupApplicationRepository.findByGroupId(groupId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
