package com.distributed_project_companion.account_service.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.distributed_project_companion.account_service.entity.Plan;

import java.util.Optional;

public interface PlanRepository extends JpaRepository<Plan, Long> {
    Optional<Plan> findByStripePriceId(String id);
}
