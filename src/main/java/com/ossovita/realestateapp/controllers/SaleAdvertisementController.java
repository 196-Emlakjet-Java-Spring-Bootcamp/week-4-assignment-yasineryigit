package com.ossovita.realestateapp.controllers;

import com.ossovita.realestateapp.business.abstracts.SaleAdvertisementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/1.0")
public class SaleAdvertisementController {

    SaleAdvertisementService saleAdvertisementService;

    public SaleAdvertisementController(SaleAdvertisementService saleAdvertisementService) {
        this.saleAdvertisementService = saleAdvertisementService;
    }

    @GetMapping("/sale-advertisement/create-dummy-sale-advertisement-request")
    public ResponseEntity<String> createDummySaleAdvertisementRequest(@RequestParam int piece){
        saleAdvertisementService.createDummySaleAdvertisementRequest(piece);
        return ResponseEntity.ok("Your request has been added to queue");
    }
}
