package com.accountManager.eventAudit;

import com.accountManager.auth.AuthorizationUtils;
import com.accountManager.auth.refreshToken.CustomeUserDetails;
import com.accountManager.exception.ExceptionMassage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventAuditService implements EventAuditInterface {

    @Autowired
    private EventAuditRepository eventAuditRepository;

    @Override
    public EventAudit saveEventAudit(EventAudit eventAudit) {
        if (eventAudit == null) {
            throw new RuntimeException(ExceptionMassage.EVENT_AUDIT_CANNOT_BE_NULL);
        }
        return eventAuditRepository.save(eventAudit);
    }

    @Override
    public EventAudit logEvent(Long userId, String entityType, Long entityId, String actionType, 
                              String oldValues, String newValues, String ipAddress, String userAgent) {
        EventAudit eventAudit = new EventAudit();
        eventAudit.setUserId(userId);
        eventAudit.setEntityType(entityType);
        eventAudit.setEntityId(entityId);
        eventAudit.setActionType(actionType);
        eventAudit.setOldValues(oldValues);
        eventAudit.setNewValues(newValues);
        eventAudit.setIpAddress(ipAddress);
        eventAudit.setUserAgent(userAgent);
        
        return eventAuditRepository.save(eventAudit);
    }

    @Override
    public List<EventAudit> getEventsByUserId(Long userId, Authentication authentication) {
        CustomeUserDetails user = (CustomeUserDetails) authentication.getPrincipal();
        if (user == null) {
            throw new RuntimeException(ExceptionMassage.INVALID_LOGIN);
        }
        
        AuthorizationUtils.validateUserAccess(authentication, userId);
        return eventAuditRepository.findByUserId(userId);
    }

    @Override
    public List<EventAudit> getEventsByEntity(String entityType, Long entityId, Authentication authentication) {
        CustomeUserDetails user = (CustomeUserDetails) authentication.getPrincipal();
        if (user == null) {
            throw new RuntimeException(ExceptionMassage.INVALID_LOGIN);
        }
        
        return eventAuditRepository.findByEntityTypeAndEntityId(entityType, entityId);
    }

    @Override
    public List<EventAudit> getEventsByActionType(String actionType, Authentication authentication) {
        CustomeUserDetails user = (CustomeUserDetails) authentication.getPrincipal();
        if (user == null) {
            throw new RuntimeException(ExceptionMassage.INVALID_LOGIN);
        }
        
        return eventAuditRepository.findByActionType(actionType);
    }

    @Override
    public List<EventAudit> getEventsByUserIdAndDateRange(Long userId, LocalDateTime startDate, LocalDateTime endDate, Authentication authentication) {
        CustomeUserDetails user = (CustomeUserDetails) authentication.getPrincipal();
        if (user == null) {
            throw new RuntimeException(ExceptionMassage.INVALID_LOGIN);
        }
        
        AuthorizationUtils.validateUserAccess(authentication, userId);
        return eventAuditRepository.findByUserIdAndCreatedAtBetween(userId, startDate, endDate);
    }

    @Override
    public List<EventAudit> getEventsByDateRange(LocalDateTime startDate, LocalDateTime endDate, Authentication authentication) {
        CustomeUserDetails user = (CustomeUserDetails) authentication.getPrincipal();
        if (user == null) {
            throw new RuntimeException(ExceptionMassage.INVALID_LOGIN);
        }
        
        return eventAuditRepository.findByCreatedAtBetweenOrderByCreatedAtDesc(startDate, endDate);
    }

    @Override
    public List<EventAudit> getEventsByUserIdAndEntityTypeAndDateRange(Long userId, String entityType, LocalDateTime startDate, LocalDateTime endDate, Authentication authentication) {
        CustomeUserDetails user = (CustomeUserDetails) authentication.getPrincipal();
        if (user == null) {
            throw new RuntimeException(ExceptionMassage.INVALID_LOGIN);
        }
        
        AuthorizationUtils.validateUserAccess(authentication, userId);
        return eventAuditRepository.findByUserIdAndEntityTypeAndCreatedAtBetween(userId, entityType, startDate, endDate);
    }

}
