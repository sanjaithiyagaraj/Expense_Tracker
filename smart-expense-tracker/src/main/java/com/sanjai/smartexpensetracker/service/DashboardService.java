package com.sanjai.smartexpensetracker.service;

import com.sanjai.smartexpensetracker.dto.DashboardDto;

public interface DashboardService {

    DashboardDto getDashboard(String email);
}
