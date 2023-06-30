package com.blog.controller;

import com.blog.exceptions.ApiException;
import com.blog.payload.JWTAuthRequest;
import com.blog.response.JWTAuthResponse;
import com.blog.security.JWTTokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTTokenHelper jwtTokenHelper;

    @PostMapping("/login")
    public ResponseEntity<JWTAuthResponse> createToken(@RequestBody JWTAuthRequest request) throws Exception {
        this.authenticate(request.getUserName(), request.getPassword());

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUserName());
        String token = this.jwtTokenHelper.generateToken(userDetails);
        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setToken(token);
        return ResponseEntity.ok(jwtAuthResponse);
    }

    private void authenticate(String userName, String password) throws Exception {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userName, password);
        try {
            this.authenticationManager.authenticate(authenticationToken);
        }catch (BadCredentialsException e){
            System.out.println("Invalid Details !!");
            throw new ApiException("Invalid UserName or Password");
        }
    }

}
