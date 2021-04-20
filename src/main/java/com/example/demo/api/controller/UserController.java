package com.example.demo.api.controller;

import com.example.demo.api.advice.exception.CUserExistException;
import com.example.demo.api.advice.exception.CUserNotFoundException;
import com.example.demo.api.entity.User;
import com.example.demo.api.model.response.CommonResult;
import com.example.demo.api.model.response.SingleResult;
import com.example.demo.api.repo.UserJpaRepo;
import com.example.demo.api.service.ResponseService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collections;
import java.util.List;

@Api(tags = {"Admin이나 해당 이용자의 user 정보 관리"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/admin")
public class UserController {
    private final UserJpaRepo userJpaRepo;

    private final ResponseService responseService;
    private final PasswordEncoder passwordEncoder;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @PreAuthorize("isAuthenticated() and hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "회원 조회", notes = "email로 회원을 조회한다")
    @GetMapping(value = "/user/{email}")
    public SingleResult<User> findUserById(@ApiParam(value = "회원 email", required = true) @PathVariable String email) {
        return responseService.getSingleResult(userJpaRepo.findByEmail(email).orElseThrow(CUserNotFoundException::new));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @PreAuthorize("isAuthenticated() and hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "회원 생성", notes = "admin 입장에서 회원을 생성한다.")
    @PostMapping(value = "/user")
    public User save(@ApiParam(value = "회원 나이", required = true) @RequestParam int age,
                     @ApiParam(value = "회원 이메일", required = true) @RequestParam String email,
                     @ApiParam(value = "회원 이름", required = true) @RequestParam String name,
                     @ApiParam(value = "회원 전화번호", required = true) @RequestParam String phone,
                     @ApiParam(value = "회원 비밀번호", required = true) @RequestParam String password,
                     @ApiParam(value = "회원 성별", required = true) @RequestParam char sex,
                     @ApiParam(value = "admin 권한 줄거면 Y or N", required = false) @RequestParam String is_admin
                     ) {

        if(userJpaRepo.findByEmail(email).isPresent())
            throw new CUserExistException();

        User user = User.builder()
                .age(age)
                .email(email)
                .name(name)
                .phone(phone)
                .password(passwordEncoder.encode(password))
                .sex(sex)
                .roles(Collections.singletonList("ROLE_USER"))
                .build();

        if(is_admin.equals("Y")) {
            user.setRoles(Collections.singletonList("ROLE_ADMIN"));
        }

        return userJpaRepo.save(user);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @PreAuthorize("isAuthenticated() and hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "모든 회원 조회", notes = "admin 입장에서 모든 회원을 조회한다")
    @GetMapping(value = "/users")
    public List<User> findAllUser() {
        return userJpaRepo.findAll();
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "현재 로그인한 회원 조회", notes = "현재 로그인한 회원의 정보를 조회한다")
    @GetMapping(value = "/user/now")
    public SingleResult<User> findUserById() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = (User) authentication.getPrincipal();

        return responseService.getSingleResult(user);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "현재 로그인한 회원 정보 수정", notes = "현재 로그인한 회원의 정보를 수정한다")
    @PutMapping(value = "/user/now/update")
    public SingleResult<User> update_now(
            @ApiParam(value = "회원 나이", required = true) @RequestParam int age,
            @ApiParam(value = "회원 이름", required = true) @RequestParam String name,
            @ApiParam(value = "회원 전화번호", required = true) @RequestParam String phone,
            @ApiParam(value = "회원 비밀번호", required = true) @RequestParam String password,
            @ApiParam(value = "회원 성별", required = true) @RequestParam char sex) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

//        System.out.println(authentication);
        User user = (User) authentication.getPrincipal();
        user.setAge(age)
                .setName(name)
                .setPhone(phone)
                .setPassword(passwordEncoder.encode(password))
                .setSex(sex);

        return responseService.getSingleResult(userJpaRepo.save(user));
    }







    @PreAuthorize("isAuthenticated() and hasRole('ROLE_ADMIN')")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 수정", notes = "admin 입장에서 회원정보를 수정한다")
    @PutMapping(value = "/user")
    public SingleResult<User> modify(
            @ApiParam(value = "회원 나이", required = true) @RequestParam int age,
            @ApiParam(value = "기존 회원 이메일", required = true) @RequestParam String email,
            @ApiParam(value = "회원 이메일", required = true) @RequestParam String new_email,
            @ApiParam(value = "회원 이름", required = true) @RequestParam String name,
            @ApiParam(value = "회원 전화번호", required = true) @RequestParam String phone,
            @ApiParam(value = "회원 비밀번호", required = true) @RequestParam String password,
            @ApiParam(value = "회원 성별", required = true) @RequestParam char sex) {

        if(userJpaRepo.findByEmail(new_email).isPresent())
            throw new CUserExistException();

        User user = userJpaRepo.findByEmail(email).orElseThrow(CUserExistException::new);

        User new_user = user
                .setAge(age)
                .setEmail(email)
                .setName(name)
                .setPhone(phone)
                .setPassword(password)
                .setSex(sex);
        return responseService.getSingleResult(userJpaRepo.save(new_user));
    }

    @PreAuthorize("isAuthenticated() and hasRole('ROLE_ADMIN')")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 삭제", notes = "admin 입장에서 user id 로 회원정보를 삭제한다")
    @DeleteMapping(value = "/user/{email}")
    public CommonResult delete(
            @ApiParam(value = "회원 email", required = true) @PathVariable String email) {
        User user = userJpaRepo.findByEmail(email).orElseThrow(CUserNotFoundException::new);

        userJpaRepo.deleteById(user.getId());
        // 성공 결과 정보만 필요한경우 getSuccessResult()를 이용하여 결과를 출력한다.
        return responseService.getSuccessResult();
    }
}