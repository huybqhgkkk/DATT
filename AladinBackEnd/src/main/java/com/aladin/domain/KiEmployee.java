package com.aladin.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A KiEmployee.
 */
@Entity
@Table(name = "ki_employee")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "kiemployee")
public class KiEmployee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "date_time", nullable = false)
    private LocalDate date_time;

    @NotNull
    @DecimalMin(value = "0.1")
    @DecimalMax(value = "2.0")
    @Column(name = "work_quantity", nullable = false)
    private Float work_quantity;

    @NotNull
    @Size(min = 10, max = 2000)
    @Column(name = "work_quantity_comment", length = 2000, nullable = false)
    private String work_quantity_comment;

    @NotNull
    @DecimalMin(value = "0.1")
    @DecimalMax(value = "2.0")
    @Column(name = "work_quality", nullable = false)
    private Float work_quality;

    @NotNull
    @Size(min = 10, max = 2000)
    @Column(name = "work_quality_comment", length = 2000, nullable = false)
    private String work_quality_comment;

    @NotNull
    @DecimalMin(value = "0.1")
    @DecimalMax(value = "2.0")
    @Column(name = "work_progress", nullable = false)
    private Float work_progress;

    @NotNull
    @Size(min = 10, max = 2000)
    @Column(name = "work_progress_comment", length = 2000, nullable = false)
    private String work_progress_comment;

    @NotNull
    @DecimalMin(value = "0.1")
    @DecimalMax(value = "2.0")
    @Column(name = "work_attitude", nullable = false)
    private Float work_attitude;

    @NotNull
    @Size(min = 10, max = 2000)
    @Column(name = "work_attitude_comment", length = 2000, nullable = false)
    private String work_attitude_comment;

    @NotNull
    @DecimalMin(value = "0.1")
    @DecimalMax(value = "2.0")
    @Column(name = "work_discipline", nullable = false)
    private Float work_discipline;

    @NotNull
    @Size(min = 10, max = 2000)
    @Column(name = "work_discipline_comment", length = 2000, nullable = false)
    private String work_discipline_comment;

    @NotNull
    @Size(min = 10, max = 2000)
    @Column(name = "assigned_work", length = 2000, nullable = false)
    private String assigned_work;

    @NotNull
    @Size(max = 2000)
    @Column(name = "other_work", length = 2000, nullable = false)
    private String other_work;

    @NotNull
    @Size(min = 10, max = 2000)
    @Column(name = "completed_work", length = 2000, nullable = false)
    private String completed_work;

    @Size(max = 500)
    @Column(name = "uncompleted_work", length = 500)
    private String uncompleted_work;

    @NotNull
    @Size(max = 2000)
    @Column(name = "favourite_work", length = 2000, nullable = false)
    private String favourite_work;

    @Size(max = 2000)
    @Column(name = "unfavourite_work", length = 2000)
    private String unfavourite_work;

    @NotNull
    @DecimalMin(value = "0.1")
    @DecimalMax(value = "2.0")
    @Column(name = "employee_ki_point", nullable = false)
    private Float employee_ki_point;

    @NotNull
    @DecimalMin(value = "0.1")
    @DecimalMax(value = "2.0")
    @Column(name = "leader_ki_point", nullable = false)
    private Float leader_ki_point;

    @NotNull
    @Size(min = 10, max = 2000)
    @Column(name = "leader_comment", length = 2000, nullable = false)
    private String leader_comment;

    @NotNull
    @DecimalMin(value = "0.1")
    @DecimalMax(value = "2.0")
    @Column(name = "boss_ki_point", nullable = false)
    private Float boss_ki_point;

    @NotNull
    @Size(min = 10, max = 2000)
    @Column(name = "boss_comment", length = 2000, nullable = false)
    private String boss_comment;

    @Column(name = "status")
    private String status;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = {"user", "department"}, allowSetters = true)
    private Employee employee;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public KiEmployee id(Long id) {
        this.setId(id);
        return this;
    }

    public LocalDate getDate_time() {
        return this.date_time;
    }

    public void setDate_time(LocalDate date_time) {
        this.date_time = date_time;
    }

    public KiEmployee date_time(LocalDate date_time) {
        this.setDate_time(date_time);
        return this;
    }

    public Float getWork_quantity() {
        return this.work_quantity;
    }

    public void setWork_quantity(Float work_quantity) {
        this.work_quantity = work_quantity;
    }

    public KiEmployee work_quantity(Float work_quantity) {
        this.setWork_quantity(work_quantity);
        return this;
    }

    public String getWork_quantity_comment() {
        return this.work_quantity_comment;
    }

    public void setWork_quantity_comment(String work_quantity_comment) {
        this.work_quantity_comment = work_quantity_comment;
    }

    public KiEmployee work_quantity_comment(String work_quantity_comment) {
        this.setWork_quantity_comment(work_quantity_comment);
        return this;
    }

    public Float getWork_quality() {
        return this.work_quality;
    }

    public void setWork_quality(Float work_quality) {
        this.work_quality = work_quality;
    }

    public KiEmployee work_quality(Float work_quality) {
        this.setWork_quality(work_quality);
        return this;
    }

    public String getWork_quality_comment() {
        return this.work_quality_comment;
    }

    public void setWork_quality_comment(String work_quality_comment) {
        this.work_quality_comment = work_quality_comment;
    }

    public KiEmployee work_quality_comment(String work_quality_comment) {
        this.setWork_quality_comment(work_quality_comment);
        return this;
    }

    public Float getWork_progress() {
        return this.work_progress;
    }

    public void setWork_progress(Float work_progress) {
        this.work_progress = work_progress;
    }

    public KiEmployee work_progress(Float work_progress) {
        this.setWork_progress(work_progress);
        return this;
    }

    public String getWork_progress_comment() {
        return this.work_progress_comment;
    }

    public void setWork_progress_comment(String work_progress_comment) {
        this.work_progress_comment = work_progress_comment;
    }

    public KiEmployee work_progress_comment(String work_progress_comment) {
        this.setWork_progress_comment(work_progress_comment);
        return this;
    }

    public Float getWork_attitude() {
        return this.work_attitude;
    }

    public void setWork_attitude(Float work_attitude) {
        this.work_attitude = work_attitude;
    }

    public KiEmployee work_attitude(Float work_attitude) {
        this.setWork_attitude(work_attitude);
        return this;
    }

    public String getWork_attitude_comment() {
        return this.work_attitude_comment;
    }

    public void setWork_attitude_comment(String work_attitude_comment) {
        this.work_attitude_comment = work_attitude_comment;
    }

    public KiEmployee work_attitude_comment(String work_attitude_comment) {
        this.setWork_attitude_comment(work_attitude_comment);
        return this;
    }

    public Float getWork_discipline() {
        return this.work_discipline;
    }

    public void setWork_discipline(Float work_discipline) {
        this.work_discipline = work_discipline;
    }

    public KiEmployee work_discipline(Float work_discipline) {
        this.setWork_discipline(work_discipline);
        return this;
    }

    public String getWork_discipline_comment() {
        return this.work_discipline_comment;
    }

    public void setWork_discipline_comment(String work_discipline_comment) {
        this.work_discipline_comment = work_discipline_comment;
    }

    public KiEmployee work_discipline_comment(String work_discipline_comment) {
        this.setWork_discipline_comment(work_discipline_comment);
        return this;
    }

    public String getAssigned_work() {
        return this.assigned_work;
    }

    public void setAssigned_work(String assigned_work) {
        this.assigned_work = assigned_work;
    }

    public KiEmployee assigned_work(String assigned_work) {
        this.setAssigned_work(assigned_work);
        return this;
    }

    public String getOther_work() {
        return this.other_work;
    }

    public void setOther_work(String other_work) {
        this.other_work = other_work;
    }

    public KiEmployee other_work(String other_work) {
        this.setOther_work(other_work);
        return this;
    }

    public String getCompleted_work() {
        return this.completed_work;
    }

    public void setCompleted_work(String completed_work) {
        this.completed_work = completed_work;
    }

    public KiEmployee completed_work(String completed_work) {
        this.setCompleted_work(completed_work);
        return this;
    }

    public String getUncompleted_work() {
        return this.uncompleted_work;
    }

    public void setUncompleted_work(String uncompleted_work) {
        this.uncompleted_work = uncompleted_work;
    }

    public KiEmployee uncompleted_work(String uncompleted_work) {
        this.setUncompleted_work(uncompleted_work);
        return this;
    }

    public String getFavourite_work() {
        return this.favourite_work;
    }

    public void setFavourite_work(String favourite_work) {
        this.favourite_work = favourite_work;
    }

    public KiEmployee favourite_work(String favourite_work) {
        this.setFavourite_work(favourite_work);
        return this;
    }

    public String getUnfavourite_work() {
        return this.unfavourite_work;
    }

    public void setUnfavourite_work(String unfavourite_work) {
        this.unfavourite_work = unfavourite_work;
    }

    public KiEmployee unfavourite_work(String unfavourite_work) {
        this.setUnfavourite_work(unfavourite_work);
        return this;
    }

    public Float getEmployee_ki_point() {
        return this.employee_ki_point;
    }

    public void setEmployee_ki_point(Float employee_ki_point) {
        this.employee_ki_point = employee_ki_point;
    }

    public KiEmployee employee_ki_point(Float employee_ki_point) {
        this.setEmployee_ki_point(employee_ki_point);
        return this;
    }

    public Float getLeader_ki_point() {
        return this.leader_ki_point;
    }

    public void setLeader_ki_point(Float leader_ki_point) {
        this.leader_ki_point = leader_ki_point;
    }

    public KiEmployee leader_ki_point(Float leader_ki_point) {
        this.setLeader_ki_point(leader_ki_point);
        return this;
    }

    public String getLeader_comment() {
        return this.leader_comment;
    }

    public void setLeader_comment(String leader_comment) {
        this.leader_comment = leader_comment;
    }

    public KiEmployee leader_comment(String leader_comment) {
        this.setLeader_comment(leader_comment);
        return this;
    }

    public Float getBoss_ki_point() {
        return this.boss_ki_point;
    }

    public void setBoss_ki_point(Float boss_ki_point) {
        this.boss_ki_point = boss_ki_point;
    }

    public KiEmployee boss_ki_point(Float boss_ki_point) {
        this.setBoss_ki_point(boss_ki_point);
        return this;
    }

    public String getBoss_comment() {
        return this.boss_comment;
    }

    public void setBoss_comment(String boss_comment) {
        this.boss_comment = boss_comment;
    }

    public KiEmployee boss_comment(String boss_comment) {
        this.setBoss_comment(boss_comment);
        return this;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public KiEmployee status(String status) {
        this.setStatus(status);
        return this;
    }

    public Employee getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public KiEmployee employee(Employee employee) {
        this.setEmployee(employee);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof KiEmployee)) {
            return false;
        }
        return id != null && id.equals(((KiEmployee) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KiEmployee{" +
            "id=" + getId() +
            ", date_time='" + getDate_time() + "'" +
            ", work_quantity=" + getWork_quantity() +
            ", work_quantity_comment='" + getWork_quantity_comment() + "'" +
            ", work_quality=" + getWork_quality() +
            ", work_quality_comment='" + getWork_quality_comment() + "'" +
            ", work_progress=" + getWork_progress() +
            ", work_progress_comment='" + getWork_progress_comment() + "'" +
            ", work_attitude=" + getWork_attitude() +
            ", work_attitude_comment='" + getWork_attitude_comment() + "'" +
            ", work_discipline=" + getWork_discipline() +
            ", work_discipline_comment='" + getWork_discipline_comment() + "'" +
            ", assigned_work='" + getAssigned_work() + "'" +
            ", other_work='" + getOther_work() + "'" +
            ", completed_work='" + getCompleted_work() + "'" +
            ", uncompleted_work='" + getUncompleted_work() + "'" +
            ", favourite_work='" + getFavourite_work() + "'" +
            ", unfavourite_work='" + getUnfavourite_work() + "'" +
            ", employee_ki_point=" + getEmployee_ki_point() +
            ", leader_ki_point=" + getLeader_ki_point() +
            ", leader_comment='" + getLeader_comment() + "'" +
            ", boss_ki_point=" + getBoss_ki_point() +
            ", boss_comment='" + getBoss_comment() + "'" +
            "}";
    }

    public String getSearchField() {
        return "type,description,reason";
    }

    public String getResponseField() {
        return "type,description,reason";
    }
}
