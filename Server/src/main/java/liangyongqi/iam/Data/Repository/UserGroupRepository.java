package liangyongqi.iam.Data.Repository;

import liangyongqi.iam.Data.Entity.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserGroupRepository extends JpaRepository<UserGroup, String> {
    // 更新对应用户组id的名称，并更新update_time为当前时间
    @Modifying
    @Transactional
    @Query("UPDATE UserGroup u SET u.name = :name, u.updateTime = CURRENT_TIMESTAMP WHERE u.id = :id")
    int updateNameById(String name, String id); // 更新对应用户组id的名称

    // 更新对用用户组id的描述，并更新update_time为当前时间
    @Modifying
    @Transactional
    @Query("UPDATE UserGroup u SET u.description = :description, u.updateTime = CURRENT_TIMESTAMP WHERE u.id = :id")
    int updateDescriptionById(String description, String id); // 更新对用用户组id的描述
}
