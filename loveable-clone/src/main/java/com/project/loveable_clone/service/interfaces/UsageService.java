package com.project.loveable_clone.service.interfaces;

import com.project.loveable_clone.dto.subscription.PlanLimitsResponse;
import com.project.loveable_clone.dto.subscription.UsageTodayResponse;

public interface UsageService {
    void recordTokenUsage(Long userId, Integer actualTokens);
    void checkDailyTokenUsage();
}
