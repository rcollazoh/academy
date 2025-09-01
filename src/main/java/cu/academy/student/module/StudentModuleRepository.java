package cu.academy.student.module;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentModuleRepository extends JpaSpecificationExecutor<StudentModuleEntity>, JpaRepository<StudentModuleEntity, Long> {
    @Query("SELECT sm FROM StudentModuleEntity sm WHERE sm.studentCourse.id = :studentCourseId")
    List<StudentModuleEntity> findFullModulesByStudentCourseId(@Param("studentCourseId") long studentCourseId);

}
