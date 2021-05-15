package com.agd.backend.api.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "visit_log")
@EqualsAndHashCode(of = "id")
public class VisitLog {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-char")
    @Column(length = 36)
    @JsonIgnore
    private UUID id;

    @JsonIgnore
    @CreationTimestamp
    private LocalDateTime creationDateTime;

    @JsonIgnore
    @UpdateTimestamp
    private LocalDateTime updateDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", referencedColumnName = "id")
    private Store store;

    @Column(nullable = false)
    private float rating;

    @Column(nullable = false, length = 500)
    private String text;

//    public VisitLog(User user, Store store, float rating, String text) {
//        this.user = user;
//        this.store = store;
//        this.rating = rating;
//        this.text = text;
//    }

    public VisitLog(Store store, float rating, String text) {
        this.store = store;
        this.rating = rating;
        this.text = text;
    }
}
