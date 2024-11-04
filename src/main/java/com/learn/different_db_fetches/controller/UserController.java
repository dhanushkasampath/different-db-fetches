package com.learn.different_db_fetches.controller;

import com.learn.different_db_fetches.dto.UserNameDto;
import com.learn.different_db_fetches.enums.FetchType;
import com.learn.different_db_fetches.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{age}")
    public ResponseEntity<List<UserNameDto>> getUserNameListByAge(@PathVariable Integer age, @RequestParam FetchType fetchType){
        List<UserNameDto> userNameDtoList = userService.getUserNameListByAge(age, fetchType);
        return new ResponseEntity<>(userNameDtoList, HttpStatus.OK);
    }
}
