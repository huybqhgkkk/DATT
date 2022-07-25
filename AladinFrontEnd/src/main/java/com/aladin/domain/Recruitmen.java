package com.aladin.domain;

import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * entity Book{\ntilte String\ndes String\nimage ImageBlob\n}\n\nentity TypeBook{\nTypeName String\n}\n\nrelationship ManyToMany{\nBook{typeBook} to TypeBook{book}\n}
 */
@ApiModel(
    description = "entity Book{\ntilte String\ndes String\nimage ImageBlob\n}\n\nentity TypeBook{\nTypeName String\n}\n\nrelationship ManyToMany{\nBook{typeBook} to TypeBook{book}\n}"
)
@Entity
@Table(name = "recruitmen")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Recruitmen implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "position", nullable = false)
    private String position;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "require", nullable = false)
    private String require;

    @NotNull
    @Column(name = "benefit", nullable = false)
    private String benefit;

    @NotNull
    @Column(name = "amount", nullable = false)
    private Long amount;

    @Column(name = "job")
    private String job;

    @Column(name = "location")
    private String location;

    @Column(name = "duration")
    private String duration;

    @Column(name = "jhi_level")
    private String level;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Recruitmen id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPosition() {
        return this.position;
    }

    public Recruitmen position(String position) {
        this.setPosition(position);
        return this;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDescription() {
        return this.description;
    }

    public Recruitmen description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRequire() {
        return this.require;
    }

    public Recruitmen require(String require) {
        this.setRequire(require);
        return this;
    }

    public void setRequire(String require) {
        this.require = require;
    }

    public String getBenefit() {
        return this.benefit;
    }

    public Recruitmen benefit(String benefit) {
        this.setBenefit(benefit);
        return this;
    }

    public void setBenefit(String benefit) {
        this.benefit = benefit;
    }

    public Long getAmount() {
        return this.amount;
    }

    public Recruitmen amount(Long amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getJob() {
        return this.job;
    }

    public Recruitmen job(String job) {
        this.setJob(job);
        return this;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getLocation() {
        return this.location;
    }

    public Recruitmen location(String location) {
        this.setLocation(location);
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDuration() {
        return this.duration;
    }

    public Recruitmen duration(String duration) {
        this.setDuration(duration);
        return this;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getLevel() {
        return this.level;
    }

    public Recruitmen level(String level) {
        this.setLevel(level);
        return this;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Recruitmen)) {
            return false;
        }
        return id != null && id.equals(((Recruitmen) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Recruitmen{" +
            "id=" + getId() +
            ", position='" + getPosition() + "'" +
            ", description='" + getDescription() + "'" +
            ", require='" + getRequire() + "'" +
            ", benefit='" + getBenefit() + "'" +
            ", amount=" + getAmount() +
            ", job='" + getJob() + "'" +
            ", location='" + getLocation() + "'" +
            ", duration='" + getDuration() + "'" +
            ", level='" + getLevel() + "'" +
            "}";
    }
}
