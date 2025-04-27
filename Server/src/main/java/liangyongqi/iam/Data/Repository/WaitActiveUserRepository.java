package liangyongqi.iam.Data.Repository;

import liangyongqi.iam.Data.Entity.User;
import liangyongqi.iam.Data.Entity.WaitActiveUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WaitActiveUserRepository extends JpaRepository<WaitActiveUser, String> {
}
