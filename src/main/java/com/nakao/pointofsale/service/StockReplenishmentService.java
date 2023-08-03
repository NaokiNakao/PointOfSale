package com.nakao.pointofsale.service;

import com.nakao.pointofsale.dao.StockReplenishmentDAO;
import com.nakao.pointofsale.enumeration.StockReplenishmentStatus;
import com.nakao.pointofsale.exception.BusinessLogicException;
import com.nakao.pointofsale.exception.DeletionException;
import com.nakao.pointofsale.exception.NotFoundException;
import com.nakao.pointofsale.model.StockReplenishment;
import com.nakao.pointofsale.repository.ProductRepository;
import com.nakao.pointofsale.repository.StockReplenishmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class StockReplenishmentService {

    private final StockReplenishmentRepository stockReplenishmentRepository;
    private final StockReplenishmentDAO stockReplenishmentDAO;
    private final ProductRepository productRepository;

    public List<StockReplenishment> getStockReplenishments(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<StockReplenishment> page = stockReplenishmentRepository.findAll(pageable);
        return page.getContent();
    }

    public StockReplenishment getStockReplenishmentById(String id) {
        return stockReplenishmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Stock Replenishment not found with ID: " + id));
    }

    public String createStockReplenishment(StockReplenishment stockReplenishment) {
        stockReplenishment.setId(UUID.randomUUID().toString());
        stockReplenishmentDAO.insert(stockReplenishment);
        return stockReplenishment.getId();
    }

    public void updateStockReplenishment(String id, StockReplenishment stockReplenishment) {
        StockReplenishment updatedStockReplenishment = getStockReplenishmentById(id);
        BeanUtils.copyProperties(stockReplenishment, updatedStockReplenishment);
        updatedStockReplenishment.setId(id);
        stockReplenishmentRepository.save(updatedStockReplenishment);
    }

    public void deleteStockReplenishment(String id) {
        if (validateStockReplenishmentStatus(getStockReplenishmentById(id))) {
            stockReplenishmentRepository.deleteById(id);
        }
        else {
            throw new DeletionException("Unable to delete Stock Replenishment with ID: " + id);
        }
    }

    public void replenishmentProcessing(String id) {
        StockReplenishment stockReplenishment = getStockReplenishmentById(id);

        if (validateStockReplenishmentStatus(stockReplenishment)) {
            productRepository.updateStock(stockReplenishment.getProductSku(),
                    stockReplenishment.getProductQuantity());
            stockReplenishmentRepository.updateStatus(stockReplenishment.getId(),
                    StockReplenishmentStatus.DELIVERED.getValue());
        }
        else {
            throw new BusinessLogicException("Unable to process Stock Replenishment with ID: " + id);
        }
    }

    private Boolean validateStockReplenishmentStatus(StockReplenishment stockReplenishment) {
        return stockReplenishment.getStatus().equals(StockReplenishmentStatus.PENDING.getValue());
    }

}
