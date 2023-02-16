package com.varukha.webproject.controller.tag;


import jakarta.servlet.http.HttpSession;
import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.tagext.TagSupport;

import java.io.IOException;

public class HelloTag extends TagSupport {


        private String role;
        public void setRole(String role) {
            this.role = role;
        }
        @Override
        public int doStartTag() throws JspException {
            try {
                String to = null;
                if (role.equalsIgnoreCase("MANAGER")) {
                    to = "Hello, " + role;
                } else
                if (role.equalsIgnoreCase("USER")) {
                    to = "Welcome, " + role;
                } else {
                    to = "";
                }
                pageContext.getOut().write("<hr/>" + to + "<hr/>");
            } catch (IOException e) {
                throw new JspException(e.getMessage());
            }
            return SKIP_BODY;
        }
    }

