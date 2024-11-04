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

    @Query(name = "User.getByAge", nativeQuery = true)
    List<UserNameDto> getUserNameByAgeViaStoredProcedure(@Param("age") Integer age);
}
