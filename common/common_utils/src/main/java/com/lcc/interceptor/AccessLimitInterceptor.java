package com.lcc.interceptor;

import com.lcc.annotation.AccessLimit;
import com.lcc.result.Result;
import com.lcc.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

/**
 * @Author Administrator
 * @Description 接口防刷拦截器
 * @Date 2021/2/19  17:44
 */
@Slf4j
@Component
public class AccessLimitInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断请求是否属于方法的请求
        if (handler instanceof HandlerMethod) {

            HandlerMethod hm = (HandlerMethod) handler;

            //获取方法中的注解,看是否有该注解
            AccessLimit accessLimit = hm.getMethodAnnotation(AccessLimit.class);
            if (accessLimit == null) {
                return true;
            }
            int seconds = accessLimit.seconds();
            int maxCount = accessLimit.maxCount();
            boolean login = accessLimit.needLogin();
            String key = request.getRequestURI();
            //如果需要登录
            if (login) {
                //获取登录的session进行判断
                boolean checkToken = JwtUtils.checkToken(request);
                if (checkToken) {
                    Integer memberId = JwtUtils.getMemberIdByJwtToken(request);
                    if (memberId != 0) {
                        key += "" + memberId;  //动态获取的userId
                    } else {
                        key += "10010";  //获取用户id失败，用10010代替
                    }
                }else {
                    return false; // Token失效了
                }
            }

            String s = stringRedisTemplate.opsForValue().get(key);
            //从redis中获取用户访问的次数
            int count = 0;
            if (!"".equals(s) && s != null) {
                count = Integer.parseInt(s);
                log.info("count的值为：。。。" + count);
            }
            if (count == 0) {
                //第一次访问
                stringRedisTemplate.opsForValue().set(key, "1", seconds, TimeUnit.SECONDS);
            } else if (count < maxCount) {
                //加1
                stringRedisTemplate.opsForValue().set(key, "" + count + 1, seconds, TimeUnit.SECONDS);
            } else {
                //超出访问次数
                render(response); //这里的CodeMsg是一个返回参数
                log.info("超出访问限制le .............");
                return false;
            }
        }
        return true;
    }

    private void render(HttpServletResponse response) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        OutputStream out = response.getOutputStream();
        String str = Result.fail().message("超出访问限制").toString();
        out.write(str.getBytes("UTF-8"));
        out.flush();
        out.close();
    }
}
