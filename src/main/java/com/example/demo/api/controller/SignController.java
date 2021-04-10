package com.example.demo.api.controller;


import com.example.demo.api.advice.exception.CEmailSigninFailedException;
import com.example.demo.api.advice.exception.CUserExistException;
import com.example.demo.api.advice.exception.CUserNotFoundException;
import com.example.demo.api.config.security.JwtTokenProvider;
import com.example.demo.api.entity.User;
import com.example.demo.api.model.response.CommonResult;
import com.example.demo.api.model.response.SingleResult;
import com.example.demo.api.repo.UserJpaRepo;
import com.example.demo.api.service.ResponseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;

@Api(tags = {"Sign"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/sign")
public class SignController {

    private final UserJpaRepo userJpaRepo;
    private final JwtTokenProvider jwtTokenProvider;
    private final ResponseService responseService;
    private final PasswordEncoder passwordEncoder;

    @ApiOperation(value = "로그인", notes = "이메일 회원 로그인을 한다.")
    @PostMapping(value = "/signin")
    public SingleResult<String> signin(@ApiParam(value = "회원ID : 이메일", required = true) @RequestParam String id,
                                       @ApiParam(value = "비밀번호", required = true) @RequestParam String password) {
        User user = userJpaRepo.findByEmail(id).orElseThrow(CEmailSigninFailedException::new);
        if (!passwordEncoder.matches(password, user.getPassword()))
            throw new CEmailSigninFailedException();

        return responseService.getSingleResult(jwtTokenProvider.createToken(String.valueOf(user.getId()), user.getRoles()));

    }

    @ApiOperation(value = "가입", notes = "회원가입을 한다.")
    @PostMapping(value = "/signup")
    public CommonResult signin(@ApiParam(value = "회원ID (이메일)", required = true) @RequestParam String id,
                               @ApiParam(value = "비밀번호", required = true) @RequestParam String password,
                               @ApiParam(value = "이름", required = true) @RequestParam String name,
                               @ApiParam(value = "전화번호", required = true) @RequestParam String phonenumber,
                               @ApiParam(value = "나이", required = true) @RequestParam int age,
                               @ApiParam(value = "성별 : M or W", required = true) @RequestParam char sex
    ) {

        if(userJpaRepo.findByEmail(id).isPresent())
            throw new CUserExistException();

        User user = User.builder()
                .email(id)
                .password(passwordEncoder.encode(password))
                .name(name)
                .age(age)
                .phonenumber(phonenumber)
                .sex(sex)
                .roles(Collections.singletonList("ROLE_USER"))
                .build();
        if(id.equals("admin")) {
            user.setRoles(Collections.singletonList("ROLE_ADMIN"));
        }
        userJpaRepo.save(user);
        return responseService.getSuccessResult();
    }
}