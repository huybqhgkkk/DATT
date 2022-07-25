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
import tech.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link com.aladin.domain.Heroes3} entity. This class is used
 * in {@link com.aladin.web.rest.Heroes3Resource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /heroes-3-s?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class Heroes3Criteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private ZonedDateTimeFilter create_time;

    private LongFilter viettelSubscriber2Id;

    public Heroes3Criteria() {}

    public Heroes3Criteria(Heroes3Criteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.create_time = other.create_time == null ? null : other.create_time.copy();
        this.viettelSubscriber2Id = other.viettelSubscriber2Id == null ? null : other.viettelSubscriber2Id.copy();
    }

    @Override
    public Heroes3Criteria copy() {
        return new Heroes3Criteria(this);
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

    public ZonedDateTimeFilter getCreate_time() {
        return create_time;
    }

    public ZonedDateTimeFilter create_time() {
        if (create_time == null) {
            create_time = new ZonedDateTimeFilter();
        }
        return create_time;
    }

    public void setCreate_time(ZonedDateTimeFilter create_time) {
        this.create_time = create_time;
    }

    public LongFilter getViettelSubscriber2Id() {
        return viettelSubscriber2Id;
    }

    public LongFilter viettelSubscriber2Id() {
        if (viettelSubscriber2Id == null) {
            viettelSubscriber2Id = new LongFilter();
        }
        return viettelSubscriber2Id;
    }

    public void setViettelSubscriber2Id(LongFilter viettelSubscriber2Id) {
        this.viettelSubscriber2Id = viettelSubscriber2Id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Heroes3Criteria that = (Heroes3Criteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(create_time, that.create_time) &&
            Objects.equals(viettelSubscriber2Id, that.viettelSubscriber2Id)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, create_time, viettelSubscriber2Id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Heroes3Criteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (create_time != null ? "create_time=" + create_time + ", " : "") +
            (viettelSubscriber2Id != null ? "viettelSubscriber2Id=" + viettelSubscriber2Id + ", " : "") +
            "}";
    }
}
