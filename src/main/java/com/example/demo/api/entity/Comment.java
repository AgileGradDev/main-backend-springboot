package com.example.demo.api.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
    @Column(nullable = false, length = 500)
    private String content;
    @Column(nullable = false)
    private float rating;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime modifiedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rid")
    private Store store;

    public Comment(User user, Store store, String content, float rating){
        this.user = user;
        this.store = store;
        this.content = content;
        this.rating = rating;
    }

    public Comment setUpdate(String content, float rating){
        this.content = content;
        this.rating = rating;
        return this;
    }

}
