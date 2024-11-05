package com.learn.different_db_fetches.service.impl;

import com.learn.different_db_fetches.dto.UserNameDto;
import com.learn.different_db_fetches.entity.User;
import com.learn.different_db_fetches.enums.FetchType;
import com.learn.different_db_fetches.repository.UserRepository;
import com.learn.different_db_fetches.service.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    public static final String FETCHED_DATA = "Fetched data: {}";
    private final UserRepository userRepository;

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public List<UserNameDto> getUserNameListByAge(Integer age, FetchType fetchType) {
        return switch (fetchType){
            case CRITERIA_API ->  getDataByCriteriaApi(age);
            case HQL -> getDataByHQL(age);
            case NATIVE_QUERY -> getDataByNativeQuery(age);
            case STORED_PROCEDURE -> getDataByStoredProcedure(age);
            case NAMED_QUERY -> getDataByNamedQuery(age);
        };
    }

    private List<UserNameDto> getDataByNamedQuery(Integer age) {
        log.info("fetching data by named query...");
        List<UserNameDto> userNameDtoList = userRepository.anyNameWeWantToGetUsersByAgeViaNamedQuery(age);
        log.info(FETCHED_DATA, userNameDtoList);
        return userNameDtoList;
    }

    /**
     * Below is the stored procedure I executed against the schema

      DELIMITER $$
      CREATE PROCEDURE get_username_by_age(IN given_age INT)
      BEGIN
          SELECT first_name, last_name from user WHERE age = given_age;
      END $$
      DELIMITER ;

     */
    private List<UserNameDto> getDataByStoredProcedure(Integer age) {
        log.info("fetching data by stored procedures...");
        List<UserNameDto> userNameDtoList = userRepository.getUserNameByAge(age);
        log.info(FETCHED_DATA, userNameDtoList);
        return userNameDtoList;
    }

    /**
     * A Native SQL Query allows you to write SQL exactly as it is executed by the database, bypassing Hibernate's entity mapping.
     * In this case, we are using a query to directly retrieve first_name and last_name from the user table,
     * which may or may not be associated with a JPA entity.
     *
     * nativeQuery = true tells Spring Data JPA that this is a native SQL query.
     *
     * @param age
     * @return
     */
    private List<UserNameDto> getDataByNativeQuery(Integer age) {
        log.info("fetching data by native query...");
        Query query = entityManager.createNativeQuery("SELECT u.first_name, u.last_name FROM user u WHERE u.age = :age", "UserNameDtoMapping");
        query.setParameter("age", age);
        List<UserNameDto> userNameDtoList = query.getResultList();
        log.info(FETCHED_DATA, userNameDtoList);
        return userNameDtoList;
    }

    private List<UserNameDto> getDataByHQL(Integer age) {
        log.info("fetching data by hql...");
        List<UserNameDto> userNameDtoList = userRepository.getUserNameByAgeViaHQL(age);
        log.info(FETCHED_DATA, userNameDtoList);
        return userNameDtoList;
    }

    private List<UserNameDto> getDataByCriteriaApi(Integer age) {
        log.info("fetching data by criteria api...");
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserNameDto> query = cb.createQuery(UserNameDto.class);

        //Root<User>: Represents the entity (User) in the query.
        Root<User> user = query.from(User.class);

        // Select firstName and lastName only
        query.select(cb.construct(UserNameDto.class, user.get("firstName"), user.get("lastName")));

        // Set the where condition for age
        Predicate agePredicate = cb.equal(user.get("age"), age);
        query.where(agePredicate);

        // Execute the query
        TypedQuery<UserNameDto> typedQuery = entityManager.createQuery(query);
        List<UserNameDto> userNameDtoList = typedQuery.getResultList();
        log.info(FETCHED_DATA, userNameDtoList);
        return userNameDtoList;
    }
}