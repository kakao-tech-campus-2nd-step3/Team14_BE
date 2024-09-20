package com.ordertogether.team14_be.spot.controller;

import com.ordertogether.team14_be.spot.dto.SpotDto;
import com.ordertogether.team14_be.spot.service.SpotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class SpotController {

    private final SpotService spotService;

    @Autowired
    public SpotController(SpotService spotService) {
        this.spotService = spotService;
    }

    //Spot 전체 조회하기
    @GetMapping("/api/v1/spot/{lat}/{lng}")
    public ResponseEntity<List<SpotDto>> getSpot(@PathVariable BigDecimal lat, @PathVariable BigDecimal lng) {
        return ResponseEntity.ok(spotService.getSpot(lat, lng));
    }

    //Spot 생성하기
    @PostMapping("/api/v1/spot")
    public ResponseEntity<SpotDto> createSpot(@RequestBody SpotDto spotDto) {
        return ResponseEntity.ok(spotService.createSpot(spotDto));
    }

    //Spot 상세 조회하기
    @GetMapping("/api/v1/spot/{id}")
    public ResponseEntity<SpotDto> getSpot(@PathVariable Long id) {
        return ResponseEntity.ok(spotService.getSpot(id));
    }

    //Spot 수정하기
    @PutMapping("/api/v1/spot")
    public ResponseEntity<SpotDto> updateSpot(@RequestBody SpotDto spotDto) {
        return ResponseEntity.ok(spotService.updateSpot(spotDto));
    }

    //Spot 삭제하기
    @DeleteMapping("/api/v1/spot/{id}")
    public ResponseEntity<Void> deleteSpot(@PathVariable Long id) {
        spotService.deleteSpot(id);
        return ResponseEntity.ok().build();
    }

}
