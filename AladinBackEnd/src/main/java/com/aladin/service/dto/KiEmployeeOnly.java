package com.aladin.service.dto;


import java.io.Serializable;
import java.time.LocalDate;

public class KiEmployeeOnly implements Serializable {
   private Long id;
   private String full_name;
   private String status;
   private LocalDate date_time;
   private Float employee_ki_point;
   private Float leader_ki_point;
   private Float boss_ki_point;
   public KiEmployeeOnly(Long id, String full_name, String status, LocalDate date_time, Float employee_ki_point, Float leader_ki_point, Float boss_ki_point) {
        this.id = id;
        this.full_name = full_name;
        this.status = status;
        this.date_time = date_time;
        this.employee_ki_point = employee_ki_point;
        this.leader_ki_point = leader_ki_point;
        this.boss_ki_point = boss_ki_point;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDate_time() {
        return date_time;
    }

    public void setDate_time(LocalDate date_time) {
        this.date_time = date_time;
    }

    public Float getEmployee_ki_point() {
        return employee_ki_point;
    }

    public void setEmployee_ki_point(Float employee_ki_point) {
        this.employee_ki_point = employee_ki_point;
    }

    public Float getLeader_ki_point() {
        return leader_ki_point;
    }

    public void setLeader_ki_point(Float leader_ki_point) {
        this.leader_ki_point = leader_ki_point;
    }

    public Float getBoss_ki_point() {
        return boss_ki_point;
    }

    public void setBoss_ki_point(Float boss_ki_point) {
        this.boss_ki_point = boss_ki_point;
    }
}
