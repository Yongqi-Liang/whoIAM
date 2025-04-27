package liangyongqi.iam.Ability;

import liangyongqi.iam.Data.Entity.UserGroup;
import liangyongqi.iam.Data.Repository.UserGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserGroupList {
    @Autowired
    private UserGroupRepository userGroupRepository;

    /**
     * 获取用户组列表
     * @return 用户组列表
     * 格式；{gid: {groupname: xxx, description: xxx, create_time: xxx, update_time: xxx}}
     */
    public Map<String, Object> getUserGroupList() {
        // 获取所有用户组数据
        List<UserGroup> userGroups = userGroupRepository.findAll();
        // 创建结果 Map
        Map<String, Object> result = new HashMap<>();
        // 遍历用户组列表，组织数据
        for (UserGroup userGroup : userGroups) {
            Map<String, Object> userGroupInfo = new HashMap<>();
            userGroupInfo.put("groupname", userGroup.getName());
            userGroupInfo.put("description", userGroup.getDescription());
            userGroupInfo.put("create_time", userGroup.getCreateTime());
            userGroupInfo.put("update_time", userGroup.getUpdateTime());

            result.put(userGroup.getId().toString(), userGroupInfo);
        }
        return result;

    }
    public Map<String, Object> getUserGroupListById(String gid) {
        // 获取所有用户组数据
        Optional<UserGroup> userGroup = userGroupRepository.findById(gid);
        // 创建结果 Map
        Map<String, Object> result = new HashMap<>();
        // 组织数据
        Map<String, Object> userGroupInfo = new HashMap<>();
        userGroupInfo.put("groupname", userGroup.get().getName());
        userGroupInfo.put("description", userGroup.get().getDescription());
        userGroupInfo.put("create_time", userGroup.get().getCreateTime());
        userGroupInfo.put("update_time", userGroup.get().getUpdateTime());

        return result;

    }
}
