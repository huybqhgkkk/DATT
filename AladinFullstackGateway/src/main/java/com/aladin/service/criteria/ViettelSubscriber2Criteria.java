package com.aladin.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.aladin.domain.ViettelSubscriber2} entity. This class is used
 * in {@link com.aladin.web.rest.ViettelSubscriber2Resource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /viettel-subscriber-2-s?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ViettelSubscriber2Criteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter msisdn;

    public ViettelSubscriber2Criteria() {}

    public ViettelSubscriber2Criteria(ViettelSubscriber2Criteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.msisdn = other.msisdn == null ? null : other.msisdn.copy();
    }

    @Override
    public ViettelSubscriber2Criteria copy() {
        return new ViettelSubscriber2Criteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public StringFilter name() {
        if (name == null) {
            name = new StringFilter();
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getMsisdn() {
        return msisdn;
    }

    public StringFilter msisdn() {
        if (msisdn == null) {
            msisdn = new StringFilter();
        }
        return msisdn;
    }

    public void setMsisdn(StringFilter msisdn) {
        this.msisdn = msisdn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ViettelSubscriber2Criteria that = (ViettelSubscriber2Criteria) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(msisdn, that.msisdn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, msisdn);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ViettelSubscriber2Criteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (msisdn != null ? "msisdn=" + msisdn + ", " : "") +
            "}";
    }
}
