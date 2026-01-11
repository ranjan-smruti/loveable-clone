package com.project.loveable_clone.mappers;

import com.project.loveable_clone.dto.subscription.PlanResponse;
import com.project.loveable_clone.dto.subscription.SubscriptionResponse;
import com.project.loveable_clone.entity.Plan;
import com.project.loveable_clone.entity.Subscription;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {
    SubscriptionResponse toSubscriptionResponse(Subscription subscription);
    PlanResponse toPlanResponse(Plan plan);
}
