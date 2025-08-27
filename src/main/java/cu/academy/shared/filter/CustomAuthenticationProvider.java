package cu.academy.shared.filter;

import cu.academy.authentication.AuthenticationService;
import cu.academy.authentication.dto.UserResponseDto;
import cu.academy.shared.configs.text_messages.Translator;
import cu.academy.shared.exceptions.ArgumentException;
import cu.academy.shared.utils.TranslatorCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final AuthenticationService authService;

    public CustomAuthenticationProvider(AuthenticationService authService) {
        this.authService = authService;
    }

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        String password = authentication.getCredentials().toString();
        final String username = (authentication.getPrincipal() == null) ? "NONE_PROVIDED" : authentication.getName();
        if (StringUtils.isEmpty(username)) {
            throw new AccessDeniedException(Translator.toLocale(TranslatorCode.INVALID_CREDENTIALS));
        }
        UserResponseDto userResponseDto;
        try {
            //Valida usuario y contraseña...
            userResponseDto = authService.login(username, password, (LoginDetails) authentication.getDetails());
        } catch (Exception e) {
            String msg = e.getMessage();
            if (e instanceof ArgumentException)
                msg = ((ArgumentException) e).getMsg();
            throw new AccessDeniedException(msg);
        }

        return new UsernamePasswordAuthenticationToken(userResponseDto, password, userResponseDto.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
