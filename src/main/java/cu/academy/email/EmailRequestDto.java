package cu.academy.email;

public record EmailRequestDto(
        String recipientEmail,
        String subject,
        String content,
        String attachmentPath
) {}
