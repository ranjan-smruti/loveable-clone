package com.project.loveable_clone.service;

import com.project.loveable_clone.dto.subscription.PlanResponse;
import com.project.loveable_clone.service.interfaces.PlanService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanServiceClass implements PlanService {
    @Override
    public List<PlanResponse> getAllActivePlans() {
        return List.of();
    }
}
