package com.aladin.repository;

import com.aladin.domain.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the {@link User} entity.
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findOneByLogin(String login);

    @EntityGraph(attributePaths = "authorities")
    Optional<User> findOneWithAuthoritiesByLogin(String login);

    Page<User> findAllByIdNotNullAndActivatedIsTrue(Pageable pageable);

    @Query(value = "select mail from department where id = (select department_id from employee WHERE id =?1)", nativeQuery = true)
    String findEmailUser(Long employee_id) ;

    @Query(value = "select mail from department where department_name=( select department_name from department " +
        "where id = (select department_id from employee WHERE id =?1)) and isleader =1 and ROWNUM =1", nativeQuery = true)
    String findEmailLeader(Long employee_id) ;

    @Query(value = "select mail from department where department_name LIKE '%BOSS%' and ROWNUM =1", nativeQuery = true)
    String findEmailBoss() ;
    @Query(value = "select count(*) from department WHERE user_id =?1 and department_name like '%BOSS%'", nativeQuery = true)
    int checkboss(String user_id);
    @Query(value = "select count(*) from department WHERE user_id =?1 and department_name like '%MONEY%'", nativeQuery = true)
    int checkmoney(String user_id);
    @Query(value = "select count(*) from department WHERE user_id =?1 and isleader = 1", nativeQuery = true)
    int checkLeader(String user_id);
    @Query(value = "select * from jhi_User WHERE id=?1", nativeQuery = true)
    Page<User> findOneUser(String user_id, Pageable pageable);
}
