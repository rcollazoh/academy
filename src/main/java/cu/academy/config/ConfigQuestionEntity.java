package cu.academy.config;

import cu.academy.nom.area.NomAreaEntity;
import cu.academy.nom.practice.NomPracticeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id")
    private ConfigModuleEntity module;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_id")
    private NomAreaEntity area;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "practice_id")
    private NomPracticeEntity practice;

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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ConfigModuleEntity getModule() {
        return module;
    }

    public void setModule(ConfigModuleEntity module) {
        this.module = module;
    }

    public NomAreaEntity getArea() {
        return area;
    }

    public void setArea(NomAreaEntity area) {
        this.area = area;
    }

    public NomPracticeEntity getPractice() {
        return practice;
    }

    public void setPractice(NomPracticeEntity practice) {
        this.practice = practice;
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