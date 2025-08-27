package cu.academy.nom.operation_type;

import cu.academy.accounto_operation.AccountOperationEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "nom_operation_type", schema = "academy")
public class NomOperationTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "int UNSIGNED not null")
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "short_description", nullable = false, length = 20)
    private String shortDescription;

    @Column(name = "description", length = 500)
    private String description;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "date_created", nullable = false)
    private Instant dateCreated;

    @Column(name = "date_modified")
    private Instant dateModified;

    @ColumnDefault("0")
    @Column(name = "clears_debt", nullable = false)
    private Boolean clearsDebt = false;

    @ColumnDefault("1")
    @Column(name = "visible", nullable = false)
    private Boolean visible = false;

    @OneToMany(mappedBy = "operationType")
    private Set<AccountOperationEntity> accountOperations = new HashSet<>();

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Boolean getClearsDebt() {
        return clearsDebt;
    }

    public void setClearsDebt(Boolean clearsDebt) {
        this.clearsDebt = clearsDebt;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Set<AccountOperationEntity> getAccountOperations() {
        return accountOperations;
    }

    public void setAccountOperations(Set<AccountOperationEntity> accountOperations) {
        this.accountOperations = accountOperations;
    }

}