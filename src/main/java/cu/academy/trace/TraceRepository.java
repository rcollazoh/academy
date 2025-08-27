package cu.academy.trace;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TraceRepository extends JpaSpecificationExecutor<TraceEntity>, JpaRepository<TraceEntity, Long> {
}
