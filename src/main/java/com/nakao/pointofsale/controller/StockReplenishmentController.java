package com.nakao.pointofsale.controller;

import com.nakao.pointofsale.model.StockReplenishment;
import com.nakao.pointofsale.service.StockReplenishmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stock-replenishments")
@RequiredArgsConstructor
public class StockReplenishmentController {

    private final StockReplenishmentService stockReplenishmentService;

    @GetMapping
    public ResponseEntity<List<StockReplenishment>> getStockReplenishments(@RequestParam(defaultValue = "0") Integer page,
                                                           @RequestParam(defaultValue = "10") Integer size) {
        List<StockReplenishment> stockReplenishments = stockReplenishmentService.getStockReplenishments(page, size);
        return new ResponseEntity<>(stockReplenishments, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockReplenishment> getStockReplenishmentById(@PathVariable String id) {
        StockReplenishment stockReplenishment = stockReplenishmentService.getStockReplenishmentById(id);
        return new ResponseEntity<>(stockReplenishment, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createStockReplenishment(@RequestBody @Valid StockReplenishment stockReplenishment) {
        String stockReplenishmentId = stockReplenishmentService.createStockReplenishment(stockReplenishment);
        return new ResponseEntity<>("Stock Replenishment created: " + stockReplenishmentId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateStockReplenishment(@PathVariable String id,
                                                           @RequestBody @Valid StockReplenishment stockReplenishment) {
        stockReplenishmentService.updateStockReplenishment(id, stockReplenishment);
        return new ResponseEntity<>("Stock Replenishment updated", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStockReplenishment(@PathVariable String id) {
        stockReplenishmentService.deleteStockReplenishment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}/process")
    public ResponseEntity<String> replenishmentProcessing(@PathVariable String id) {
        stockReplenishmentService.replenishmentProcessing(id);
        return new ResponseEntity<>("Stock Replenishment processed", HttpStatus.OK);
    }

}
