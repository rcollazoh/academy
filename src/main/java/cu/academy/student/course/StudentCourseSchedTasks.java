package cu.academy.student.course;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StudentCourseSchedTasks {


    private final StudentCourseService studentCourseService;

    public StudentCourseSchedTasks(StudentCourseService studentCourseService) {
        this.studentCourseService = studentCourseService;
    }

    @Scheduled(cron = "0 0 1 * * *") // Ejecuta todos los días a las 2:00 AM
    public void checkAndUpdateExpiredCourses() {
        studentCourseService.findExpiredActivatedCourses();
    }

    @Scheduled(cron = "0 0 1 * * *") // Ejecuta todos los días a las 2:00 AM
    public void sendEmailByApplyCourses() {
        studentCourseService.findExpiredActivatedCourses();
    }
}
