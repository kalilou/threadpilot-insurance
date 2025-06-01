package com.threadpilot.insurance.repository;

import com.threadpilot.insurance.model.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    Optional<Promotion> findFirstByDescriptionOrderByValidityDesc(String description);
}