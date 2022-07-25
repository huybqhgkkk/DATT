package com.aladin.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Kafkaproducer.
 */
@Entity
@Table(name = "kafkaproducer")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "kafkaproducer")
public class Kafkaproducer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "subject")
    private String subject;

    @Column(name = "recipients")
    private String recipients;

    @Column(name = "recipientsname")
    private String recipientsname;

    @Column(name = "cc")
    private String cc;

    @Column(name = "datetime")
    private ZonedDateTime datetime;

    @Column(name = "content",length = 2000)
    private String content;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Kafkaproducer id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return this.subject;
    }

    public Kafkaproducer subject(String subject) {
        this.setSubject(subject);
        return this;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getRecipients() {
        return this.recipients;
    }

    public Kafkaproducer recipients(String recipients) {
        this.setRecipients(recipients);
        return this;
    }

    public void setRecipients(String recipients) {
        this.recipients = recipients;
    }

    public String getRecipientsname() {
        return this.recipientsname;
    }

    public Kafkaproducer recipientsname(String recipientsname) {
        this.setRecipientsname(recipientsname);
        return this;
    }

    public void setRecipientsname(String recipientsname) {
        this.recipientsname = recipientsname;
    }

    public String getCc() {
        return this.cc;
    }

    public Kafkaproducer cc(String cc) {
        this.setCc(cc);
        return this;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public ZonedDateTime getDatetime() {
        return this.datetime;
    }

    public Kafkaproducer datetime(ZonedDateTime datetime) {
        this.setDatetime(datetime);
        return this;
    }

    public void setDatetime(ZonedDateTime datetime) {
        this.datetime = datetime;
    }

    public String getContent() {
        return this.content;
    }

    public Kafkaproducer content(String content) {
        this.setContent(content);
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Kafkaproducer)) {
            return false;
        }
        return id != null && id.equals(((Kafkaproducer) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Kafkaproducer{" +
            "id=" + getId() +
            ", subject='" + getSubject() + "'" +
            ", recipients='" + getRecipients() + "'" +
            ", recipientsname='" + getRecipientsname() + "'" +
            ", cc='" + getCc() + "'" +
            ", datetime='" + getDatetime() + "'" +
            ", content='" + getContent() + "'" +
            "}";
    }
}
