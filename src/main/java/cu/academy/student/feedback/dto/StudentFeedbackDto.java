package cu.academy.student.feedback.dto;


import cu.academy.student.feedback.module.dto.StudentFeedbackModuleDto;

import java.util.List;

public record StudentFeedbackDto(Long id,
                                 Long StudentModuleId,
                                 String learningQuestion,
                                 String durationQuestion,
                                 String satisfactionQuestion,
                                 String errorObs,
                                 String considerationObs,
                                 List<StudentFeedbackModuleDto> modules

) {
}