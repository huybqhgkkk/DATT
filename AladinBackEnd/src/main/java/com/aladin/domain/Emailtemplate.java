package com.aladin.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Emailtemplate.
 */
@Entity
@Table(name = "emailtemplate")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "emailtemplate")
public class Emailtemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 2, max = 50)
    @Column(name = "templatename", length = 50, nullable = false)
    private String templatename;

    @NotNull
    @Size(min = 10, max = 1000)
    @Column(name = "subject", length = 1000, nullable = false)
    private String subject;

    @Column(name = "hyperlink")
    private String hyperlink;

    @Column(name = "datetime")
    private ZonedDateTime datetime;

    @NotNull
    @Size(min = 2, max = 2000)
    @Column(name = "content", length = 1000, nullable = false)
    private String content;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Emailtemplate id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTemplatename() {
        return this.templatename;
    }

    public Emailtemplate templatename(String templatename) {
        this.setTemplatename(templatename);
        return this;
    }

    public void setTemplatename(String templatename) {
        this.templatename = templatename;
    }

    public String getSubject() {
        return this.subject;
    }

    public Emailtemplate subject(String subject) {
        this.setSubject(subject);
        return this;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getHyperlink() {
        return this.hyperlink;
    }

    public Emailtemplate hyperlink(String hyperlink) {
        this.setHyperlink(hyperlink);
        return this;
    }

    public void setHyperlink(String hyperlink) {
        this.hyperlink = hyperlink;
    }

    public ZonedDateTime getDatetime() {
        return this.datetime;
    }

    public Emailtemplate datetime(ZonedDateTime datetime) {
        this.setDatetime(datetime);
        return this;
    }

    public void setDatetime(ZonedDateTime datetime) {
        this.datetime = datetime;
    }

    public String getContent() {
        return this.content;
    }

    public Emailtemplate content(String content) {
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
        if (!(o instanceof Emailtemplate)) {
            return false;
        }
        return id != null && id.equals(((Emailtemplate) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Emailtemplate{" +
            "id=" + getId() +
            ", templatename='" + getTemplatename() + "'" +
            ", subject='" + getSubject() + "'" +
            ", hyperlink='" + getHyperlink() + "'" +
            ", datetime='" + getDatetime() + "'" +
            ", content='" + getContent() + "'" +
            "}";
    }
}
