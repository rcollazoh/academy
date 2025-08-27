package cu.academy.shared.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class SecurityUtils {

    public static String getNewAccessToken(HttpServletRequest request, String username, Algorithm algorithm,
                                           List<SimpleGrantedAuthority> authorities) {
        return JWT.create()
                .withSubject(username)
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);

    }

    public static String getNewRefreshToken(HttpServletRequest request, String username, Algorithm algorithm) {
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + 100 * 60 * 1000))
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);
    }
}
