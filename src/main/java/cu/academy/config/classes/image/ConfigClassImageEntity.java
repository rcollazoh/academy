package cu.academy.config.classes.image;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "config_class_image")
public class ConfigClassImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "order_num")
    private Integer orderNum;

    @Size(max = 150)
    @Column(name = "title", length = 150)
    private String title;

    @Lob
    @Column(name = "description")
    private String description;

    @Lob
    @Column(name = "type")
    private String type;

    @Lob
    @Column(name = "recourse_url")
    private String recourseUrl;

    @Column(name = "class_id")
    private Long classId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRecourseUrl() {
        return recourseUrl;
    }

    public void setRecourseUrl(String recourseUrl) {
        this.recourseUrl = recourseUrl;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

}