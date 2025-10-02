package cu.academy.trace;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Entity
@Table(name = "trace" , schema = "academy")
public class TraceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "action_id", nullable = false)
    private Long actionId;

    @Column(name = "person_id")
    private Long personId;

    @Column(name = "full_name", nullable = false, length = 512)
    private String fullName;

    @Column(name = "mobile_phone", length = 15)
    private String mobilePhone;

    @Column(name = "context", length = 100)
    private String context;

    @Column(name = "details", length = 4000)
    private String details;

    @Column(name = "student_course_id")
    private Long studentCourseId;

    @Column(name = "application_id")
    private Long applicationId;

    @Column(name = "student_course_code", length = 20)
    private String studentCourseCode;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "date_created", nullable = false)
    private Instant dateCreated;

    @Column(name = "ip_address", length = 50)
    private String ipAddress;

    public TraceEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public Long getActionId() {

        return actionId;
    }

    public void setActionId(Long actionId) {
        this.actionId = actionId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public Instant getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Long getStudentCourseId() {
        return studentCourseId;
    }

    public void setStudentCourseId(Long studentCourseId) {
        this.studentCourseId = studentCourseId;
    }

    public String getStudentCourseCode() {
        return studentCourseCode;
    }

    public void setStudentCourseCode(String studentCourseCode) {
        this.studentCourseCode = studentCourseCode;
    }
}