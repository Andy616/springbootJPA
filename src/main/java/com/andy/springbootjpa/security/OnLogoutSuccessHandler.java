package com.andy.springbootjpa.security;

import com.andy.springbootjpa.dto.ResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OnLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                Authentication authentication) throws IOException, ServletException {
        ResponseDTO response = new ResponseDTO();
        response.setMessage("Logged out");
        httpServletResponse.getOutputStream().println(new ObjectMapper().writeValueAsString(response));

    }
}
