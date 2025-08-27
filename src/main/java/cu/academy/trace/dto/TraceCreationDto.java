package cu.academy.trace.dto;

public record TraceCreationDto(
        Long actionId,
        Long personId,
        String fullName,
        String mobilePhone,
        String context,
        String details,
        Long studentCourseId,
        Long applicationId,
        String studentCourseCode,
        String ipAddress
) {
    public TraceCreationDto withActionId(Long actionId) {
        return new TraceCreationDto(
                actionId, personId, fullName, mobilePhone, context, details,
                studentCourseId, applicationId, studentCourseCode, ipAddress
        );
    }

    public TraceCreationDto withPersonId(Long personId) {
        return new TraceCreationDto(
                actionId, personId, fullName, mobilePhone, context, details,
                studentCourseId, applicationId, studentCourseCode, ipAddress
        );
    }

    public TraceCreationDto withFullName(String fullName) {
        return new TraceCreationDto(
                actionId, personId, fullName, mobilePhone, context, details,
                studentCourseId, applicationId, studentCourseCode, ipAddress
        );
    }

    public TraceCreationDto withMobilePhone(String mobilePhone) {
        return new TraceCreationDto(
                actionId, personId, fullName, mobilePhone, context, details,
                studentCourseId, applicationId, studentCourseCode, ipAddress
        );
    }

    public TraceCreationDto withContext(String context) {
        return new TraceCreationDto(
                actionId, personId, fullName, mobilePhone, context, details,
                studentCourseId, applicationId, studentCourseCode, ipAddress
        );
    }

    public TraceCreationDto withDetails(String details) {
        return new TraceCreationDto(
                actionId, personId, fullName, mobilePhone, context, details,
                studentCourseId, applicationId, studentCourseCode, ipAddress
        );
    }

    public TraceCreationDto withStudentCourseId(Long studentCourseId) {
        return new TraceCreationDto(
                actionId, personId, fullName, mobilePhone, context, details,
                studentCourseId, applicationId, studentCourseCode, ipAddress
        );
    }

    public TraceCreationDto withApplicationId(Long applicationId) {
        return new TraceCreationDto(
                actionId, personId, fullName, mobilePhone, context, details,
                studentCourseId, applicationId, studentCourseCode, ipAddress
        );
    }

    public TraceCreationDto withStudentCourseCode(String studentCourseCode) {
        return new TraceCreationDto(
                actionId, personId, fullName, mobilePhone, context, details,
                studentCourseId, applicationId, studentCourseCode, ipAddress
        );
    }

    public TraceCreationDto withIpAddress(String ipAddress) {
        return new TraceCreationDto(
                actionId, personId, fullName, mobilePhone, context, details,
                studentCourseId, applicationId, studentCourseCode, ipAddress
        );
    }
}