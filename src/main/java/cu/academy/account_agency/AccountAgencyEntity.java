package cu.academy.account_agency;

import cu.academy.accounto_operation.AccountOperationEntity;
import cu.academy.nom.payment_method.NomPaymentMethodEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "account_agency", schema = "academy")
public class AccountAgencyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "int UNSIGNED not null")
    private Long id;

    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @Column(name = "short_description", length = 50)
    private String shortDescription;

    @Column(name = "description", length = 1000)
    private String description;

    @ColumnDefault("'CUP'")
    @Column(name = "currency", nullable = false, length = 20)
    private String currency;

    @Column(name = "balance", precision = 19, scale = 4)
    private BigDecimal balance;

    @ColumnDefault("1")
    @Column(name = "is_active")
    private Boolean isActive;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "date_created", nullable = false)
    private Instant dateCreated;

    @Column(name = "date_modified")
    private Instant dateModified;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nom_payment_method_id")
    private NomPaymentMethodEntity nomPaymentMethod;

    @ColumnDefault("0.0000")
    @Column(name = "tip", precision = 19, scale = 4)
    private BigDecimal tip;

    @OneToMany(mappedBy = "agencyAccount")
    private Set<AccountOperationEntity> accountOperations = new LinkedHashSet<>();

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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
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

    public NomPaymentMethodEntity getNomPaymentMethod() {
        return nomPaymentMethod;
    }

    public void setNomPaymentMethod(NomPaymentMethodEntity nomPaymentMethod) {
        this.nomPaymentMethod = nomPaymentMethod;
    }

    public BigDecimal getTip() {
        return tip;
    }

    public void setTip(BigDecimal tip) {
        this.tip = tip;
    }

    public Set<AccountOperationEntity> getAccountOperations() {
        return accountOperations;
    }

    public void setAccountOperations(Set<AccountOperationEntity> accountOperations) {
        this.accountOperations = accountOperations;
    }

}