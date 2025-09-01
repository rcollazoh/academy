package cu.academy.student.exam.answer;

import cu.academy.config.ConfigOptionEntity;
import cu.academy.config.ConfigQuestionEntity;
import cu.academy.student.module.StudentModuleEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Entity
@Table(name = "student_exam_answer")
public class StudentExamAnswerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_student_id")
    private StudentModuleEntity moduleStudent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private ConfigQuestionEntity question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id")
    private ConfigOptionEntity option;

    @Column(name = "is_correct")
    private Boolean isCorrect;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Instant createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    private Instant updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public StudentModuleEntity getModuleStudent() {
        return moduleStudent;
    }

    public void setModuleStudent(StudentModuleEntity moduleStudent) {
        this.moduleStudent = moduleStudent;
    }

    public ConfigQuestionEntity getQuestion() {
        return question;
    }

    public void setQuestion(ConfigQuestionEntity question) {
        this.question = question;
    }

    public ConfigOptionEntity getOption() {
        return option;
    }

    public void setOption(ConfigOptionEntity option) {
        this.option = option;
    }

    public Boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(Boolean isCorrect) {
        this.isCorrect = isCorrect;
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