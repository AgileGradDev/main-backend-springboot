package com.example.demo.api.controller;


import com.example.demo.api.advice.exception.CEmailSigninFailedException;
import com.example.demo.api.advice.exception.CUserExistException;
import com.example.demo.api.advice.exception.CUserNotFoundException;
import com.example.demo.api.config.security.JwtTokenProvider;
import com.example.demo.api.entity.KakaoProfile;
import com.example.demo.api.entity.User;
import com.example.demo.api.model.response.CommonResult;
import com.example.demo.api.model.response.SingleResult;
import com.example.demo.api.repo.UserJpaRepo;
import com.example.demo.api.service.ResponseService;
import com.example.demo.api.service.user.KakaoService;
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
@RequestMapping(value = "/auth")
public class AuthController {

    private final UserJpaRepo userJpaRepo;
    private final JwtTokenProvider jwtTokenProvider;
    private final ResponseService responseService;
    private final PasswordEncoder passwordEncoder;
    private final KakaoService kakaoService;

    @ApiOperation(value = "로그인", notes = "이메일 회원 로그인을 한다.")
    @PostMapping(value = "/signin")
    public SingleResult<String> signIn(@ApiParam(value = "회원ID : 이메일", required = true) @RequestParam String id,
                                       @ApiParam(value = "비밀번호", required = true) @RequestParam String password) {
        User user = userJpaRepo.findByEmail(id).orElseThrow(CEmailSigninFailedException::new);
        if (!passwordEncoder.matches(password, user.getPassword()))
            throw new CEmailSigninFailedException();

        return responseService.getSingleResult(jwtTokenProvider.createToken(String.valueOf(user.getId()), user.getRoles()));

    }

    @ApiOperation(value = "가입", notes = "회원가입을 한다.")
    @PostMapping(value = "/signup")
    public CommonResult signUp(@ApiParam(value = "회원ID (이메일)", required = true) @RequestParam String id,
                               @ApiParam(value = "비밀번호", required = true) @RequestParam String password,
                               @ApiParam(value = "이름", required = true) @RequestParam String name,
                               @ApiParam(value = "전화번호", required = true) @RequestParam String phone,
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
                .phone(phone)
                .sex(sex)
                .roles(Collections.singletonList("ROLE_USER"))
                .build();
        if(id.equals("admin")) {
            user.setRoles(Collections.singletonList("ROLE_ADMIN"));
        }
        userJpaRepo.save(user);
        return responseService.getSuccessResult();
    }

    @ApiOperation(value = "소셜 로그인", notes = "소셜 회원 로그인을 한다.")
    @PostMapping(value = "/signin/{provider}")
    public SingleResult<String> signInByProvider(
            @ApiParam(value = "서비스 제공자 provider", required = true, defaultValue = "kakao") @PathVariable String provider,
            @ApiParam(value = "소셜 access_token", required = true) @RequestParam String accessToken) {

        KakaoProfile profile = kakaoService.getKakaoProfile(accessToken);
        User user = userJpaRepo.findByEmailAndProvider(String.valueOf(profile.getId()), provider).orElseThrow(CUserNotFoundException::new);
        return responseService.getSingleResult(jwtTokenProvider.createToken(String.valueOf(user.getId()), user.getRoles()));
    }

    @ApiOperation(value = "소셜 계정 가입", notes = "소셜 계정 회원가입을 한다.")
    @PostMapping(value = "/signup/{provider}")
    public CommonResult signUpByProvider(@ApiParam(value = "서비스 제공자 provider", required = true, defaultValue = "kakao") @PathVariable String provider,
                                       @ApiParam(value = "소셜 access_token", required = true) @RequestParam String accessToken,
                                       @ApiParam(value = "이름", required = true) @RequestParam String name,
                                       @ApiParam(value = "전화번호", required = true) @RequestParam String phone,
                                       @ApiParam(value = "소셜 access_token", required = true) @RequestParam int age,
                                       @ApiParam(value = "소셜 access_token", required = true) @RequestParam char sex) {

        KakaoProfile profile = kakaoService.getKakaoProfile(accessToken);
        Optional<User> user = userJpaRepo.findByEmailAndProvider(String.valueOf(profile.getId()), provider);
        if(user.isPresent())
            throw new CUserExistException();

        userJpaRepo.save(User.builder()
                .email(String.valueOf(profile.getId()))
                .provider(provider)
                .name(name)
                .age(age)
                .phone(phone)
                .sex(sex)
                .roles(Collections.singletonList("ROLE_USER"))
                .build());
        return responseService.getSuccessResult();
    }
}