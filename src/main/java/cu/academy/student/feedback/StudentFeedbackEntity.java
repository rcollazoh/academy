package cu.academy.student.feedback;

import cu.academy.shared.enum_types.EnumModuleStatus;
import cu.academy.student.module.StudentModuleEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@Entity
@Table(name = "student_feedback")
public class StudentFeedbackEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "module_id", nullable = false)
    private StudentModuleEntity module;

    @Size(max = 100)
    @Column(name = "learning_question", length = 100)
    private String learningQuestion;

    @Size(max = 100)
    @Column(name = "duration_question", length = 100)
    private String durationQuestion;

    @Size(max = 100)
    @Column(name = "satisfaction_question", length = 100)
    private String satisfactionQuestion;

    @Lob
    @Column(name = "error_obs")
    private String errorObs;

    @Lob
    @Column(name = "consideration_obs")
    private String considerationObs;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Instant createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    private Instant updatedAt;

    @PrePersist
    protected void onCreate() {
        Instant now = Instant.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StudentModuleEntity getModule() {
        return module;
    }

    public void setModule(StudentModuleEntity module) {
        this.module = module;
    }

    public String getLearningQuestion() {
        return learningQuestion;
    }

    public void setLearningQuestion(String learningQuestion) {
        this.learningQuestion = learningQuestion;
    }

    public String getDurationQuestion() {
        return durationQuestion;
    }

    public void setDurationQuestion(String durationQuestion) {
        this.durationQuestion = durationQuestion;
    }

    public String getSatisfactionQuestion() {
        return satisfactionQuestion;
    }

    public void setSatisfactionQuestion(String satisfactionQuestion) {
        this.satisfactionQuestion = satisfactionQuestion;
    }

    public String getErrorObs() {
        return errorObs;
    }

    public void setErrorObs(String errorObs) {
        this.errorObs = errorObs;
    }

    public String getConsiderationObs() {
        return considerationObs;
    }

    public void setConsiderationObs(String considerationObs) {
        this.considerationObs = considerationObs;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

}