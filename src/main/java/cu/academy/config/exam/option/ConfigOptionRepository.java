package cu.academy.config.exam.option;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConfigOptionRepository extends JpaSpecificationExecutor<ConfigOptionEntity>, JpaRepository<ConfigOptionEntity, Long> {
    @Query("SELECT ce FROM ConfigOptionEntity ce WHERE ce.question.id = :questionId")
    List<ConfigOptionEntity> findByQuestionId(long questionId);
}

