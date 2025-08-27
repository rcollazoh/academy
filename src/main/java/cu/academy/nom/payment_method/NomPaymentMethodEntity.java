package cu.academy.nom.payment_method;

import cu.academy.account_agency.AccountAgencyEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "nom_payment_method", schema = "academy")
public class NomPaymentMethodEntity {
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

    @ColumnDefault("1")
    @Column(name = "show_to_customer", nullable = false)
    private Boolean showToCustomer = false;

    @ColumnDefault("0")
    @Column(name = "agency_transfer")
    private Boolean agencyTransfer;

    @Column(name = "percentage_applied")
    private Float percentageApplied;

    @ColumnDefault("0")
    @Column(name = "foreign_transfer")
    private Boolean foreignTransfer;

    @Column(name = "minimum_transfer_value")
    private Float minimumTransferValue;

    @ColumnDefault("1")
    @Column(name = "is_active")
    private Boolean isActive;

    @OneToMany(mappedBy = "nomPaymentMethod")
    private Set<AccountAgencyEntity> accountAgencies = new HashSet<>();

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

    public Boolean getShowToCustomer() {
        return showToCustomer;
    }

    public void setShowToCustomer(Boolean showToCustomer) {
        this.showToCustomer = showToCustomer;
    }

    public Boolean getAgencyTransfer() {
        return agencyTransfer;
    }

    public void setAgencyTransfer(Boolean agencyTransfer) {
        this.agencyTransfer = agencyTransfer;
    }

    public Float getPercentageApplied() {
        return percentageApplied;
    }

    public void setPercentageApplied(Float percentageApplied) {
        this.percentageApplied = percentageApplied;
    }

    public Boolean getForeignTransfer() {
        return foreignTransfer;
    }

    public void setForeignTransfer(Boolean foreignTransfer) {
        this.foreignTransfer = foreignTransfer;
    }

    public Float getMinimumTransferValue() {
        return minimumTransferValue;
    }

    public void setMinimumTransferValue(Float minimumTransferValue) {
        this.minimumTransferValue = minimumTransferValue;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Set<AccountAgencyEntity> getAccountAgencies() {
        return accountAgencies;
    }

    public void setAccountAgencies(Set<AccountAgencyEntity> accountAgencies) {
        this.accountAgencies = accountAgencies;
    }

}