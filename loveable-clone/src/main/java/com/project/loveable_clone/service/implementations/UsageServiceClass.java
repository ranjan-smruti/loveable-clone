package com.project.loveable_clone.service.implementations;

import com.project.loveable_clone.dto.subscription.PlanLimitsResponse;
import com.project.loveable_clone.dto.subscription.UsageTodayResponse;
import com.project.loveable_clone.service.UsageService;
import org.springframework.stereotype.Service;

@Service
public class UsageServiceClass implements UsageService {
    @Override
    public UsageTodayResponse getTodayUsageOfUser(Long userId) {
        return null;
    }

    @Override
    public PlanLimitsResponse getCurrentSubscriptionLimitsOfUser(Long userId) {
        return null;
    }
}
