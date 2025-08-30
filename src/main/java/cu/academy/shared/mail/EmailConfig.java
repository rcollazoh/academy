package cu.academy.shared.mail;

import cu.academy.config.parameter.ConfigParameterService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class EmailConfig {

    private final ConfigParameterService configParameterService;

    public EmailConfig(ConfigParameterService configParameterService) {
        this.configParameterService = configParameterService;
    }

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername(getUserNameEmail());
        mailSender.setPassword(getPasswordEmail());

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.transport.protocol", "smtp");

        return mailSender;
    }

    private String getUserNameEmail() {
        return configParameterService.getBy("USUARIO_CORREO_EMISOR").getValue();
    }

    private String getPasswordEmail() {
        return configParameterService.getBy("CLAVE_APP_CORREO_EMISOR").getValue();
    }

}
