package cu.academy.student.module;

import cu.academy.config.module.ConfigModuleEntity;
import cu.academy.shared.enum_types.EnumModuleStatus;
import cu.academy.student.course.StudentCourseEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "student_module")
public class StudentModuleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_course_id")
    private StudentCourseEntity studentCourse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id")
    private ConfigModuleEntity module;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EnumModuleStatus status;

    @Column(name = "fecha_exam")
    private LocalDate fechaExam;

    @ColumnDefault("0")
    @Column(name = "intentos")
    private Integer intentos;

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
        status =  EnumModuleStatus.NEW;
        intentos = 0;
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

    public StudentCourseEntity getStudentCourse() {
        return studentCourse;
    }

    public void setStudentCourse(StudentCourseEntity studentCourse) {
        this.studentCourse = studentCourse;
    }

    public ConfigModuleEntity getModule() {
        return module;
    }

    public void setModule(ConfigModuleEntity module) {
        this.module = module;
    }

    public EnumModuleStatus getStatus() {
        return status;
    }

    public void setStatus(EnumModuleStatus status) {
        this.status = status;
    }

    public LocalDate getFechaExam() {
        return fechaExam;
    }

    public void setFechaExam(LocalDate fechaExam) {
        this.fechaExam = fechaExam;
    }

    public Integer getIntentos() {
        return intentos;
    }

    public void setIntentos(Integer intentos) {
        this.intentos = intentos;
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