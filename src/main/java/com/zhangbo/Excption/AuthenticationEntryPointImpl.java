package com.zhangbo.Excption;

import com.alibaba.fastjson.JSON;
import com.zhangbo.until.Result;
import com.zhangbo.until.Status;
import com.zhangbo.until.WebUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        Result result =new Result(403,"用户名或密码错误",null);
        String json = JSON.toJSONString(result);
        WebUtils.renderString(response,json);
    }
}

