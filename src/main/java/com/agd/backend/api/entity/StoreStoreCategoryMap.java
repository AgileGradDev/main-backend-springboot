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
@Table(name = "store_store_category_map")
public class StoreStoreCategoryMap {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-char")
    @Column(length = 36)
    @JsonIgnore
    private UUID id;

    @CreationTimestamp
    private LocalDateTime creationDateTime;

    @UpdateTimestamp
    private LocalDateTime updateDateTime;

    @Type(type = "uuid-char")
    @Column(length = 36, nullable = false)
    private UUID storeId;

    @Column(nullable = false)
    private Long storeCategoryId;

    public StoreStoreCategoryMap(UUID storeId, Long storeCategoryId) {
        this.storeId = storeId;
        this.storeCategoryId = storeCategoryId;
    }
}
