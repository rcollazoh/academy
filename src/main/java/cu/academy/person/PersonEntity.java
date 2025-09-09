package cu.academy.person;

import cu.academy.accounto_operation.AccountOperationEntity;
import cu.academy.nom.area.NomAreaEntity;
import cu.academy.nom.practice.NomPracticeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "person", schema = "academy")
public class PersonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", length = 100)
    private String name;
    @Column(name = "last_name", length = 200)
    private String lastName;

    @Column(name = "email", length = 150)
    private String email;

    @Column(name = "phone", length = 20)
    private String phone;

    @Basic
    @Column(name = "password", length = 60)
    private String password;

    @Basic
    @Column(name = "is_user", nullable = false)
    private Boolean isUser = false;
    @Basic
    @Column(name = "is_client", nullable = false)
    private Boolean isClient = false;
    @Lob
    @Column(name = "gender")
    private String gender;

    @Column(name = "birthday")
    private LocalDate birthday;

    @ColumnDefault("0")
    @Column(name = "verified")
    private Boolean verified;

    @Column(name = "status", length = 50)
    private String status;


    @Column(name = "photo")
    private String photo;

    @Column(name = "about_me", length = 5000)
    private String aboutMe;

    @Column(name = "date_creation")
    private Instant dateCreation;

    @Column(name = "date_modify")
    private Instant dateModify;

    @OneToMany(mappedBy = "toPerson")
    private Set<AccountOperationEntity> accountOperations = new LinkedHashSet<>();

    @Size(max = 45)
    @Column(name = "id_number", length = 45)
    private String idNumber;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_id")
    private NomAreaEntity area;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "practice_id")
    private NomPracticeEntity practice;

    public NomPracticeEntity getPractice() {
        return practice;
    }

    public void setPractice(NomPracticeEntity practice) {
        this.practice = practice;
    }

    public NomAreaEntity getArea() {
        return area;
    }

    public void setArea(NomAreaEntity area) {
        this.area = area;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public Instant getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Instant dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Instant getDateModify() {
        return dateModify;
    }

    public void setDateModify(Instant dateModify) {
        this.dateModify = dateModify;
    }

    public Set<AccountOperationEntity> getAccountOperations() {
        return accountOperations;
    }

    public void setAccountOperations(Set<AccountOperationEntity> accountOperations) {
        this.accountOperations = accountOperations;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Boolean getIsUser() {
        return isUser;
    }

    public void setIsUser(Boolean user) {
        isUser = user;
    }

    public Boolean getIsClient() {
        return isClient;
    }

    public void setIsClient(Boolean client) {
        isClient = client;
    }

    @PrePersist
    protected void preInsert() {
        if (isClient == null) {
            isClient = false;
        }
        if (isUser == null) {
            isUser = false;
        }

    }
}