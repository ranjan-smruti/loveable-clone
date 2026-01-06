package com.project.loveable_clone.service;

import com.project.loveable_clone.dto.subscription.PlanResponse;
import com.project.loveable_clone.repository.PlanRepository;
import com.project.loveable_clone.service.interfaces.PlanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PlanServiceClass implements PlanService {
    private final PlanRepository planRepository;
    @Override
    public List<PlanResponse> getAllActivePlans() {
        return planRepository.getAllActivePlans();
    }
}
