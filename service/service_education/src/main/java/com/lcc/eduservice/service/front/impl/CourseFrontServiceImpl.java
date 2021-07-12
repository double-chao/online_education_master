package com.lcc.eduservice.service.front.impl;

import com.lcc.constant.OrderConstant;
import com.lcc.eduservice.client.OrderClient;
import com.lcc.eduservice.entity.chapter.ChapterVo;
import com.lcc.eduservice.entity.frontvo.CourseWebVo;
import com.lcc.eduservice.service.EduChapterService;
import com.lcc.eduservice.service.EduCourseService;
import com.lcc.eduservice.service.front.CourseFrontService;
import com.lcc.result.Result;
import com.lcc.servicebase.exceptionhandler.BadException;
import com.lcc.servicebase.exceptionhandler.CodeEnum;
import com.lcc.util.JwtUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Author Administrator
 * @Description
 * @Date 2021/3/28  19:35
 */
@Service
public class CourseFrontServiceImpl implements CourseFrontService {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduChapterService chapterService;

    @Autowired
    private OrderClient orderClient;

    @Autowired
    private ThreadPoolExecutor poolExecutor;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Result getFrontCourseInfoByCourseId(Integer courseId, HttpServletRequest request) {
        boolean checkToken = JwtUtils.checkToken(request);
        if (checkToken) {
            Integer memberId = JwtUtils.getMemberIdByJwtToken(request);
            if (0 == memberId) {
                throw new BadException(CodeEnum.USER_NO_LOGIN_EXCEPTION);
            }
            CourseWebVo courseWebVo = new CourseWebVo();
            CompletableFuture<Void> courseWebVoFuture = CompletableFuture.runAsync(() -> {
                CourseWebVo getCourseWebVo = courseService.getBaseCourseInfo(courseId);
                BeanUtils.copyProperties(getCourseWebVo, courseWebVo);
            }, poolExecutor);

            List<ChapterVo> chapterVideoList = new ArrayList<>();
            CompletableFuture<Void> chapterVideoListFuture = CompletableFuture.runAsync(() -> {
                List<ChapterVo> getChapterVideoList = chapterService.getChapterAndVideoById(courseId);
                chapterVideoList.addAll(getChapterVideoList);
            }, poolExecutor);

            AtomicBoolean buyCourse = new AtomicBoolean(false);
            CompletableFuture<Void> buyCourseFuture = CompletableFuture.runAsync(() -> {
                boolean flag = orderClient.isBuyCourse(courseId, memberId);
                buyCourse.set(flag);
            }, poolExecutor);

            try {
                CompletableFuture.allOf(courseWebVoFuture, chapterVideoListFuture, buyCourseFuture).get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

            BigDecimal price = courseWebVo.getPrice();
            BigDecimal freePrice = new BigDecimal("0");
            boolean isBuy = buyCourse.get();
            if (price.compareTo(freePrice) != 0 && isBuy == false) { // 课程的价格不为0并且没有购买的课程，需要生成订单的token
                String token = UUID.randomUUID().toString().replace("-", "");
                courseWebVo.setToken(token); // 客户端保存一个防重令牌
                // redis中添加一个随机Token 防重令牌 设置过期时间，30分钟
                stringRedisTemplate.opsForValue().set(OrderConstant.USER_ORDER_TOKEN_PREFIX + memberId, token,30, TimeUnit.MINUTES);
            }

            return Result.ok().data("courseWebVo", courseWebVo).data("chapterVideoList", chapterVideoList).data("isBuy", buyCourse);
        } else {
            throw new BadException(CodeEnum.LOGIN_EXPIRED_EXCEPTION);
        }
    }
}
