package liangyongqi.iam.Data.Repository;

import liangyongqi.iam.Data.Entity.Faceid;
import liangyongqi.iam.Data.Entity.FaceidId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FaceidRepository extends JpaRepository<Faceid, FaceidId> {
}
