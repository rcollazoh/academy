package cu.academy.student.feedback;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentFeedbackRepository extends JpaSpecificationExecutor<StudentFeedbackEntity>, JpaRepository<StudentFeedbackEntity, Long> {
}
