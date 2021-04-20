package com.example.demo.api.controller;

import com.example.demo.api.entity.Comment;
import com.example.demo.api.entity.User;
import com.example.demo.api.model.response.CommonResult;
import com.example.demo.api.model.response.ListResult;
import com.example.demo.api.model.response.SingleResult;
import com.example.demo.api.service.CommentService;
import com.example.demo.api.service.ResponseService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = {"Comment"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/comment")
public class CommentController {

    private final ResponseService responseService;
    private final CommentService commentService;

    @ApiOperation(value = "식당 기준 댓글 조회", notes = "식당 기준으로 댓글 정보를 조회한다")
    @GetMapping(value = "/{storeName}")
    public ListResult<Comment> commentsStore(@PathVariable String storeName){
        return responseService.getListResult(commentService.findComments(storeName));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "현재 로그인한 user 기준 댓글 조회", notes = "현재 로그인한 user 기준으로 댓글 정보를 조회한다")
    @GetMapping(value = "/user")
    public ListResult<Comment> commentsUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return responseService.getListResult(commentService.findCommentsUser(user));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @PreAuthorize("isAuthenticated() or hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "댓글 작성", notes = "댓글 작성 한다")
    @PostMapping(value = "/{storeName}")
    public SingleResult<Comment> comment(@PathVariable String storeName,
                                         @ApiParam(value = "댓글 내용", required = true)@RequestParam String content,
                                         @ApiParam(value = "평점", required = true)@RequestParam float rating){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return responseService.getSingleResult(commentService.writeComment(user.getId(), storeName, content, rating));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @PreAuthorize("isAuthenticated() or hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "댓글 수정", notes = "댓글을 수정한다.")
    @PutMapping(value = "/{commentId}")
    public SingleResult<Comment> updatePost(@PathVariable Long commentId, String content,
                                            @ApiParam(value = "평점", required = true)@RequestParam float rating) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return responseService.getSingleResult(commentService.updateComment(commentId, user.getId(), content, rating));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @PreAuthorize("isAuthenticated() or hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "댓글 삭제", notes = "댓글을 삭제한다.")
    @DeleteMapping(value = "/{commentId}")
    public CommonResult deletePost(@PathVariable Long commentId, String content) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return responseService.getSingleResult(commentService.deleteComment(commentId, user.getId()));
    }

}
