package cu.academy.shared.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import cu.academy.shared.configs.text_messages.Translator;
import cu.academy.shared.utils.EndpointResult;
import cu.academy.shared.utils.TranslatorCode;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class CustomAuthorizationFilter extends OncePerRequestFilter {

    private final Logger logger = LoggerFactory.getLogger(CustomAuthorizationFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        logger.info("Executing CustomAuthorizationFilter...");
        if (request.getServletPath().equals("/academy/login") || request.getServletPath().equals("/academy/refresh_token"))
            filterChain.doFilter(request, response);
        else {
            String authorizationHeader = request.getHeader(AUTHORIZATION);
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                try {
                    String tokenAuth = authorizationHeader.substring("Bearer ".length());
                    Algorithm algorithm = Algorithm.HMAC256("LpciQdpzs7t69JIYcyvbbFZ6cJZN0jVMpRrPaht0Yv7JX8Ss31srhlhRvWvwd4o".getBytes());
                    JWTVerifier verifier = JWT.require(algorithm).build();
                    DecodedJWT decodedJWT = verifier.verify(tokenAuth);
                    String username = decodedJWT.getSubject();
                    String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
                    String origin = decodedJWT.getClaim("origin").asString();
                    String roleAuth = decodedJWT.getClaim("role").asString();
                    request.setAttribute("roles", roles);
                    request.setAttribute("origin", origin);
                    request.setAttribute("role", roleAuth);
                    Collection<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
                    if(roles!=null && roles.length>0){
                        stream(roles).forEach(role -> grantedAuthorities.add(new SimpleGrantedAuthority(role)));
                    }
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(username, null, grantedAuthorities);
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    filterChain.doFilter(request, response);
                } catch (Exception e) {
                    logger.error("Error CustomAuthorizationFilter: " + e.getMessage());
                    if (e instanceof com.auth0.jwt.exceptions.TokenExpiredException){
                        response.setStatus(UNAUTHORIZED.value());
                        response.setContentType(APPLICATION_JSON_VALUE);
                        new ObjectMapper().writeValue(response.getOutputStream(), new EndpointResult(null, Translator.toLocale(TranslatorCode.TOKEN_EXPIRED)));
                    } else {
                        response.setStatus(FORBIDDEN.value());
                        response.setContentType(APPLICATION_JSON_VALUE);
                        new ObjectMapper().writeValue(response.getOutputStream(), new EndpointResult(null, Translator.toLocale(TranslatorCode.INVALID_CREDENTIALS)));
                    }
                }
            } else
                filterChain.doFilter(request, response);
        }
    }
}
