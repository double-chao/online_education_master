package com.lcc.educenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lcc.educenter.entity.UcenterMember;
import com.lcc.educenter.entity.vo.RegisterVo;
import com.lcc.educenter.mapper.UcenterMemberMapper;
import com.lcc.educenter.service.UcenterMemberService;
import com.lcc.servicebase.exceptionhandler.BadException;
import com.lcc.servicebase.exceptionhandler.CodeEnum;
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
            throw new BadException(CodeEnum.USERNAME_OR_PASSWORD_NOT_NULL_EXCEPTION);
        }
        QueryWrapper<UcenterMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile", phone);
        UcenterMember mobileMember = baseMapper.selectOne(queryWrapper);
        if (mobileMember == null) {
            throw new BadException(CodeEnum.PHONE_NOT_REGISTER_EXCEPTION);
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(password, mobileMember.getPassword())) {
            throw new BadException(CodeEnum.PASSWORD_ERROR);
        }
        if (mobileMember.getIsDisabled()) {
            throw new BadException(CodeEnum.USER_FORBID);
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
            throw new BadException(CodeEnum.ACCOUNT_PASSWORD_NICKNAME_CODE_NOT_NULL);
        }
        String phoneCode = redisTemplate.opsForValue().get(mobile); //根据redis的key值，得到code
        if (!code.equals(phoneCode)) {
            throw new BadException(CodeEnum.PHONE_CODE_ERROR_EXCEPTION);
        }
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if (count > 0) {
            throw new BadException(CodeEnum.PHONE_REGISTER);
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
