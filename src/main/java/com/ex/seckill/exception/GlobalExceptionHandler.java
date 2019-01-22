package com.ex.seckill.exception;

import com.ex.seckill.utils.ServerResponse;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ServerResponse exceptionHandler(HttpServletRequest request,Exception e){
        if(e instanceof GlobalException){
            GlobalException globalException=(GlobalException)e;
            System.out.println("================"+globalException.getMsg()+"================");
            globalException.printStackTrace();
            return ServerResponse.createByErrorMessage(globalException.getMsg());
        } else if(e instanceof BindException){
            BindException ex=(BindException)e;
            List<ObjectError> errors=ex.getAllErrors();
            StringBuilder msg=new StringBuilder("");
            for(ObjectError objectError:errors){
                msg.append(objectError.getDefaultMessage());
            }
            System.out.println("================"+msg.toString()+"================");
            ex.printStackTrace();
            return ServerResponse.createByErrorMessage(msg.toString());
        }else{
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("系统错误！");
        }
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        System.out.println("============应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器");
    }

    /*@ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String processUnauthenticatedException(NativeWebRequest request, Exception e) {
        System.out.println("===========应用到所有@RequestMapping注解的方法，Exception");
        return "viewName"; //返回一个逻辑视图名
    }*/

}
