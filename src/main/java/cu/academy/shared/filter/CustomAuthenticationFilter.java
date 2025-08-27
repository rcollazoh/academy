package cu.academy.shared.filter;

import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import cu.academy.authentication.AuthenticationService;
import cu.academy.authentication.dto.UserResponseDto;
import cu.academy.shared.enum_types.EnumTipoPersona;
import cu.academy.shared.utils.EndpointResult;
import cu.academy.trace.TraceService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final Logger logger = LoggerFactory.getLogger(CustomAuthenticationFilter.class);
    private final AuthenticationManager authenticationManager;
    private final AuthenticationService authenticationService;
    private final TraceService traceService;


    public CustomAuthenticationFilter(AuthenticationManager authenticationManager,
                                      AuthenticationService authenticationService, TraceService traceService) {
        this.authenticationManager = authenticationManager;
        this.authenticationService = authenticationService;
        this.traceService = traceService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        logger.info("Executing CustomAuthenticationFilter...");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String tipoPersonaParam = request.getParameter("personType");
        String token = request.getParameter("token");
        String idClient = request.getParameter("idCliente");
        EnumTipoPersona typePerson = tipoPersonaParam == null ? null : EnumTipoPersona.valueOf(tipoPersonaParam);

        LoginDetails loginDetails = new LoginDetails(typePerson,token, idClient);
        logger.info("tipoPersona is: " + typePerson);
        logger.info("username is: " + username);
        logger.info("password is: " + password);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        authenticationToken.setDetails(loginDetails);
        try {
            return authenticationManager.authenticate(authenticationToken);
        } catch (Exception e) {
            response.setContentType(APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.displayName());
            response.setStatus(FORBIDDEN.value());
            try {
                EndpointResult endpointResult = new EndpointResult<>(null, e.getMessage());
                PrintWriter out = response.getWriter();
                out.println(endpointResult.toJsonString());
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return null;
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authentication) throws IOException {
        UserResponseDto user = (UserResponseDto) authentication.getPrincipal();
        Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) authentication.getAuthorities();
        Algorithm algorithm = Algorithm.HMAC256("LpciQdpzs7t69JIYcyvbbFZ6cJZN0jVMpRrPaht0Yv7JX8Ss31srhlhRvWvwd4o".getBytes());
        authenticationService.refreshAndSetNewAccessAndRefreshTokens(request, response, algorithm, user.getMobilePhone(),
                new ArrayList<>(authorities));
        traceService.insertTraceLogin(user,request);
        new ObjectMapper().writeValue(response.getOutputStream(), new EndpointResult<>(user, null));
    }
}