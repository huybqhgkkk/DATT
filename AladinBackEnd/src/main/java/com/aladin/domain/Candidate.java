package com.aladin.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Candidate.
 */
@Entity
@Table(name = "candidate")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "candidate")
public class Candidate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "position")
    private String position;

    @Column(name = "location")
    private String location;

    @Column(name = "preference")
    private String preference;

    @Column(name = "education")
    private String education;

    @Column(name = "experience")
    private String experience;

    @Column(name = "target")
    private String target;

    @Column(name = "fullname")
    private String fullname;

    @Column(name = "sex")
    private String sex;

    @Lob
    @Column(name = "cv")
    private byte[] cv;

    @Column(name = "cv_content_type")
    private String cvContentType;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "relationship")
    private String relationship;

    @Column(name = "date_register")
    private LocalDate dateRegister;

    @ManyToOne
    private Recruitment recruitment;

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

    public LocalDate getBirthday() {
        return this.birthday;
    }

    public Candidate birthday(LocalDate birthday) {
        this.setBirthday(birthday);
        return this;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
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

    public LocalDate getDateRegister() {
        return this.dateRegister;
    }

    public Candidate dateRegister(LocalDate dateRegister) {
        this.setDateRegister(dateRegister);
        return this;
    }

    public void setDateRegister(LocalDate dateRegister) {
        this.dateRegister = dateRegister;
    }

    public Recruitment getRecruitment() {
        return this.recruitment;
    }

    public void setRecruitment(Recruitment recruitment) {
        this.recruitment = recruitment;
    }

    public Candidate recruitment(Recruitment recruitment) {
        this.setRecruitment(recruitment);
        return this;
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
            ", location='" + getLocation() + "'" +
            ", preference='" + getPreference() + "'" +
            ", education='" + getEducation() + "'" +
            ", experience='" + getExperience() + "'" +
            ", target='" + getTarget() + "'" +
            ", fullname='" + getFullname() + "'" +
            ", sex='" + getSex() + "'" +
            ", cv='" + getCv() + "'" +
            ", cvContentType='" + getCvContentType() + "'" +
            ", birthday='" + getBirthday() + "'" +
            ", relationship='" + getRelationship() + "'" +
            ", dateRegister='" + getDateRegister() + "'" +
            "}";
    }
}
