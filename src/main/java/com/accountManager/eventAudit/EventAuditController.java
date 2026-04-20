package com.accountManager.eventAudit;

import com.accountManager.common.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/event-audit")
@Slf4j
public class EventAuditController {

    private final EventAuditInterface eventAuditService;

    public EventAuditController(EventAuditInterface eventAuditService) {
        this.eventAuditService = eventAuditService;
    }

    @PostMapping
    public ApiResponse<EventAudit> createEventAudit(@RequestBody EventAudit eventAudit, Authentication authentication) {
        try {
            EventAudit savedEventAudit = eventAuditService.saveEventAudit(eventAudit);
            return ApiResponse.success(savedEventAudit, "Event audit created successfully");
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/{userId}")
    public ApiResponse<List<EventAudit>> getEventsByUserId(@PathVariable Long userId, Authentication authentication) {
        try {
            List<EventAudit> events = eventAuditService.getEventsByUserId(userId, authentication);
            return ApiResponse.success(events, "Events retrieved successfully by user ID");
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/entity/{entityType}/{entityId}")
    public ApiResponse<List<EventAudit>> getEventsByEntity(@PathVariable String entityType, @PathVariable Long entityId, Authentication authentication) {
        try {
            List<EventAudit> events = eventAuditService.getEventsByEntity(entityType, entityId, authentication);
            return ApiResponse.success(events, "Events retrieved successfully by entity");
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/action/{actionType}")
    public ApiResponse<List<EventAudit>> getEventsByActionType(@PathVariable String actionType, Authentication authentication) {
        try {
            List<EventAudit> events = eventAuditService.getEventsByActionType(actionType, authentication);
            return ApiResponse.success(events, "Events retrieved successfully by action type");
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/{userId}/date-range")
    public ApiResponse<List<EventAudit>> getEventsByUserIdAndDateRange(
            @PathVariable Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            Authentication authentication) {
        try {
            List<EventAudit> events = eventAuditService.getEventsByUserIdAndDateRange(userId, startDate, endDate, authentication);
            return ApiResponse.success(events, "Events retrieved successfully by user ID and date range");
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/date-range")
    public ApiResponse<List<EventAudit>> getEventsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            Authentication authentication) {
        try {
            List<EventAudit> events = eventAuditService.getEventsByDateRange(startDate, endDate, authentication);
            return ApiResponse.success(events, "Events retrieved successfully by date range");
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/{userId}/entity/{entityType}/date-range")
    public ApiResponse<List<EventAudit>> getEventsByUserIdAndEntityTypeAndDateRange(
            @PathVariable Long userId,
            @PathVariable String entityType,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            Authentication authentication) {
        try {
            List<EventAudit> events = eventAuditService.getEventsByUserIdAndEntityTypeAndDateRange(userId, entityType, startDate, endDate, authentication);
            return ApiResponse.success(events, "Events retrieved successfully by user ID, entity type and date range");
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
