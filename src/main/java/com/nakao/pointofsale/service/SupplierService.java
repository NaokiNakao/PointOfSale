package com.nakao.pos.service;

import com.nakao.pos.exception.DeletionException;
import com.nakao.pos.exception.NotFoundException;
import com.nakao.pos.model.Supplier;
import com.nakao.pos.repository.StockReplenishmentRepository;
import com.nakao.pos.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Naoki Nakao on 7/19/2023
 * @project POS
 */

@Service
@Transactional
@RequiredArgsConstructor
public class SupplierService {

    private final SupplierRepository supplierRepository;
    private final StockReplenishmentRepository stockReplenishmentRepository;

    public List<Supplier> getSuppliers(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Supplier> page = supplierRepository.findAll(pageable);
        return page.getContent();
    }

    public Supplier getSupplierById(Long id) {
        return supplierRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Supplier not found with ID: " + id));
    }

    public void createSupplier(Supplier supplier) {
        supplierRepository.save(supplier);
    }

    public void updateSupplier(Long id, Supplier supplier) {
        Supplier updatedSupplier = getSupplierById(id);
        BeanUtils.copyProperties(supplier, updatedSupplier);
        updatedSupplier.setId(id);
        supplierRepository.save(updatedSupplier);
    }

    public void deleteSupplier(Long id) {
        if (supplierRepository.existsById(id)) {
            if (isValidSupplierDeletion(id)) {
                supplierRepository.deleteById(id);
            } else {
                throw new DeletionException("Unable to delete Supplier with ID: " + id);
            }
        }
        else {
            throw new  NotFoundException("Supplier not found with ID: " + id);
        }
    }

    private Boolean isValidSupplierDeletion(Long id) {
        return stockReplenishmentRepository.countBySupplierId(id) == 0;
    }

}

