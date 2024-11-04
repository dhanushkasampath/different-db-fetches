package com.learn.different_db_fetches.service;

import com.learn.different_db_fetches.dto.UserNameDto;
import com.learn.different_db_fetches.enums.FetchType;

import java.util.List;

public interface UserService {
    List<UserNameDto> getUserNameListByAge(Integer age, FetchType fetchType);
}
