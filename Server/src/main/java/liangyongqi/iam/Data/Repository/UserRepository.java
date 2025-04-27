package liangyongqi.iam.Data.Repository;

import liangyongqi.iam.Data.Entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface UserRepository extends JpaRepository<User, String> {
    // 更新对应id的randomkey
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.randomkey = :randomKey WHERE u.id = :id")
    int updateRandomkeyById(String randomKey, String id);
    // 更新对应id的status
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.status = :status WHERE u.id = :id")
    int updateStatusById(String status, String id);
    // 更新对应id的用户名和更新时间
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.name = :name, u.updateTime = CURRENT_TIMESTAMP WHERE u.id = :id")
    int updateNameById(String name, String id);
    // 更新对应id的邮箱和更新时间
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.email = :email, u.updateTime = CURRENT_TIMESTAMP WHERE u.id = :id")
    int updateEmailById(String email, String id);
    // 更新对应id的groupid和更新时间
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.groupId = :groupId, u.updateTime = CURRENT_TIMESTAMP WHERE u.id = :id")
    int updateGroupIdById(String groupId, String id);
    // 通过id查找randomkey
    @Query("SELECT u.randomkey FROM User u WHERE u.id = :id")
    String findRandomKeyById(String id);
    // 查询用户组id是否存在
    @Modifying
    @Transactional
    @Query("SELECT COUNT(u) FROM User u WHERE u.groupId = :groupId")
    boolean existsByGroupId(String groupId);
}
