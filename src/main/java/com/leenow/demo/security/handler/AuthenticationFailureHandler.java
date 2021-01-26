package com.leenow.demo.security.handler;


import com.leenow.demo.exception.AbstractBaseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Authentication failure handler.
 *
 * @author johnniang
 */
public interface AuthenticationFailureHandler {

    /**
     * Calls when a user has been unsuccessfully authenticated.
     *
     * @param request   http servlet request
     * @param response  http servlet response
     * @param exception api exception
     * @throws IOException      io exception
     * @throws ServletException service exception
     */
    void onFailure(HttpServletRequest request, HttpServletResponse response, AbstractBaseException exception) throws IOException, ServletException;
}
