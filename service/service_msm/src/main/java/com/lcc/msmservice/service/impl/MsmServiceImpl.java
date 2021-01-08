package com.lcc.msmservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.lcc.msmservice.service.MsmService;
import com.lcc.msmservice.utils.AliyunConstantUtils;
import com.lcc.msmservice.utils.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class MsmServiceImpl implements MsmService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean sendMsm(String phone) {
        if (StringUtils.isEmpty(phone)) {
            return false;
        }
        //1 从redis获取验证码，如果获取到直接返回
        String code = redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(code)) {
            return true;
        }
        //2 如果redis获取 不到，进行阿里云发送
        //生成随机值，传递阿里云进行发送
        code = RandomUtil.getSixBitRandom();
        Map<String, Object> param = new HashMap<>();
        param.put("code", code);

        DefaultProfile profile =
                DefaultProfile.getProfile("default", AliyunConstantUtils.ACCESS_KEY_ID,
                        AliyunConstantUtils.ACCESS_KEY_SECRET);
        IAcsClient client = new DefaultAcsClient(profile);
        //设置相关固定的参数
        CommonRequest request = new CommonRequest();
//        request.setSysProtocol(ProtocolType.HTTPS);
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");

        //设置发送相关的参数
        request.putQueryParameter("PhoneNumbers", phone); //手机号
        request.putQueryParameter("SignName", "直达在线教育网站"); //申请阿里云 签名名称
        request.putQueryParameter("TemplateCode", "SMS_191766536"); //申请阿里云 模板code
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param)); //验证码数据，转换json数据传递
        try {
            //最终发送
            CommonResponse response = client.getCommonResponse(request);
            if (response.getHttpResponse().isSuccess()) {
                //发送成功，把发送成功验证码放到redis里面
                //设置有效时间  把输入的手机号码作为key值存到redis中
                redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);
                log.info("验证码发送成功，并且把发送成功的验证码存到redis中");
                return true;
            }else {
                log.info("验证码发送失败");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}