package com.aladin.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Candidate.
 */
@Entity
@Table(name = "candidate")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Candidate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "phone", nullable = false)
    private String phone;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "position", nullable = false)
    private String position;

    @NotNull
    @Column(name = "birthday", nullable = false)
    private Instant birthday;

    @NotNull
    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "preference")
    private String preference;

    @NotNull
    @Column(name = "education", nullable = false)
    private String education;

    @NotNull
    @Column(name = "experience", nullable = false)
    private String experience;

    @Column(name = "target")
    private String target;

    @Column(name = "relationship")
    private String relationship;

    @Column(name = "timeregister")
    private Instant timeregister;

    @Column(name = "fullname")
    private String fullname;

    @Column(name = "sex")
    private String sex;

    @Lob
    @Column(name = "cv")
    private byte[] cv;

    @Column(name = "cv_content_type")
    private String cvContentType;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Candidate id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return this.phone;
    }

    public Candidate phone(String phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return this.email;
    }

    public Candidate email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPosition() {
        return this.position;
    }

    public Candidate position(String position) {
        this.setPosition(position);
        return this;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Instant getBirthday() {
        return this.birthday;
    }

    public Candidate birthday(Instant birthday) {
        this.setBirthday(birthday);
        return this;
    }

    public void setBirthday(Instant birthday) {
        this.birthday = birthday;
    }

    public String getLocation() {
        return this.location;
    }

    public Candidate location(String location) {
        this.setLocation(location);
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPreference() {
        return this.preference;
    }

    public Candidate preference(String preference) {
        this.setPreference(preference);
        return this;
    }

    public void setPreference(String preference) {
        this.preference = preference;
    }

    public String getEducation() {
        return this.education;
    }

    public Candidate education(String education) {
        this.setEducation(education);
        return this;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getExperience() {
        return this.experience;
    }

    public Candidate experience(String experience) {
        this.setExperience(experience);
        return this;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getTarget() {
        return this.target;
    }

    public Candidate target(String target) {
        this.setTarget(target);
        return this;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getRelationship() {
        return this.relationship;
    }

    public Candidate relationship(String relationship) {
        this.setRelationship(relationship);
        return this;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public Instant getTimeregister() {
        return this.timeregister;
    }

    public Candidate timeregister(Instant timeregister) {
        this.setTimeregister(timeregister);
        return this;
    }

    public void setTimeregister(Instant timeregister) {
        this.timeregister = timeregister;
    }

    public String getFullname() {
        return this.fullname;
    }

    public Candidate fullname(String fullname) {
        this.setFullname(fullname);
        return this;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getSex() {
        return this.sex;
    }

    public Candidate sex(String sex) {
        this.setSex(sex);
        return this;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public byte[] getCv() {
        return this.cv;
    }

    public Candidate cv(byte[] cv) {
        this.setCv(cv);
        return this;
    }

    public void setCv(byte[] cv) {
        this.cv = cv;
    }

    public String getCvContentType() {
        return this.cvContentType;
    }

    public Candidate cvContentType(String cvContentType) {
        this.cvContentType = cvContentType;
        return this;
    }

    public void setCvContentType(String cvContentType) {
        this.cvContentType = cvContentType;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Candidate)) {
            return false;
        }
        return id != null && id.equals(((Candidate) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Candidate{" +
            "id=" + getId() +
            ", phone='" + getPhone() + "'" +
            ", email='" + getEmail() + "'" +
            ", position='" + getPosition() + "'" +
            ", birthday='" + getBirthday() + "'" +
            ", location='" + getLocation() + "'" +
            ", preference='" + getPreference() + "'" +
            ", education='" + getEducation() + "'" +
            ", experience='" + getExperience() + "'" +
            ", target='" + getTarget() + "'" +
            ", relationship='" + getRelationship() + "'" +
            ", timeregister='" + getTimeregister() + "'" +
            ", fullname='" + getFullname() + "'" +
            ", sex='" + getSex() + "'" +
            ", cv='" + getCv() + "'" +
            ", cvContentType='" + getCvContentType() + "'" +
            "}";
    }
}
