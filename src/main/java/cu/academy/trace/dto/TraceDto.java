package cu.academy.trace.dto;

import java.time.Instant;

public record TraceDto(
        Long id,
        Long actionId,
        Long personId,
        String fullName,
        String mobilePhone,
        String context,
        String details,
        Long studentCourseId,
        Long applicationId,
        String studentCourseCode,
        Instant dateCreated,
        String ipAddress
) {}
