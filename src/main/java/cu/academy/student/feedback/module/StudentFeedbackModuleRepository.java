package cu.academy.student.feedback.module;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentFeedbackModuleRepository extends JpaSpecificationExecutor<StudentFeedbackModuleEntity>, JpaRepository<StudentFeedbackModuleEntity, Long> {
}
