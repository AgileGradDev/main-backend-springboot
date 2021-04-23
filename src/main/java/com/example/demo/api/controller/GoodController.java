package com.example.demo.api.controller;

import com.example.demo.api.entity.Good;
import com.example.demo.api.entity.User;
import com.example.demo.api.model.response.CommonResult;
import com.example.demo.api.model.response.SingleResult;
import com.example.demo.api.service.GoodService;
import com.example.demo.api.service.ResponseService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"Good"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/good")
public class GoodController {

    private final ResponseService responseService;
    private final GoodService goodService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "좋아요", notes = "로그인 상태에서 좋아요를 한다.")
    @PostMapping(value = "/{storeName}")
    public SingleResult<Good> like(@PathVariable String storeName){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return responseService.getSingleResult(goodService.good(user, storeName));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "좋아요 취소", notes = "로그인 상태에서 좋아요 취소를 한다.")
    @DeleteMapping(value = "/{storeName}")
    public CommonResult unlike(@PathVariable String storeName){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return responseService.getSingleResult(goodService.cancelGood(user, storeName));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "로그인한 유저의 좋아요 목록들", notes = "로그인한 유저의 좋아요 목록 받아온다.")
    @GetMapping(value = "/user")
    public CommonResult unlike(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return responseService.getListResult(goodService.userLikes(user));
    }

    @ApiOperation(value = "가게 좋아요 개수 받아오기", notes = "가게 좋아요 개수 받아오기.")
    @GetMapping(value = "/{storeName}")
    public CommonResult count(@PathVariable String storeName){
        return responseService.getSingleResult(goodService.countLike(storeName));
    }
}
