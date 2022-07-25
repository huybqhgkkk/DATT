package com.aladin.service.dto;

import java.io.Serializable;
import java.time.LocalDate;

public class EmployeeOnly implements Serializable {
    private Long id;
    String full_name;
    String email;
    LocalDate date_of_birth;
    LocalDate first_day_work;
    String phone_number;
    String avatar;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(LocalDate date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public LocalDate getFirst_day_work() {
        return first_day_work;
    }

    public void setFirst_day_work(LocalDate first_day_work) {
        this.first_day_work = first_day_work;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public EmployeeOnly(Long id, String full_name, String email, LocalDate date_of_birth, LocalDate first_day_work, String phone_number, String avatar) {
        this.id = id;
        this.full_name = full_name;
        this.email = email;
        this.date_of_birth = date_of_birth;
        this.first_day_work = first_day_work;
        this.phone_number = phone_number;
        this.avatar = avatar;
    }

    public EmployeeOnly() {

    }

    public String getSearchField() {
        return "full_name,email,phone_number,department.departmentName";
    }

    public String getResponseField() {
        return "full_name,email,phone_number,first_day_work,date_of_birth,avatar,department";
    }
}
