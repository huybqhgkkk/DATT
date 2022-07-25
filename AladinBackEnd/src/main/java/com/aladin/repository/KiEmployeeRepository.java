package com.aladin.repository;

import com.aladin.domain.KiEmployee;
import com.aladin.service.dto.KiEmployeeOnly;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the KiEmployee entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KiEmployeeRepository extends JpaRepository<KiEmployee, Long> {
    @Modifying
    @Query(value = "update ki_employee set status = ?1 where id = ?2", nativeQuery = true)
    void updatestatus(@Param("status") int status,@Param("id") Long ki_id);

    @Modifying
    @Query(value = "update ki_employee set status = 2", nativeQuery = true)
    void endKi();

    @Query(value = "select count(*) from ki_employee WHERE " +
        "to_char(to_date(date_time, 'DD-MM-YYYY'), 'year') =  to_char(to_date(current_date, 'DD-MM-YYYY'), 'year') " +
        "and to_char(to_date(date_time, 'DD-MM-YYYY'), 'month') =  to_char(to_date(current_date, 'DD-MM-YYYY'), 'month') " +
        "and employee_id = ?1", nativeQuery = true)
    int checkkimonth (Long employee_id);

    @Query(value = "SELECT NEW com.aladin.service.dto.KiEmployeeOnly (a.id,b.full_name,a.status,a.date_time,a.employee_ki_point,a.leader_ki_point,a.boss_ki_point) from KiEmployee a INNER JOIN Employee b on a.employee.id = b.id ORDER by a.date_time desc")
    Page<KiEmployeeOnly> findBoss(Pageable pageable);

    @Query(value = "select NEW com.aladin.service.dto.KiEmployeeOnly (a.id,b.full_name,a.status,a.date_time,a.employee_ki_point,a.leader_ki_point,a.boss_ki_point) from KiEmployee a INNER JOIN Employee b " +
        "on a.employee.id = b.id INNER JOIN Department c  on a.employee.id = b.id and b.user.id = c.user.id WHERE (c.departmentName = (select d.departmentName from Department d where d.user.id = ?1)) ORDER by a.date_time desc")
    Page<KiEmployeeOnly> findLeader(String user_id,Pageable pageable);

    @Query(value = "select NEW com.aladin.service.dto.KiEmployeeOnly (a.id,b.full_name,a.status,a.date_time,a.employee_ki_point,a.leader_ki_point,a.boss_ki_point) from KiEmployee a INNER JOIN Employee b on a.employee.id = b.id where b.user.id = ?1 ORDER by a.date_time desc")
    Page<KiEmployeeOnly> findUser(String user_id,Pageable pageable);

    @Query(value = "select status from ki_employee where id = ?1", nativeQuery = true)
    Long getstatus(Long ki_id);

    @Query(value = "select user_id from employee WHERE id = (select employee_id from ki_employee where id =?1)", nativeQuery = true)
    Long getUserId(Long ki_id);

}
