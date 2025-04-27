package liangyongqi.iam.Data.Entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.Immutable;

/**
 * 用户应用视图实体类
 */
@Entity
@Immutable
@Table(name = "user_application")
public class UserApplicationView {
    @Id
    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "application_id", nullable = false)
    private String applicationId;

    @Column(name = "group_id", nullable = false)
    private String groupId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}