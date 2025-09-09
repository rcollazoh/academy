package cu.academy.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import cu.academy.authentication.dto.LogoutRequest;
import cu.academy.authentication.dto.UserResponseDto;
import cu.academy.person.PersonEntity;
import cu.academy.person.PersonRepository;
import cu.academy.person.PersonService;
import cu.academy.shared.configs.text_messages.Translator;
import cu.academy.shared.exceptions.ArgumentException;
import cu.academy.shared.security.SecurityUtils;
import cu.academy.shared.utils.EndpointResult;
import cu.academy.shared.utils.TranslatorCode;
import cu.academy.shared.filter.LoginDetails;
import cu.academy.trace.TraceService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
public class AuthenticationService {

    private final PersonRepository personRepository;
    private final PersonService personaService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    private static final String className = "AuthenticationService";
    private final TraceService traceService;

    public AuthenticationService(PersonService personaService,
                                 PersonRepository personRepository, TraceService traceService
//                                ,EmailService emailService
    ) {
        this.personRepository = personRepository;
        this.personaService = personaService;
//        this.emailService = emailService;
        this.traceService = traceService;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public UserResponseDto login(String username, String password,
                                 LoginDetails loginDetails) throws ArgumentException {
        try {
//            EnumTipoPersona tipoPersona = loginDetails.getPersonType();

//            verifyIfConditionTrueThrowArgumentException(tipoPersona == null || username == null || password == null,
//                    TranslatorCode.TIPO_PERSON_REQUERIDO);

            PersonEntity user = personRepository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException(Translator.toLocale(TranslatorCode.NO_CLIENTE)));

            if (!passwordEncoder.matches(password, user.getPassword())) {
                throw new BadCredentialsException(Translator.toLocale(TranslatorCode.INVALID_CREDENTIALS));
            }

            if (user.getPassword() == null)
                throw new BadCredentialsException(Translator.toLocale(TranslatorCode.NO_PASSWORD));

//            // Verifica que el usuario sea del tipo de persona especificado
//            personaService.validatePersonTypeForPerson(user, tipoPersona);

            Collection<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
// crear traza

            return new UserResponseDto(username, password, grantedAuthorities, user.getId(),
                    user.getName().concat(" ").concat(user.getLastName()) , user.getEmail(), user.getPhone(), user.getArea().getId(),user.getPractice().getId());
        } catch (Exception ex) {
//            trazaLogSistemaService.insertLog(className, methodName, "Error en el loging: " + username + " " + loginDetails.toString(), ex,
//                    EnumTipoPersona.CLIENTE.equals(loginDetails.getTipoPersona()) ? 1 : EnumTipoPersona.CHOFER.equals(loginDetails.getTipoPersona()) ? 2 : 3);
            throw ex;
        }

    }

    private static void verifyIfConditionTrueThrowArgumentException(boolean condition, String errorMessage) {
        if (condition)
            throw new ArgumentException(Translator.toLocale(errorMessage));
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void logout(LogoutRequest logoutRequest, HttpServletRequest request) {
        traceService.insertTraceLogout(personaService.getById(logoutRequest.getPersonId()),request);
    }

    @Transactional
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String methodName = "refreshToken";
        try {
            String authorizationHeader = request.getHeader(AUTHORIZATION);
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                try {
                    String refreshToken = authorizationHeader.substring("Bearer ".length());
                    Algorithm algorithm = Algorithm.HMAC256("LpciQdpzs7t69JIYcyvbbFZ6cJZN0jVMpRrPaht0Yv7JX8Ss31srhlhRvWvwd4o".getBytes());
                    JWTVerifier verifier = JWT.require(algorithm).build();
                    DecodedJWT decodedJWT = verifier.verify(refreshToken);
                    String username = decodedJWT.getSubject();
                    String origin = decodedJWT.getClaims().get("origin").asString();
                    String role = decodedJWT.getClaims().get("role").asString();

                    PersonEntity person = personRepository.findByEmail(username)
                            .orElseThrow(() -> new ArgumentException(Translator.toLocale(TranslatorCode.NO_CLIENTE)));

//                    refreshAndSetNewAccessAndRefreshTokens(request, response, algorithm, person.getPhone(),
//                            new ArrayList<>(personaService.getRolesGrantedAuthorities(person)), origin, role);
                    new ObjectMapper().writeValue(response.getOutputStream(), new EndpointResult<>(null, null));
                } catch (TokenExpiredException e) {
                    logAndWriteResponseError(e, response, TranslatorCode.TOKEN_EXPIRED);
                } catch (Exception e) {
                    logAndWriteResponseError(e, response, TranslatorCode.INVALID_CREDENTIALS);
                }
            } else {
                throw new RuntimeException("Refresh token is missing.");
            }
        } catch (Exception ex) {
//            trazaLogSistemaService.insertLog(className, methodName, "Error al refrezcar el token.", ex, 3);
            throw ex;
        }

    }

    private static void logAndWriteResponseError(Exception e, HttpServletResponse response, String errorMessage) throws IOException {
      //  logger.error("Error AuthenticationService: " + e.getMessage());
        response.setStatus(FORBIDDEN.value());
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), new EndpointResult<>(null,
                Translator.toLocale(errorMessage)));
    }

    public void refreshAndSetNewAccessAndRefreshTokens(HttpServletRequest request, HttpServletResponse response,
                                                       Algorithm algorithm, String username,
                                                       List<SimpleGrantedAuthority> authorities) {
        String newAccessToken = SecurityUtils.getNewAccessToken(request, username, algorithm, authorities);
        String newRefreshToken = SecurityUtils.getNewRefreshToken(request, username, algorithm);
        response.setHeader("access_token", newAccessToken);
        response.setHeader("refresh_token", newRefreshToken);
        response.setContentType(APPLICATION_JSON_VALUE);
    }

    /**
     * Verifica que el telefono y correo son de una misma persona y enviar la clave al correo. Es para el sitio web renetaxi
     *
     * @return
     * @throws ArgumentException
     */
//    @Transactional(rollbackFor = RuntimeException.class)
//    public String recuperarClave(String telefCelular, String correo) throws ArgumentException {
//        String methodName = "recuperarClave";
//        try {
//            String mensaje = "";
//            PersonEntity cliente = personRepository.findByPhone(telefCelular).orElse(null);
//
//            //Se comprueba que el correo sea de la misma persona q el telefono.
//            if (cliente != null && cliente.getEmail() != null && cliente.getEmail().equals(correo)) {
//                StringUtils util = new StringUtils();
//                String passwordTemporal = util.generateRandomPass();
//
//                emailService.sendMenssaje(correo, "ReneTaxis-Recuperar clave", textMessage, cliente.getOtrosCorreos());
//
//                mensaje = textMessage;
//                String passwordTemporalEncode = bCryptPasswordEncoder.encode(passwordTemporal);
//                cliente.setPassword_temporal(passwordTemporalEncode);
//                personRepository.save(cliente);
//
//            } else {
//                mensaje = "El correo: " + correo + " no pertenece al usuario con número de télefono: " + telefCelular;
//            }
//            return mensaje;
//        } catch (Exception ex) {
//           // trazaLogSistemaService.insertLog(className, methodName, "Error al recuperar clave del telefono." + telefCelular, ex, 3);
//            throw ex;
//        }
//
//    }
}