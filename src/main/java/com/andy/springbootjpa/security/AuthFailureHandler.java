package com.andy.springbootjpa.security;

import com.andy.springbootjpa.dto.ResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class AuthFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {

        response.setStatus(HttpStatus.FORBIDDEN.value());
        ResponseDTO data = new ResponseDTO(HttpStatus.FORBIDDEN.value(), exception.getMessage());
        response.getOutputStream().println(new ObjectMapper().writeValueAsString(data));

    }


}
