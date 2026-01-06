package com.project.loveable_clone.dto.subscription;

import java.math.BigDecimal;

public record PlanResponse(Long id,
                           String name,
                           Integer maxProjects,
                           Integer maxTokensPerDay,
                           Boolean unlimitedAi,
                           BigDecimal price) {
}
