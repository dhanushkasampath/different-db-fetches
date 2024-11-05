package com.learn.different_db_fetches.repository;

import com.learn.different_db_fetches.dto.UserNameDto;
import com.learn.different_db_fetches.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT new com.learn.different_db_fetches.dto.UserNameDto(u.firstName, u.lastName) FROM User u WHERE u.age = :age")
    List<UserNameDto> getUserNameByAgeViaHQL(@Param("age") int age);

//    @Query(name = "User.getByAge", nativeQuery = true)
//    List<UserNameDto> fetchUserNameByAgeViaStoredProcedure(@Param("age") Integer age);

//    @Query(value = "SELECT first_name, last_name FROM user WHERE age = ?1", nativeQuery = true)
//    @Query(value = "SELECT new com.learn.different_db_fetches.dto.UserNameDto(first_name, last_name) FROM user WHERE age = ?1", nativeQuery = true)
//    List<UserNameDto> getUserNameByAgeViaNativeQuery(@Param("age") int age);

    @Query(name = "User.findUsersByAge")
    List<UserNameDto> anyNameWeWantToGetUsersByAgeViaNamedQuery(@Param("age") int age);
}
