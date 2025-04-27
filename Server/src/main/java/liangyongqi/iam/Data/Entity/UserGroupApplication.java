package liangyongqi.iam.Data.Entity;

import jakarta.persistence.*;

/**
 * 用户组应用实体类
 */
@Entity
@Table(name = "user_group_application")
@IdClass(UserGroupApplicationId.class)
public class UserGroupApplication {
    @Id
    @Column(name = "group_id", nullable = false)
    private String groupId;

    @Id
    @Column(name = "application_id", nullable = false)
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