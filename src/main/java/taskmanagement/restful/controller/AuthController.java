package taskmanagement.restful.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import taskmanagement.restful.model.LoginUserRequest;
import taskmanagement.restful.model.RegisterUserRequest;
import taskmanagement.restful.model.TokenResponse;
import taskmanagement.restful.model.WebResponse;
import taskmanagement.restful.service.AuthService;
import org.springframework.http.MediaType;


@RestController
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping(
        path = "/api/auth/login",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<TokenResponse> login(@RequestBody LoginUserRequest request) {
        TokenResponse tokenResponse = authService.login(request);
        return WebResponse.<TokenResponse>builder().data(tokenResponse).build();
    }

    @PostMapping(
        path = "/api/auth/register",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> register(@RequestBody RegisterUserRequest request) {  
        authService.register(request);
        return WebResponse.<String>builder().data("OK").build();
    }
}
