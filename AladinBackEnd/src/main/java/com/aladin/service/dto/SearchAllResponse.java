package com.aladin.service.dto;

import com.aladin.domain.Department;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchAllResponse {
    private Integer id;
    private String position;
    private String description;
    private String require;
    private String benefit;
    private Long amount;
    private String job;
    private String location;
    private String level;
    private String duration;
    private String type;
    private String reason;
    private String index;
    private String full_name;
    private String avatar;
    private String email;
    private String date_of_birth;
    private String first_day_work;
    private String phone_number;
    private String displayInfo;
    private DepartmentDTO department;


    public SearchAllResponse() {

    }

    public SearchAllResponse(Integer id, String position, String description, String require, String benefit, Long amount, String job, String location, String level, String duration, String type, String reason, String index, String full_name, String avatar, String email, String date_of_birth, String first_day_work, String phone_number, String displayInfo, DepartmentDTO department) {
        this.id = id;
        this.position = position;
        this.description = description;
        this.require = require;
        this.benefit = benefit;
        this.amount = amount;
        this.job = job;
        this.location = location;
        this.level = level;
        this.duration = duration;
        this.type = type;
        this.reason = reason;
        this.index = index;
        this.full_name = full_name;
        this.avatar = avatar;
        this.email = email;
        this.date_of_birth = date_of_birth;
        this.first_day_work = first_day_work;
        this.phone_number = phone_number;
        this.displayInfo = displayInfo;
        this.department = department;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRequire() {
        return require;
    }

    public void setRequire(String require) {
        this.require = require;
    }

    public String getBenefit() {
        return benefit;
    }

    public void setBenefit(String benefit) {
        this.benefit = benefit;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
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

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getFirst_day_work() {
        return first_day_work;
    }

    public void setFirst_day_work(String first_day_work) {
        this.first_day_work = first_day_work;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getDisplayInfo() {
        return displayInfo;
    }

    public void setDisplayInfo(String displayInfo) {
        this.displayInfo = displayInfo;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public DepartmentDTO getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentDTO department) {
        this.department = department;
    }
}
