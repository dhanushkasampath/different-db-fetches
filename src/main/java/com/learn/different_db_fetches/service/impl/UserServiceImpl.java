package com.learn.different_db_fetches.service.impl;

import com.learn.different_db_fetches.dto.UserNameDto;
import com.learn.different_db_fetches.enums.FetchType;
import com.learn.different_db_fetches.repository.UserRepository;
import com.learn.different_db_fetches.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

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
        return List.of();
    }

    private List<UserNameDto> getDataByStoredProcedure(Integer age) {
        log.info("fetching data by stored procedures...");
        return List.of();
    }

    private List<UserNameDto> getDataByNativeQuery(Integer age) {
        log.info("fetching data by native query...");
        return List.of();
    }

    private List<UserNameDto> getDataByHQL(Integer age) {
        log.info("fetching data by hql...");
        List<UserNameDto> userNameDtoList = userRepository.findFirstNameAndLastNameByAge(age);
        log.info("Fetched data: {}", userNameDtoList);
        return userNameDtoList;
    }

    private List<UserNameDto> getDataByCriteriaApi(Integer age) {
        log.info("fetching data by criteria api...");
        return List.of();
    }
}