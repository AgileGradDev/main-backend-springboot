package com.example.demo.api.controller;

import com.example.demo.api.entity.User;
import com.example.demo.api.model.response.CommonResult;
import com.example.demo.api.model.response.SingleResult;
import com.example.demo.api.repo.UserJpaRepo;
import com.example.demo.api.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"Admin의 user관리"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/admin")
public class UserController {

    private final UserJpaRepo userJpaRepo;
    private final ResponseService responseService;

    @ApiOperation(value = "모든 회원 조회", notes = "admin 입장에서 모든 회원을 조회한다")
    @GetMapping(value = "/users")
    public List<User> findAllUser() {
        return userJpaRepo.findAll();
    }

    @ApiOperation(value = "회원 조회", notes = "id로 회원을 조회한다")
    @GetMapping(value = "/user/{id}")
    public SingleResult<User> findUserById(@ApiParam(value = "회원ID", required = true) @PathVariable Long id) {
        return responseService.getSingleResult(userJpaRepo.findById(id).orElse(null));
    }

    @ApiOperation(value = "회원 생성", notes = "admin 입장에서 회원을 생성한다.")
    @PostMapping(value = "/user")
    public User save(@ApiParam(value = "회원 나이", required = true) @RequestParam int age,
                     @ApiParam(value = "회원 이메일", required = true) @RequestParam String email,
                     @ApiParam(value = "회원 이름", required = true) @RequestParam String name,
                     @ApiParam(value = "회원 전화번호", required = true) @RequestParam String phonenumber,
                     @ApiParam(value = "회원 성별", required = true) @RequestParam char sex) {
        User user = User.builder()
                .user_age(age)
                .user_email(email)
                .user_name(name)
                .user_phonenumber(phonenumber)
                .user_sex(sex)
                .build();
        return userJpaRepo.save(user);
    }

    @ApiOperation(value = "회원 수정", notes = "admin 입장에서 회원정보를 수정한다")
    @PutMapping(value = "/user")
    public SingleResult<User> modify(
            @ApiParam(value = "회원 나이", required = true) @RequestParam int age,
            @ApiParam(value = "회원 이메일", required = true) @RequestParam String email,
            @ApiParam(value = "회원 이름", required = true) @RequestParam String name,
            @ApiParam(value = "회원 전화번호", required = true) @RequestParam String phonenumber,
            @ApiParam(value = "회원 성별", required = true) @RequestParam char sex) {
        User user = User.builder()
                .user_age(age)
                .user_email(email)
                .user_name(name)
                .user_phonenumber(phonenumber)
                .user_sex(sex)
                .build();
        return responseService.getSingleResult(userJpaRepo.save(user));
    }

    @ApiOperation(value = "회원 삭제", notes = "admin 입장에서 user email 로 회원정보를 삭제한다")
    @DeleteMapping(value = "/user/{id}")
    public CommonResult delete(
            @ApiParam(value = "회원번호", required = true) @PathVariable Long id) {
        userJpaRepo.deleteById((Long) id);
        // 성공 결과 정보만 필요한경우 getSuccessResult()를 이용하여 결과를 출력한다.
        return responseService.getSuccessResult();
    }
}