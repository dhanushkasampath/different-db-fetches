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

    /**
     * For HQL, you don't need to write nativeQuery = true. You can directly write a query based on the entity properties.
     *
     * @param age
     * @return
     */
    @Query("SELECT new com.learn.different_db_fetches.dto.UserNameDto(u.firstName, u.lastName) FROM User u WHERE u.age = :age")
    List<UserNameDto> getUserNameByAgeViaHQL(@Param("age") int age);

    @Query(name = "User.getByAge")
    List<UserNameDto> getUserNameByAge(@Param("age") Integer age);

    @Query(name = "User.findUsersByAge")
    List<UserNameDto> anyNameWeWantToGetUsersByAgeViaNamedQuery(@Param("age") int age);
}
