package cu.academy.role;

import cu.academy.person.person_role.PersonRoleEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "role", schema = "academy")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "int UNSIGNED not null")
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "short_description", length = 1000)
    private String shortDescription;

    @Column(name = "long_description", length = 2000)
    private String longDescription;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "date_created", nullable = false)
    private Instant dateCreated;

    @Column(name = "date_modified")
    private Instant dateModified;

    @OneToMany(mappedBy = "role")
    private Set<PersonRoleEntity> personRoles = new LinkedHashSet<>();

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

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public Instant getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Instant getDateModified() {
        return dateModified;
    }

    public void setDateModified(Instant dateModified) {
        this.dateModified = dateModified;
    }

    public Set<PersonRoleEntity> getPersonRoles() {
        return personRoles;
    }

    public void setPersonRoles(Set<PersonRoleEntity> personRoles) {
        this.personRoles = personRoles;
    }

}