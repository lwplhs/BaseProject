package com.lwp.website.interceptor;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.net.BindException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/05/29/11:47
 * @Description:
 */
@RestControllerAdvice
public class MyExceptionHandler {
    private static Logger LOGGER = LoggerFactory.getLogger(MyExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public Object handleException(Exception e, HttpServletRequest request, HttpServletResponse response){
        e.printStackTrace();
        LOGGER.error("请求：{}发生异常：{}", request.getRequestURI(), e);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",-1);
        jsonObject.put("msg","请求异常");
        return jsonObject.toString();

    }

    /**
     * 自定义注解异常拦截
     */
    @ExceptionHandler({BindException.class, ConstraintViolationException.class, MethodArgumentNotValidException.class})
    public Object handleMethodArgumentNotValidException(Exception e, HttpServletRequest request) {

        LOGGER.error("请求：{}发生异常：{}", request.getRequestURI(), e);

        // 错误信息
        StringBuilder sb = new StringBuilder("参数校验失败：");
        // 错误信息map
        Map<String, String> error = new HashMap<>();

        String msg = "";
        if (!(e instanceof BindException) && !(e instanceof MethodArgumentNotValidException)) {
            for (ConstraintViolation cv : ((ConstraintViolationException) e).getConstraintViolations()) {
                msg = cv.getMessage();
                sb.append(msg).append("；");

                Iterator<Path.Node> it = cv.getPropertyPath().iterator();
                Path.Node last = null;
                while (it.hasNext()) {
                    last = (Path.Node) it.next();
                }
                /*for(last = null; it.hasNext(); last = (Path.Node)it.next()) {
                }*/
                error.put(last != null ? last.getName() : "", msg);
            }
        } else {
            List<ObjectError> allErrors = null;
            if (e instanceof BindException) {
                //allErrors = ((BindException) e).get;
            } else {
                allErrors = ((MethodArgumentNotValidException) e).getBindingResult().getAllErrors();
            }
            // 拼接错误信息
            for (ObjectError oe : allErrors) {
                msg = oe.getDefaultMessage();
                sb.append(msg).append("；");
                if (oe instanceof FieldError) {
                    error.put(((FieldError) oe).getField(), msg);
                } else {
                    error.put(oe.getObjectName(), msg);
                }
            }
        }
        return "";
    }
}
