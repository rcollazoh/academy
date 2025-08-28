package cu.academy.nom;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Entity
@Table(name = "nom_country")
public class NomCountry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 100)
    @Column(name = "name", length = 100)
    private String name;

    @Size(max = 3)
    @Column(name = "country_code", length = 3)
    private String countryCode;

    @Size(max = 50)
    @Column(name = "cell_prefix", length = 50)
    private String cellPrefix;

    @Size(max = 30)
    @Column(name = "nationality", length = 30)
    private String nationality;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "creation_date", nullable = false)
    private Instant creationDate;

    @Column(name = "modification_date")
    private Instant modificationDate;

    @NotNull
    @ColumnDefault("1")
    @Column(name = "active", nullable = false)
    private Boolean active = false;

    @Column(name = "max_cell_number_length")
    private Integer maxCellNumberLength;

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

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCellPrefix() {
        return cellPrefix;
    }

    public void setCellPrefix(String cellPrefix) {
        this.cellPrefix = cellPrefix;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public Instant getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Instant modificationDate) {
        this.modificationDate = modificationDate;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Integer getMaxCellNumberLength() {
        return maxCellNumberLength;
    }

    public void setMaxCellNumberLength(Integer maxCellNumberLength) {
        this.maxCellNumberLength = maxCellNumberLength;
    }

}