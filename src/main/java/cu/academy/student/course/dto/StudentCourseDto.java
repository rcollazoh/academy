package cu.academy.student.course.dto;

import cu.academy.config.course.dto.ConfigCourseDto;

import java.time.LocalDate;

public record StudentCourseDto(Long id,
                               Long personId,
                               String personName,             // 👈 ejemplo: nombre de la persona
                               String personLastName,             // 👈 ejemplo: nombre de la persona
                               String personEmail,
                               ConfigCourseDto course,
                               LocalDate startDate,
                               LocalDate endDate,
                               String status,
                               Boolean requiresInvoice,
                               String receiptUrl,
                               String paymentMethod,
                               String certifyUrl
) {}