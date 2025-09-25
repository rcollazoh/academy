package cu.academy.config.exam.question;

import cu.academy.config.exam.option.ConfigOptionEntity;
import cu.academy.config.exam.ConfigExamEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "config_question")
public class ConfigQuestionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @Lob
    @Column(name = "text", nullable = false)
    private String text;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Instant createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    private Instant updatedAt;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "exam_id", nullable = false)
    private ConfigExamEntity exam;

    @Column(name = "order_num")
    private Integer orderNum;

    @OneToMany(mappedBy = "question")
    private Set<ConfigOptionEntity> configOptions = new LinkedHashSet<>();

    public Set<ConfigOptionEntity> getConfigOptions() {
        return configOptions;
    }

    public void setConfigOptions(Set<ConfigOptionEntity> configOptions) {
        this.configOptions = configOptions;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public ConfigExamEntity getExam() {
        return exam;
    }

    public void setExam(ConfigExamEntity exam) {
        this.exam = exam;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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