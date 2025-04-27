package liangyongqi.iam.Data.Entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * 用户组应用复合主键类
 */
public class UserGroupApplicationId implements Serializable {
    private String groupId;
    private String applicationId;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }
}