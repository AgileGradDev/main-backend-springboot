package com.agd.backend.api.repository;

import com.agd.backend.api.entity.Store;
import com.agd.backend.api.entity.User;
import com.agd.backend.api.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentJpaRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByStore(Store store);


    List<Comment> findByUser(User user);
}
