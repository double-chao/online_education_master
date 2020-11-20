package com.lcc.educenter.service;

import com.lcc.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lcc.educenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author chaochao
 * @since 2020-05-31
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    /**
     *  登录
     * @param member
     * @return
     */
    String login(UcenterMember member);

    /**
     *  注册
     * @param registerVo
     */
    void register(RegisterVo registerVo);

    /**
     * 根据微信id获取用户信息
     * @param openid
     * @return
     */
    UcenterMember getOpenIdMember(String openid);

    /**
     * 统计一天注册人数
     * @param day
     * @return
     */
    Integer countRegisterDay(String day);
}
