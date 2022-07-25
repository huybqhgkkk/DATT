//package com.aladin.repository;
//
//import com.aladin.domain.Authority;
//import com.aladin.domain.User;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.r2dbc.repository.Query;
//import org.springframework.data.r2dbc.repository.R2dbcRepository;
//import org.springframework.stereotype.Repository;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
///**
// * Spring Data R2DBC repository for the {@link User} entity.
// */
//@Repository
//public interface UserGroupRepository extends R2dbcRepository<User, String>, UserRepositoryInternal {
//
//    @Query("INSERT INTO USERGROUP (GROUPNAME,  MAIL) VALUES ('admin', 'hientv12121@gmail.com'")
//    Mono<Void> saveUserGroup();
//
//    @Query("DELETE FROM jhi_user_authority")
//    Mono<Void> deleteAllUserAuthorities();
//
//    @Query("DELETE FROM jhi_user_authority WHERE user_id = :userId")
//    Mono<Void> deleteUserAuthorities(Long userId);
//
//    @Query("select * from jhi_authority")
//    Flux<Authority> loadAllAuthorities();
//
//}
//
