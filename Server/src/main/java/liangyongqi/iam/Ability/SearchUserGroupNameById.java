package liangyongqi.iam.Ability;

import liangyongqi.iam.Data.Entity.UserGroup;
import liangyongqi.iam.Data.Repository.UserGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SearchUserGroupNameById {
    @Autowired
    private UserGroupRepository userGroupRepository;

    // 单个 ID 查询用户组名称
    public String getUserGroupNameById(String id) {
        return userGroupRepository.findById(id)
                .map(UserGroup::getName)
                .orElse("Unknown Group");
    }

    // 批量查询用户组名称
    public Map<String, String> getUserGroupNamesByIds(List<String> ids) {
        List<UserGroup> userGroups = userGroupRepository.findAllById(ids); // 批量查询
        Map<String, String> result = new HashMap<>();
        for (UserGroup group : userGroups) {
            result.put(group.getId(), group.getName()); // 将 ID 和名称存入结果
        }
        return result;
    }
}

