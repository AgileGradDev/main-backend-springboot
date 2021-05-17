package com.agd.backend.api.service;

import com.agd.backend.api.advice.exception.CNotOwnerException;
import com.agd.backend.api.advice.exception.CResourceNotExistException;
import com.agd.backend.api.advice.exception.CUserNotFoundException;
import com.agd.backend.api.entity.Store;
import com.agd.backend.api.entity.User;
import com.agd.backend.api.repository.CommentJpaRepository;
import com.agd.backend.api.repository.StoreRepository;
import com.agd.backend.api.repository.UserJpaRepository;
import com.agd.backend.api.entity.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentJpaRepository commentJpaRepo;
    private final UserJpaRepository userJpaRepo;
    private final StoreRepository storeJpaRepo;

    public Store findStore(String storeName) {
        return Optional.ofNullable(storeJpaRepo.findByName(storeName)).orElseThrow(CResourceNotExistException::new);
    }

    public List<Comment> findComments(String storeName) {
        return commentJpaRepo.findByStore(findStore(storeName));
    }

    public List<Comment> findCommentsUser(User user) {
        return commentJpaRepo.findByUser(user);
    }

    public Comment getComment(Long commentId) {
        return commentJpaRepo.findById(commentId).orElseThrow(CResourceNotExistException::new);
    }

    public Comment writeComment(Long uid, String storeName, String content, float rating) {
        Store store = findStore(storeName);
        Comment comment = new Comment(userJpaRepo.findById(uid).orElseThrow(CUserNotFoundException::new), store, content, rating);
        return commentJpaRepo.save(comment);
    }

    public Comment updateComment(Long commentId, Long uid, String content, float rating) {
        Comment comment = getComment(commentId);
        Long userId = comment.getUser().getId();
        if (!uid.equals(userId))
            throw new CNotOwnerException();
        comment.setUpdate(content, rating);
        return commentJpaRepo.save(comment);
    }

    public boolean deleteComment(Long commentId, Long uid) {
        Comment comment = getComment(commentId);
        Long userId = comment.getUser().getId();
        if (!uid.equals(userId))
            throw new CNotOwnerException();
        commentJpaRepo.delete(comment);
        return true;
    }
}
