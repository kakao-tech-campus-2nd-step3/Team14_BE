package com.ordertogether.team14_be.spot.entity;

import com.ordertogether.team14_be.payment.domain.BaseEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder // 상속받은 필드도 빌더에서 사용
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(indexes = {@Index(name = "idx_lat_lng", columnList = "lat, lng")})
public class Spot extends BaseEntity {

    @Column(precision = 10, scale = 8)
    private BigDecimal lat;

    @Column(precision = 11, scale = 8)
    private BigDecimal lng;

    private String category;
    private String store_name;
    private Integer minimum_order_amount;

    @Lob
    @Column(columnDefinition = "MEDIUMTEXT")
    private String together_order_link;

    private String pick_up_location;
    private String delivery_status;
    @Builder.Default
    private Boolean isDeleted = false;

    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}
