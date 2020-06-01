package com.lcc.educenter.controller;

import com.google.gson.Gson;
import com.lcc.educenter.entity.UcenterMember;
import com.lcc.educenter.service.UcenterMemberService;
import com.lcc.educenter.utils.ConstantWxUtils;
import com.lcc.educenter.utils.HttpClientUtils;
import com.lcc.servicebase.exceptionhandler.BadException;
import com.lcc.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URLEncoder;
import java.util.HashMap;

@CrossOrigin
@Controller
@RequestMapping("/api/ucenter/wx") //这个请求路径是为了本地测试，在域名中配置了请求到本地的路径地址
public class WxApiController {

    @Autowired
    private UcenterMemberService memberService;

    //2 获取扫描人信息，添加数据 扫码确认后，调用这个本地方法
    @GetMapping("/callback")  // 请求路径为固定了，因为在域名中配置了，请求到本地的路径
    public String callback(String code, String state) {
        try {
            //1 获取code值，临时票据，类似于验证码
            //2 拿着code请求 微信固定的地址，得到两个值 accsess_token 和 openid
            String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";
            //拼接三个参数 ：id  秘钥 和 code值
            String accessTokenUrl = String.format(baseAccessTokenUrl, ConstantWxUtils.WX_OPEN_APP_ID,
                    ConstantWxUtils.WX_OPEN_APP_SECRET, code
            );
            //请求这个拼接好的地址，得到返回两个值 accsess_token 和 openid
            //使用httpclient发送请求，得到返回结果
            String accessTokenInfo = HttpClientUtils.get(accessTokenUrl);
            //从accessTokenInfo字符串获取出来两个值 accsess_token 和 openid
            //accessTokenInfo是一个json对象，key-value
            //把accessTokenInfo字符串转换map集合，根据map里面key获取对应值
            Gson gson = new Gson(); //使用json转换工具谷歌的，直接把json对象转换为map集合
            HashMap mapAccessToken = gson.fromJson(accessTokenInfo, HashMap.class);
            String access_token = (String) mapAccessToken.get("access_token");
            String openid = (String) mapAccessToken.get("openid");
            //把扫描人信息添加数据库里面,微信登录不用注册
            //判断数据表里面是否存在相同微信信息，根据openid判断
            UcenterMember member = memberService.getOpenIdMember(openid);
            if (member == null) {//数据库中没有相同微信数据，进行添加
                //3 拿着得到accsess_token 和 openid，再去请求微信提供固定的地址，获取到扫描人信息
                //访问微信的资源服务器，获取用户信息
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" + "&openid=%s";
                String userInfoUrl = String.format(baseUserInfoUrl, access_token, openid); //拼接两个参数
                //发送请求
                String userInfo = HttpClientUtils.get(userInfoUrl);
                //获取返回userinfo字符串扫描人信息
                HashMap userInfoMap = gson.fromJson(userInfo, HashMap.class);
                String nickname = (String) userInfoMap.get("nickname"); //key值为json对象中的key
                String headimgurl = (String) userInfoMap.get("headimgurl");
                member = new UcenterMember();
                member.setOpenid(openid);
                member.setNickname(nickname);
                member.setAvatar(headimgurl);
                memberService.save(member); //添加到数据库
            }
            //使用jwt根据member对象生成token字符串
            String jwtToken = JwtUtils.getJwtToken(member.getId(), member.getNickname());
            //返回首页面，通过路径传递token字符串
            return "redirect:http://localhost:3000?token=" + jwtToken;
        } catch (Exception e) {
            throw new BadException(20001, "登录失败");
        }
    }

    //1 生成微信扫描二维码
    @GetMapping("/login")   // 请求路径为固定了，因为在域名中配置了，请求到本地的路径
    public String getWxCode() {
        //固定地址，后面拼接参数,看腾讯开发文档，里面参数意思都有描述
//        String url = "https://open.weixin.qq.com/" +
//                "connect/qrconnect?appid="+ ConstantWxUtils.WX_OPEN_APP_ID+"&response_type=code";
        // 微信开放平台授权baseUrl  %s相当于?代表占位符,意思就是要添加参数
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";
        //对redirect_url进行URLEncoder编码
        String redirectUrl = ConstantWxUtils.WX_OPEN_REDIRECT_URL;
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "utf-8");
        } catch (Exception e) {

        }
        //设置%s里面值
        String url = String.format(baseUrl, ConstantWxUtils.WX_OPEN_APP_ID, redirectUrl, "zdonline"
        );
        //重定向到请求微信地址里面
        return "redirect:" + url;
    }
}
