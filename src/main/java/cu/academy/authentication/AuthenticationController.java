package cu.academy.authentication;

import cu.academy.authentication.dto.LogoutRequest;
import cu.academy.shared.utils.EndpointResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;



@Controller
@RequestMapping(path = "/academy")
public class AuthenticationController {

    private final AuthenticationService authService;

    @Autowired
    public AuthenticationController(AuthenticationService authService) {
        this.authService = authService;
    }

    @PostMapping("/logout")
    public ResponseEntity<EndpointResult> logout(@Valid @RequestBody LogoutRequest logoutRequest,HttpServletRequest request) throws Exception {
        authService.logout(logoutRequest,request);
        return ResponseEntity.ok(new EndpointResult(null, null));
    }

    @GetMapping("/refresh_token")
    public ResponseEntity<EndpointResult> refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception {
        authService.refreshToken(request, response);
        return ResponseEntity.ok(new EndpointResult(null, null));
    }
}