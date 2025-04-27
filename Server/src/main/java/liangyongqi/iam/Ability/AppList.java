package liangyongqi.iam.Ability;

import liangyongqi.iam.Data.Entity.Application;
import liangyongqi.iam.Data.Entity.UserGroupApplication;
import liangyongqi.iam.Data.Repository.ApplicationRepository;
import liangyongqi.iam.Data.Repository.UserGroupApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AppList {
    private final ApplicationRepository applicationRepository;
    private final UserGroupApplicationRepository userGroupApplicationRepository;
    private final SearchUserGroupNameById searchUserGroupNameById;

    @Autowired
    public AppList(ApplicationRepository applicationRepository,
                   UserGroupApplicationRepository userGroupApplicationRepository,
                   SearchUserGroupNameById searchUserGroupNameById) {
        this.applicationRepository = applicationRepository;
        this.userGroupApplicationRepository = userGroupApplicationRepository;
        this.searchUserGroupNameById = searchUserGroupNameById;
    }

    public Map<String, Object> getAppList() {
        List<Application> apps = applicationRepository.findAll();
        if (apps == null || apps.isEmpty()) {
            return new HashMap<>(); // 返回空结果
        }

        Map<String, Object> result = new HashMap<>();
        for (Application app : apps) {
            Map<String, Object> appInfo = new HashMap<>();
            appInfo.put("name", app.getName());
            appInfo.put("description", app.getDescription());
            appInfo.put("uri", app.getUri());

            // 获取用户组应用信息
            List<UserGroupApplication> userGroupApplications =
                    userGroupApplicationRepository.findByApplicationId(app.getId());
            if (userGroupApplications == null || userGroupApplications.isEmpty()) {
                userGroupApplications = new ArrayList<>();
            }

            // 收集用户组 ID 列表
            List<String> groupIds = new ArrayList<>();
            for (UserGroupApplication uga : userGroupApplications) {
                groupIds.add(uga.getGroupId());
            }

            // 批量查询用户组名称
            Map<String, String> groupIdToNameMap = searchUserGroupNameById.getUserGroupNamesByIds(groupIds);

            // 构建用户组信息
            List<Map<String, Object>> userGroups = new ArrayList<>();
            for (String groupId : groupIds) {
                Map<String, Object> userGroupInfo = new HashMap<>();
                userGroupInfo.put("groupid", groupId);
                userGroupInfo.put("groupname", groupIdToNameMap.getOrDefault(groupId, "未知组"));
                userGroups.add(userGroupInfo);
            }

            appInfo.put("usergroups", userGroups);
            appInfo.put("create_time", app.getCreateTime());
            appInfo.put("update_time", app.getUpdateTime());
            result.put(app.getId(), appInfo);
        }
        return result;
    }
}
