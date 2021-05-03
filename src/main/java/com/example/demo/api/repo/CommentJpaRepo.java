package com.example.demo.api.repo;

import com.example.demo.api.entity.Comment;
import com.example.demo.api.entity.Store;
import com.example.demo.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentJpaRepo extends JpaRepository<Comment, Long> {
    List<Comment> findByStore(Store store);



    List<Comment> findByUser(User user);
}
