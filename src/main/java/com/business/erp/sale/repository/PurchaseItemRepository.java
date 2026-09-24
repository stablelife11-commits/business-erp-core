package com.business.erp.sale.repository;

import com.business.erp.sale.model.PurchaseItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PurchaseItemRepository extends JpaRepository<PurchaseItem, Long> {
    List<PurchaseItem> findByPurchaseId(Long purchaseId);
}