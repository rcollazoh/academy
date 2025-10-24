package cu.academy.config.reference;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConfigReferenceRepository extends JpaSpecificationExecutor<ConfigReferenceEntity>, JpaRepository<ConfigReferenceEntity, Long> {
    @Query("SELECT cr FROM ConfigReferenceEntity cr WHERE cr.module.id = :moduleId  order by cr.orderNum")
    List<ConfigReferenceEntity> findByModuleAndOrderByOrderNumAsc(Long moduleId);

}
