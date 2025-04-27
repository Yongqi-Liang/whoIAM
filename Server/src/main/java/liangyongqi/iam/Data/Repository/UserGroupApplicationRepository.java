package liangyongqi.iam.Data.Repository;

import liangyongqi.iam.Data.Entity.UserGroupApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface UserGroupApplicationRepository extends JpaRepository<UserGroupApplication, String> {
    /**
     * 创建用户组应用
     * @param groupId
     * @param applicationId
     * @return boolean
     */
    @Modifying
    @Transactional
    @Query(value = "insert into user_group_application (group_id, application_id) values (?1, ?2)", nativeQuery = true)
    boolean createUserGroupApplication(String groupId, String applicationId);

    /**
     * 根据应用 ID 查询关联的用户组应用记录
     *
     * @param applicationId 应用的唯一标识
     * @return 与该应用关联的用户组应用记录列表
     */
    @Modifying
    @Transactional
    @Query(value = "select * from user_group_application where application_id = ?1", nativeQuery = true)
    List<UserGroupApplication> findByApplicationId(String applicationId);

    /**
     * 根据用户组 ID 和应用 ID 删除用户组应用记录
     * @param groupId
     * @param applicationId
     */
    @Modifying
    @Transactional
    @Query(value = "delete from user_group_application where group_id = ?1 and application_id = ?2", nativeQuery = true)
    void deleteByGroupIdAndApplicationId(String groupId, String applicationId);

    /**
     * 根据用户组 ID 查询关联的用户组应用记录
     * @param groupId
     * @return
     */
    @Modifying
    @Transactional
    @Query(value = "select * from user_group_application where group_id = ?1", nativeQuery = true)
    List<UserGroupApplication> findByGroupId(String groupId);

    /**
     * 根据用户组 ID 和应用 ID 查询用户组应用记录是否存在
     * @param groupId
     * @param applicationId
     * @return
     */
    Optional<UserGroupApplication> findByGroupIdAndApplicationId(String groupId, String applicationId);

}
