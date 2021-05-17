package com.agd.backend.api.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StoreImage {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-char")
    @Column(length = 36)
    private UUID id;

    @Type(type = "uuid-char")
    @Column(length = 36)
    private UUID storeId;

    @Column(length = 2000)
    private String url;

    public StoreImage(UUID storeId, String url) {
        this.storeId = storeId;
        this.url = url;
    }
}
