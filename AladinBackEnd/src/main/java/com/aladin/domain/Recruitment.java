package com.aladin.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Recruitment.
 */
@Entity
@Table(name = "recruitment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "recruitment")
public class Recruitment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "position")
    private String position;

    @Column(name = "description")
    private String description;

    @Column(name = "require")
    private String require;

    @Column(name = "benefit")
    private String benefit;

    @Column(name = "amount")
    private Long amount;

    @Column(name = "job")
    private String job;

    @Column(name = "location")
    private String location;

    @Column(name = "jhi_level")
    private String level;

    @Column(name = "duration")
    private LocalDate duration;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Recruitment id(Long id) {
        this.setId(id);
        return this;
    }

    public String getPosition() {
        return this.position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Recruitment position(String position) {
        this.setPosition(position);
        return this;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Recruitment description(String description) {
        this.setDescription(description);
        return this;
    }

    public String getRequire() {
        return this.require;
    }

    public void setRequire(String require) {
        this.require = require;
    }

    public Recruitment require(String require) {
        this.setRequire(require);
        return this;
    }

    public String getBenefit() {
        return this.benefit;
    }

    public void setBenefit(String benefit) {
        this.benefit = benefit;
    }

    public Recruitment benefit(String benefit) {
        this.setBenefit(benefit);
        return this;
    }

    public Long getAmount() {
        return this.amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Recruitment amount(Long amount) {
        this.setAmount(amount);
        return this;
    }

    public String getJob() {
        return this.job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Recruitment job(String job) {
        this.setJob(job);
        return this;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Recruitment location(String location) {
        this.setLocation(location);
        return this;
    }

    public String getLevel() {
        return this.level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Recruitment level(String level) {
        this.setLevel(level);
        return this;
    }

    public LocalDate getDuration() {
        return this.duration;
    }

    public void setDuration(LocalDate duration) {
        this.duration = duration;
    }

    public Recruitment duration(LocalDate duration) {
        this.setDuration(duration);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Recruitment)) {
            return false;
        }
        return id != null && id.equals(((Recruitment) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Recruitment{" +
            "id=" + getId() +
            ", position='" + getPosition() + "'" +
            ", description='" + getDescription() + "'" +
            ", require='" + getRequire() + "'" +
            ", benefit='" + getBenefit() + "'" +
            ", amount=" + getAmount() +
            ", job='" + getJob() + "'" +
            ", location='" + getLocation() + "'" +
            ", level='" + getLevel() + "'" +
            ", duration='" + getDuration() + "'" +
            "}";
    }

    public String getSearchField() {
        return "position,description,require,benefit,job,location,level";
    }

    public String getResponseField() {
        return "position,description,require,benefit,amount,job,location,level,duration";
    }

}
