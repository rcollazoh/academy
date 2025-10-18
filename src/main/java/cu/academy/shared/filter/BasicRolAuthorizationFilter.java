package cu.academy.shared.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import cu.academy.shared.configs.text_messages.Translator;
import cu.academy.shared.enum_types.EnumRole;
import cu.academy.shared.utils.TranslatorCode;
import cu.academy.shared.enum_types.EnumTipoPersona;
import cu.academy.shared.utils.EndpointResult;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class BasicRolAuthorizationFilter extends OncePerRequestFilter {

    private final Logger myLogger = LoggerFactory.getLogger(BasicRolAuthorizationFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        myLogger.info("Executing BasicRolAuthorizationFilter...");
        if (request.getServletPath().equals("/academy/login") || request.getServletPath().equals("/academy/refresh_token") || request.getServletPath().equals("/academy/person/recover_key"))
            filterChain.doFilter(request, response);
        else {
            String authorizationHeader = request.getHeader(AUTHORIZATION);
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                try {
                    String origin = (String) request.getAttribute("origin");
                    if (EnumTipoPersona.CLIENT.name().equals(origin)) {
                        String role = (String) request.getAttribute("role");
                        EnumRole enumRol = EnumRole.valueOf(role);
                    }
                    filterChain.doFilter(request, response);
                } catch (Exception e) {
                    logger.error("Error CustomAuthorizationFilter: " + e.getMessage());
                    response.setStatus(FORBIDDEN.value());
                    response.setContentType(APPLICATION_JSON_VALUE);
                    new ObjectMapper().writeValue(response.getOutputStream(),
                            new EndpointResult<>(null, Translator.toLocale(TranslatorCode.INVALID_CREDENTIALS)));
                }
            } else
                filterChain.doFilter(request, response);
        }
    }
}
