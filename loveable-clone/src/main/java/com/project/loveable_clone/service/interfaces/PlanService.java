package com.project.loveable_clone.service.interfaces;

import com.project.loveable_clone.dto.subscription.PlanResponse;

import java.util.List;

public interface PlanService {
    List<PlanResponse> getAllActivePlans();
}
