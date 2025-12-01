package com.project.loveable_clone.dto.subscription;

public record PlanLimitsResponse(String planName,
                                 Integer maxTokensPerDay,
                                 Integer maxProjects,
                                 Boolean unlimitedAi) {
}
