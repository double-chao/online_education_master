package com.lcc.educenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lcc.educenter.entity.UcenterMember;
import com.lcc.educenter.entity.vo.RegisterVo;
import com.lcc.educenter.mapper.UcenterMemberMapper;
import com.lcc.educenter.service.UcenterMemberService;
import com.lcc.servicebase.exceptionhandler.BadException;
import com.lcc.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author chaochao
 * @since 2020-05-31
 */
@Slf4j
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public String login(UcenterMember member) {
        String phone = member.getMobile();
        String password = member.getPassword();
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(password)) {
            throw new BadException(20001, "账号或密码不能为空，登录失败");
        }
        QueryWrapper<UcenterMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile", phone);
        UcenterMember mobileMember = baseMapper.selectOne(queryWrapper);
        if (mobileMember == null) {
            throw new BadException(20001, "手机号还未注册，请先注册吧");
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode(password);
        if (!passwordEncoder.matches(password, encode)) {
            throw new BadException(20001, "密码错误，请重新输入，登录失败");
        }
        if (mobileMember.getIsDisabled()) {
            throw new BadException(20001, "该用户被禁用，登录失败");
        }
        //登录成功  返回一个token值  把登录成功后数据库中查询的数据    用户id和昵称放入到token中
        return JwtUtils.getJwtToken(mobileMember.getId(), mobileMember.getNickname());
    }

    @Override
    public void register(RegisterVo registerVo) {
        String code = registerVo.getCode();
        String mobile = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();
        if (StringUtils.isEmpty(code) || StringUtils.isEmpty(mobile)
                || StringUtils.isEmpty(nickname) || StringUtils.isEmpty(password)) {
            throw new BadException(20001, "账号、密码、昵称、验证码不能为空，注册失败");
        }
        String phoneCode = redisTemplate.opsForValue().get(mobile); //根据redis的key值，得到code
        if (!code.equals(phoneCode)) {
            throw new BadException(20001, "手机注册码错误，请重新输入");
        }
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if (count > 0) {
            throw new BadException(20001, "手机号已注册，去登录吧");
        }
        UcenterMember ucenterMember = new UcenterMember();
        BeanUtils.copyProperties(registerVo, ucenterMember);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        ucenterMember.setPassword(passwordEncoder.encode(password));
        baseMapper.insert(ucenterMember);
    }

    @Override
    public UcenterMember getOpenIdMember(String openid) {
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid", openid);
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public Integer countRegisterDay(String day) {
        return baseMapper.countRegisterByDay(day);
    }
}
