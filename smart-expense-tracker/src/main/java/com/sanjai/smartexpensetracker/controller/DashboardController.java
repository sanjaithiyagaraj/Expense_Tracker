package com.sanjai.smartexpensetracker.controller;

import com.sanjai.smartexpensetracker.dto.ApiResponse;
import com.sanjai.smartexpensetracker.dto.DashboardDto;
import com.sanjai.smartexpensetracker.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@Tag(name = "Dashboard", description = "Dashboard & Reports APIs")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @Operation(summary = "Get dashboard summary for current user")
    @GetMapping
    public ResponseEntity<ApiResponse<DashboardDto>> getDashboard(
            Authentication authentication) {
        DashboardDto dashboard = dashboardService.getDashboard(authentication.getName());
        return ResponseEntity.ok(
                ApiResponse.success("Dashboard retrieved successfully", dashboard));
    }
}
