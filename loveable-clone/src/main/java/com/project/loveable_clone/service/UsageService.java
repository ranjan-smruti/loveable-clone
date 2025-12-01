package com.project.loveable_clone.service;

import com.project.loveable_clone.dto.subscription.PlanLimitsResponse;
import com.project.loveable_clone.dto.subscription.UsageTodayResponse;
import org.jspecify.annotations.Nullable;

public interface UsageService {
    UsageTodayResponse getTodayUsageOfUser(Long userId);

    PlanLimitsResponse getCurrentSubscriptionLimitsOfUser(Long userId);
}
