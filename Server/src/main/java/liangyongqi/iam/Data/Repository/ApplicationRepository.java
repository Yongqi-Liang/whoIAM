package liangyongqi.iam.Data.Repository;

import liangyongqi.iam.Data.Entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, String> {

    // 根据id查询应用
    Optional<Application> findById(String id);
    // 根据id删除应用
    void deleteById(String id);

    // 根据id更新name字段和update_time时间戳字段
    @Modifying
    @Transactional
    @Query("UPDATE Application a SET a.name = :name, a.updateTime = CURRENT_TIMESTAMP WHERE a.id = :id")
    void updateNameById(String id, String name);
    // 根据id更新description字段和update_time字段
    @Modifying
    @Transactional
    @Query("UPDATE Application a SET a.description = :description, a.updateTime = CURRENT_TIMESTAMP WHERE a.id = :id")
    void updateDescriptionById(String id, String description);
    // 根据id更新uri字段和update_time字段
    @Modifying
    @Transactional
    @Query("UPDATE Application a SET a.uri = :uri, a.updateTime = CURRENT_TIMESTAMP WHERE a.id = :id")
    void updateUriById(String id, String uri);

}
