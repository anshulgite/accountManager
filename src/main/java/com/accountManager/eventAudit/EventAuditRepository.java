package com.accountManager.eventAudit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventAuditRepository extends JpaRepository<EventAudit, Long> {

    List<EventAudit> findByUserId(Long userId);

    List<EventAudit> findByEntityTypeAndEntityId(String entityType, Long entityId);

    List<EventAudit> findByActionType(String actionType);

    List<EventAudit> findByUserIdAndCreatedAtBetween(Long userId, LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT e FROM EventAudit e WHERE e.userId = :userId AND e.entityType = :entityType AND e.createdAt BETWEEN :startDate AND :endDate")
    List<EventAudit> findByUserIdAndEntityTypeAndCreatedAtBetween(
            @Param("userId") Long userId,
            @Param("entityType") String entityType,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    @Query("SELECT e FROM EventAudit e WHERE e.createdAt BETWEEN :startDate AND :endDate ORDER BY e.createdAt DESC")
    List<EventAudit> findByCreatedAtBetweenOrderByCreatedAtDesc(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

}
