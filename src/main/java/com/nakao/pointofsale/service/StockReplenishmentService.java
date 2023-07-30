package com.nakao.pos.service;

import com.nakao.pos.dao.StockReplenishmentDAO;
import com.nakao.pos.exception.DeletionException;
import com.nakao.pos.exception.NotFoundException;
import com.nakao.pos.enumeration.StockReplenishmentStatus;
import com.nakao.pos.exception.StockReplenishmentProcessingException;
import com.nakao.pos.model.StockReplenishment;
import com.nakao.pos.repository.ProductRepository;
import com.nakao.pos.repository.StockReplenishmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * @author Naoki Nakao on 7/19/2023
 * @project POS
 */

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

    public void createStockReplenishment(StockReplenishment stockReplenishment) {
        stockReplenishment.setId(UUID.randomUUID().toString());
        stockReplenishmentDAO.insert(stockReplenishment);
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
            throw new StockReplenishmentProcessingException("Unable to process Stock Replenishment");
        }
    }

    private Boolean validateStockReplenishmentStatus(StockReplenishment stockReplenishment) {
        return stockReplenishment.getStatus().equals(StockReplenishmentStatus.PENDING.getValue());
    }

}
