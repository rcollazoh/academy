package cu.academy.student.classes;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
public interface StudentClassRepository extends JpaSpecificationExecutor<StudentClassEntity>, JpaRepository<StudentClassEntity, Long> {
    @Query("SELECT sc FROM StudentClassEntity sc WHERE sc.studentModule.id = :moduleId")
    List<StudentClassEntity> findByModuleId(long moduleId);

    @Modifying
    @Transactional
    @Query("UPDATE StudentClassEntity s SET s.viewed = :viewed WHERE s.id = :id")
    void updateViewedById(@Param("id") long id, @Param("viewed") boolean viewed);

    @Modifying
    @Transactional
    @Query("UPDATE StudentClassEntity s SET s.currentImageId = :currentImageId WHERE s.id = :id")
    void updateCurrentImageIdById(@Param("id") long id, @Param("currentImageId") Long currentImageId);
}
