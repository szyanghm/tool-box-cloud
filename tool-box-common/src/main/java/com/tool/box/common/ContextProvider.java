package com.tool.box.common;

import lombok.Getter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Getter
class ContextProvider {

    private HttpServletRequest request;
    private HttpServletResponse response;

    ContextProvider(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }
}
