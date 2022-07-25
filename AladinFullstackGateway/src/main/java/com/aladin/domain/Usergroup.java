package com.aladin.domain;

import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * An authority (a security role) used by Spring Security.
 */
@Table("usergroup")
public class Usergroup implements Serializable, Persistable<String> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @javax.persistence.Column(name = "id")
    private Long id;

    @javax.persistence.Column(name = "groupname")
    private String groupname;

    @javax.persistence.Column(name = "isleader")
    private String isleader;

    @javax.persistence.Column(name = "mail")
    private String mail;

    @Column(name = "datetime")
    private ZonedDateTime datetime;

    @ManyToOne
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return String.valueOf(this.id);
    }

    @Override
    public boolean isNew() {
        return false;
    }

    public Usergroup id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupname() {
        return this.groupname;
    }

    public Usergroup groupname(String groupname) {
        this.setGroupname(groupname);
        return this;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getIsleader() {
        return this.isleader;
    }

    public Usergroup isleader(String isleader) {
        this.setIsleader(isleader);
        return this;
    }

    public void setIsleader(String isleader) {
        this.isleader = isleader;
    }

    public String getMail() {
        return this.mail;
    }

    public Usergroup mail(String mail) {
        this.setMail(mail);
        return this;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public ZonedDateTime getDatetime() {
        return this.datetime;
    }

    public Usergroup datetime(ZonedDateTime datetime) {
        this.setDatetime(datetime);
        return this;
    }

    public void setDatetime(ZonedDateTime datetime) {
        this.datetime = datetime;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Usergroup user(User user) {
        this.setUser(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Usergroup)) {
            return false;
        }
        return id != null && id.equals(((Usergroup) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Usergroup{" +
            "id=" + getId() +
            ", groupname='" + getGroupname() + "'" +
            ", isleader='" + getIsleader() + "'" +
            ", mail='" + getMail() + "'" +
            ", datetime='" + getDatetime() + "'" +
            "}";
    }
}
