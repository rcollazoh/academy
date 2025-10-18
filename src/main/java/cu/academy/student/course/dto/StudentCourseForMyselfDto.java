package cu.academy.student.course.dto;

import java.time.LocalDate;

public record StudentCourseForMyselfDto(Long id,
                                        Long personId,
                                        String configCourseName,
                                        LocalDate startDate,
                                        LocalDate endDate,
                                        String status,
                                        Boolean requiresInvoice,
                                        String receiptUrl,
                                        String paymentMethod,
                                        String certifyUrl
) {}