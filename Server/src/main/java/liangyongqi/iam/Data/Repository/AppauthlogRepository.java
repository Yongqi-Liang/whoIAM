package liangyongqi.iam.Data.Repository;

import liangyongqi.iam.Data.Entity.Appauthlog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AppauthlogRepository extends JpaRepository<Appauthlog, String> , JpaSpecificationExecutor<Appauthlog> {

    // 根据用户 ID 和应用 ID 和日期（年月日） 查询应用授权日志
    @Query(value = "select * from appauthlog where uid = ?1 and appid = ?2 and date(authdatetime) = ?3", nativeQuery = true)
    List<Appauthlog> findByUidAndAppidAndDate(String uid, String appid, String date);

}
