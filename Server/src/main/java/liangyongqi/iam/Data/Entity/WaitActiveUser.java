package liangyongqi.iam.Data.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.sql.Timestamp;

/**
 * 待激活用户实体类
 */
@Entity
@Table(name = "wait_active_user")
public class WaitActiveUser {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "active_code")
    private String activeCode;

    @Column(name = "expire_time")
    private Timestamp expireTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActiveCode() {
        return activeCode;
    }

    public void setActiveCode(String activeCode) {
        this.activeCode = activeCode;
    }

    public Timestamp getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Timestamp expireTime) {
        this.expireTime = expireTime;
    }
}
