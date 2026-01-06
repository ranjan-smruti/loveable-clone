package com.project.loveable_clone.repository;

import com.project.loveable_clone.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface PlanRepository extends JpaRepository<Plan, Long> {
}
