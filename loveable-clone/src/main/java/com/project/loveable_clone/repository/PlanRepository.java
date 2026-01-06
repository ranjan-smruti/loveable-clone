package com.project.loveable_clone.repository;

import com.project.loveable_clone.dto.subscription.PlanResponse;
import com.project.loveable_clone.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PlanRepository extends JpaRepository<Plan, Long> {
    @Query("""
        SELECT new com.project.loveable_clone.dto.subscription.PlanResponse(
            p.id,
            p.name,
            p.maxProjects,
            p.maxTokensPerDay,
            p.unlimitedAi,
            p.price
        )
        FROM Plan p
        WHERE p.active = true
        ORDER BY p.id
    """)
    List<PlanResponse> getAllActivePlans();
}
