package cu.academy.student.exam;

import cu.academy.config.exam.ConfigExamEntity;
import cu.academy.shared.enum_types.EnumExamStatus;
import cu.academy.student.module.StudentModuleEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@Entity
@Table(name = "student_exam")
public class StudentExamEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "config_exam_id", nullable = false)
    private ConfigExamEntity configExam;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "student_module_id", nullable = false)
    private StudentModuleEntity studentModule;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EnumExamStatus status;

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
        status = EnumExamStatus.NEW;
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

    public ConfigExamEntity getConfigExam() {
        return configExam;
    }

    public void setConfigExam(ConfigExamEntity configExam) {
        this.configExam = configExam;
    }

    public StudentModuleEntity getStudentModule() {
        return studentModule;
    }

    public void setStudentModule(StudentModuleEntity studentModule) {
        this.studentModule = studentModule;
    }

    public EnumExamStatus getStatus() {
        return status;
    }

    public void setStatus(EnumExamStatus status) {
        this.status = status;
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