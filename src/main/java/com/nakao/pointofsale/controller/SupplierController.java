package com.nakao.pos.controller;

import com.nakao.pos.model.Supplier;
import com.nakao.pos.service.SupplierService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Naoki Nakao on 7/19/2023
 * @project POS
 */

@RestController
@RequestMapping("/api/v1/suppliers")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierService supplierService;

    @GetMapping
    public ResponseEntity<List<Supplier>> getSuppliers(@RequestParam(defaultValue = "0") Integer page,
                                                 @RequestParam(defaultValue = "10") Integer size) {
        List<Supplier> suppliers = supplierService.getSuppliers(page, size);
        return new ResponseEntity<>(suppliers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Supplier> getSupplierById(@PathVariable Long id) {
        Supplier supplier = supplierService.getSupplierById(id);
        return new ResponseEntity<>(supplier, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String > createSupplier(@RequestBody @Valid Supplier supplier) {
        supplierService.createSupplier(supplier);
        return new ResponseEntity<>("Supplier created", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String > updateSupplier(@PathVariable Long id, @RequestBody @Valid Supplier supplier) {
        supplierService.updateSupplier(id, supplier);
        return new ResponseEntity<>("Supplier updated", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable Long id) {
        supplierService.deleteSupplier(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

