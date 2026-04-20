package com.accountManager.eventAudit;

import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.util.List;

public interface EventAuditInterface {

    EventAudit saveEventAudit(EventAudit eventAudit);

    EventAudit logEvent(Long userId, String entityType, Long entityId, String actionType, 
                       String oldValues, String newValues, String ipAddress, String userAgent);

    List<EventAudit> getEventsByUserId(Long userId, Authentication authentication);

    List<EventAudit> getEventsByEntity(String entityType, Long entityId, Authentication authentication);

    List<EventAudit> getEventsByActionType(String actionType, Authentication authentication);

    List<EventAudit> getEventsByUserIdAndDateRange(Long userId, LocalDateTime startDate, LocalDateTime endDate, Authentication authentication);

    List<EventAudit> getEventsByDateRange(LocalDateTime startDate, LocalDateTime endDate, Authentication authentication);

    List<EventAudit> getEventsByUserIdAndEntityTypeAndDateRange(Long userId, String entityType, LocalDateTime startDate, LocalDateTime endDate, Authentication authentication);

}
