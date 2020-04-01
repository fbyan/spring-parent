package com.yaomy.sgrain.exception.api;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @program: spring-parent
 * @description: 自定义异常处理控制器
 * @author: 姚明洋
 * @create: 2020/03/31
 */
@RestController
public class SgrainErrorController implements ErrorController {
    /**
     * 错误路由
     */
    private static final String PATH = "/error";

    @Override
    public String getErrorPath() {
        return PATH;
    }
    @RequestMapping(PATH)
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request, Throwable ex){
        HttpStatus status = getStatus(request);
        Map<String, Object> result = Maps.newLinkedHashMap();
        result.put("status", status.value());
        if(StringUtils.isNotEmpty(ex.getMessage())){
            result.put("message", ex.getMessage());
        } else {
            result.put("message", status.getReasonPhrase());
        }
        return ResponseEntity.ok(result);
    }

    /**
     * 获取错误的状态码
     */
    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        try {
            return HttpStatus.valueOf(statusCode);
        }
        catch (Exception ex) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

}
