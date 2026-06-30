package com.distributed_project_companion.workspace_service.client;


import com.distributed_project_companion.common_lib.dto.PlanDto;
import com.distributed_project_companion.common_lib.dto.UserDto;
import com.distributed_project_companion.workspace_service.config.FeignAuthConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@FeignClient(name = "account-service", path = "/account",configuration = FeignAuthConfig.class, url="${ACCOUNT_SERVICE_URI:}")
public interface AccountClient {

    @GetMapping("/internal/v1/users/by-email")
    Optional<UserDto> getUserByEmail(@RequestParam("email") String email);

    @GetMapping("/internal/v1/billing/current-plan/{userId}")
    PlanDto getCurrentSubscribedPlanByUser(@PathVariable("userId") Long userId);
}
