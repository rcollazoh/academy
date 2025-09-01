package cu.academy.student.classes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentClassRepository extends JpaSpecificationExecutor<StudentClassEntity>, JpaRepository<StudentClassEntity, Long> {
    @Query("SELECT sc FROM StudentClassEntity sc WHERE sc.studentCmodule.id = :studentModuleId")
        List<StudentClassEntity> findByModuleId(long moduleId);
}
