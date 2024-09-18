package com.ordertogether.team14_be.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Spot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // ERD 설계서대로 수정 필요 (ManyToMany로 할 것인지, 양쪽에서 ManyToOne으로 중간 테이블을 볼 것인지)
    private Member member;

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

}
