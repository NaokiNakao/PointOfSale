package com.nakao.pointofsale.service;

import com.nakao.pointofsale.repository.StockReplenishmentRepository;
import com.nakao.pointofsale.exception.DeletionException;
import com.nakao.pointofsale.exception.NotFoundException;
import com.nakao.pointofsale.model.Supplier;
import com.nakao.pointofsale.repository.SupplierRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SupplierService {
    private final SupplierRepository supplierRepository;
    private final StockReplenishmentRepository stockReplenishmentRepository;

    public SupplierService(SupplierRepository supplierRepository, StockReplenishmentRepository stockReplenishmentRepository) {
        this.supplierRepository = supplierRepository;
        this.stockReplenishmentRepository = stockReplenishmentRepository;
    }

    public List<Supplier> getSuppliers(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Supplier> page = supplierRepository.findAll(pageable);
        return page.getContent();
    }

    public Supplier getSupplierById(Long id) {
        return supplierRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Supplier not found with ID: " + id));
    }

    public Long createSupplier(Supplier supplier) {
        return supplierRepository.save(supplier).getId();
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

