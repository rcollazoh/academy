package cu.academy.email;


import com.sun.mail.util.MailConnectException;
import cu.academy.images.FilesStorageServiceImpl;
import cu.academy.person.PersonEntity;
import cu.academy.shared.exceptions.ArgumentException;
import cu.academy.trace.TraceService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Arrays;

@Service
public class EmailService {
    private final TraceService traceService;
    private final FilesStorageServiceImpl filesStorageService;
    private static final String className = "ViajeServiceImpl";
    JavaMailSender javaMailSender;

    public EmailService(TraceService trazaLogSistemaService, FilesStorageServiceImpl filesStorageService, JavaMailSender javaMailSender) {
        this.traceService = trazaLogSistemaService;
        this.filesStorageService = filesStorageService;
        this.javaMailSender = javaMailSender;
    }

    @Async
    public void sendMessage(String to, String subject, String content, String ccEmails) {
        String methodName = "sendMessaje";
        try {
            SimpleMailMessage email = new SimpleMailMessage();

            email.setTo(to);
            email.setSubject(subject);
            email.setText(content);
            // Add CC emails if provided
            if (ccEmails != null && !ccEmails.trim().isEmpty()) {
                // Split the CC emails by comma, trim whitespace, and filter out empty strings
                String[] ccArray = Arrays.stream(ccEmails.split(","))
                        .map(String::trim)
                        .filter(emailAddress -> !emailAddress.isEmpty())
                        .toArray(String[]::new);
                email.setCc(ccArray); // Set the CC recipients
            }

            javaMailSender.send(email);
        } catch (Exception e) {
            //traceService.insertLog(className, methodName, "Error al enviar correo a: " + to, e, 3);
        }
    }

    public void sendMenssajeAndAttachment(String to, String subject, String content, String filePath, String ccEmails) {
        String methodName = "sendMenssajeAndAttachment";
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content);

            // Add CC emails if provided
            if (ccEmails != null && !ccEmails.trim().isEmpty()) {
                // Split the CC emails by comma and trim whitespace
                String[] ccArray = ccEmails.split(",");
                for (String cc : ccArray) {
                    helper.addCc(cc.trim()); // Add each CC email
                }
            }

            // Adjuntar el archivo
            if (filePath != null) {
                File file = new File(filePath);
                helper.addAttachment(file.getName(), file);
            }

        } catch (MessagingException e) {
            // traceService.insertLog(className, methodName, "Error al enviar correo a: " + to, e, 3);
            throw new RuntimeException(e);
        }

        javaMailSender.send(message);
    }

    @Async
    public void sendMessageAndAttachmentWithFile(String to, String subject, String content, MultipartFile file, String nameFile) throws ArgumentException {
        String methodName = "sendMenssajeAndAttachment";
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content);

//            // Add CC emails if provided
//            if (ccEmails != null && !ccEmails.trim().isEmpty()) {
//                // Split the CC emails by comma and trim whitespace
//                String[] ccArray = ccEmails.split(",");
//                for (String cc : ccArray) {
//                    helper.addCc(cc.trim()); // Add each CC email
//                }
//            }

            // Adjuntar el archivo
            if (file != null) {
                MultipartFileInputStreamSource inputStreamSource = new MultipartFileInputStreamSource(file);
                helper.addAttachment(nameFile, inputStreamSource);

            }

            javaMailSender.send(message);

        } catch (MailSendException e) {
            // Manejar la excepción de envío de correo
            if (e.getCause() instanceof MailConnectException) {
                //   traceService.insertLog(className, methodName, "Error de conexión a Internet al enviar correo a: " + to, e, 3);
                throw new ArgumentException("No se pudo enviar el correo debido a mala conexión a Internet.");
            } else {
                //   traceService.insertLog(className, methodName, "Error al enviar correo a: " + to, e, 3);
                throw new ArgumentException("Lo sentimos, ocurrió un error al enviar el calendario.");
            }
        } catch (MailAuthenticationException e) {
            throw new ArgumentException("La contraseña del correo de la agencia a cambiado. Comuníquese con los desarrolladores.");
        } catch (Exception e) {
            //  traceService.insertLog(className, methodName, "Error al enviar correo a: " + to, e, 3);
            throw new RuntimeException(e);
        }
    }

    /**
     * Metodo para enviar correo ya sea con adjunto o sin el.
     *
     * @param person
     * @param message
     * @param subject
     * @param pathPdf
     */
    public void sendEmail(PersonEntity person, String message, String subject, String pathPdf) {
        String email = person.getEmail();
        if (email == null)
            return;
        if (pathPdf == null)
            sendMessage(email, subject, message, person.getEmail());
        else
            sendMenssajeAndAttachment(email, subject, message, pathPdf, person.getEmail());
    }

    public void sendEmailRecoverPassword(String to, String subject, String password, String ccEmails) {
        String methodName = "enviarCorreoRecuperarContrasenna";
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);

            // Crear el contenido HTML
            String htmlContent = getPlantillaHtmlRecuperarContrasenna(password);

            // Establecer el contenido HTML
            helper.setText(htmlContent, true);

            // Adjuntar la imagen
            File logoFile = new File(filesStorageService.getRutaLogoAgencia()); // Cambia esta ruta a donde esté tu imagen
            helper.addInline("logoImage", logoFile); // Esto adjunta la imagen con el Content-ID 'logoImage'

            // Agregar CC emails si se proporcionan
            if (ccEmails != null && !ccEmails.trim().isEmpty()) {
                String[] ccArray = Arrays.stream(ccEmails.split(","))
                        .map(String::trim)
                        .filter(emailAddress -> !emailAddress.isEmpty())
                        .toArray(String[]::new);
                helper.setCc(ccArray); // Establecer los destinatarios CC
            }

            // Enviar el correo
            javaMailSender.send(message);
        } catch (Exception e) {
            //   traceService.insertLog(className, methodName, "Error inesperado al enviar correo a: " + to, e, 3);
        }
    }

    private String getPlantillaHtmlRecuperarContrasenna(String contrasenna) {
        return "<!DOCTYPE html>" +
                "<html lang=\"es\">" +
                "<head>" +
                "    <meta charset=\"UTF-8\">" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "    <title>Recuperar Contraseña</title>" +
                "    <style>" +
                "        body {" +
                "            font-family: Arial, sans-serif;" +
                "            margin: 0;" +
                "            padding: 20px;" +
                "            background-color: #f4f4f4;" +
                "            color: #333;" +
                "        }" +
                "        .container {" +
                "            max-width: 600px;" +
                "            margin: 0 auto;" +
                "            background-color: #fff;" +
                "            padding: 20px;" +
                "            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);" +
                "        }" +
                "        .logo {" +
                "            text-align: center;" +
                "            margin-bottom: 10px;" +
                "        }" +
                "        .logo img {" +
                "            max-width: 100px;" +
                "        }" +
                "        .content {" +
                "            text-align: center;" +
                "        }" +
                "        .code {" +
                "            font-size: 24px;" +
                "            font-weight: bold;" +
                "            margin-top: 10px;" +
                "            color: #e74c3c;" +
                "        }" +
                "    </style>" +
                "</head>" +
                "<body>" +
                "    <div class=\"container\">" +
                "        <div class=\"logo\">" +
                "            <img src=\"cid:logoImage\" alt=\"Logo\">" +
                "        </div>" +
                "        <div class=\"content\">" +
                "            <p>Su código para recuperar su cuenta es:</p>" +
                "            <p class=\"code\">" + contrasenna + "</p>" +
                "        </div>" +
                "    </div>" +
                "</body>" +
                "</html>";
    }

}
