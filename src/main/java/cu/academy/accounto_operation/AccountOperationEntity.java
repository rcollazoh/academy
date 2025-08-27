package cu.academy.accounto_operation;

import cu.academy.account_agency.AccountAgencyEntity;
import cu.academy.nom.operation_type.NomOperationTypeEntity;
import cu.academy.person.PersonEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "account_operation", schema = "academy")
public class AccountOperationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "int UNSIGNED not null")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "operation_type_id", nullable = false)
    private NomOperationTypeEntity operationType;

    @Column(name = "from_person_id")
    private Long fromPersonId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_person_id")
    private PersonEntity toPerson;

    @Column(name = "from_person_type", length = 20)
    private String fromPersonType;

    @Column(name = "to_person_type", length = 20)
    private String toPersonType;

    @Column(name = "amount", nullable = false, precision = 19, scale = 4)
    private BigDecimal amount;

    @Column(name = "currency", length = 20)
    private String currency;

    @Column(name = "description", length = 1000)
    private String description;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "date_created", nullable = false)
    private Instant dateCreated;

    @Column(name = "date_modified")
    private Instant dateModified;


    @ColumnDefault("0")
    @Column(name = "is_cancelled", nullable = false)
    private Boolean isCancelled = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agency_account_id")
    private AccountAgencyEntity agencyAccount;

    @Column(name = "agency_account_remaining_balance", precision = 19, scale = 4)
    private BigDecimal agencyAccountRemainingBalance;

    @ColumnDefault("0")
    @Column(name = "is_settled", nullable = false)
    private Boolean isSettled = false;

    @Column(name = "notes", length = 1000)
    private String notes;

    @Column(name = "payment_method_id")
    private Long paymentMethodId;

    @ColumnDefault("0.0000")
    @Column(name = "tip", nullable = false, precision = 19, scale = 4)
    private BigDecimal tip;

    @Column(name = "confirmation_status_change_date")
    private Instant confirmationStatusChangeDate;

    @Column(name = "student_course_id")
    private Long studentCourseId;

    public Long getStudentCourseId() {
        return studentCourseId;
    }

    public void setStudentCourseId(Long studentCourseId) {
        this.studentCourseId = studentCourseId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NomOperationTypeEntity getOperationType() {
        return operationType;
    }

    public void setOperationType(NomOperationTypeEntity operationType) {
        this.operationType = operationType;
    }

    public Long getFromPersonId() {
        return fromPersonId;
    }

    public void setFromPersonId(Long fromPersonId) {
        this.fromPersonId = fromPersonId;
    }

    public PersonEntity getToPerson() {
        return toPerson;
    }

    public void setToPerson(PersonEntity toPerson) {
        this.toPerson = toPerson;
    }

    public String getFromPersonType() {
        return fromPersonType;
    }

    public void setFromPersonType(String fromPersonType) {
        this.fromPersonType = fromPersonType;
    }

    public String getToPersonType() {
        return toPersonType;
    }

    public void setToPersonType(String toPersonType) {
        this.toPersonType = toPersonType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
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

    public Boolean getIsCancelled() {
        return isCancelled;
    }

    public void setIsCancelled(Boolean isCancelled) {
        this.isCancelled = isCancelled;
    }

    public AccountAgencyEntity getAgencyAccount() {
        return agencyAccount;
    }

    public void setAgencyAccount(AccountAgencyEntity agencyAccount) {
        this.agencyAccount = agencyAccount;
    }

    public BigDecimal getAgencyAccountRemainingBalance() {
        return agencyAccountRemainingBalance;
    }

    public void setAgencyAccountRemainingBalance(BigDecimal agencyAccountRemainingBalance) {
        this.agencyAccountRemainingBalance = agencyAccountRemainingBalance;
    }

    public Boolean getIsSettled() {
        return isSettled;
    }

    public void setIsSettled(Boolean isSettled) {
        this.isSettled = isSettled;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Long getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(Long paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public BigDecimal getTip() {
        return tip;
    }

    public void setTip(BigDecimal tip) {
        this.tip = tip;
    }

    public Instant getConfirmationStatusChangeDate() {
        return confirmationStatusChangeDate;
    }

    public void setConfirmationStatusChangeDate(Instant confirmationStatusChangeDate) {
        this.confirmationStatusChangeDate = confirmationStatusChangeDate;
    }

}