package com.lcc.educenter.mapper;

import com.lcc.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author chaochao
 * @since 2020-05-31
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    /**
     * 统计某天注册人数
     *
     *  ！！！ 注意：当参数不止一个是 如 String day,String id. 在配置文件中应该怎么取参数呢？
     *             在参数前加注解@Parm("day") ,配置文件中就可以根据注解中的值来取参数。
     *             或者可以根据下标来取，比如在配置文件中#{0}代表取第一个，#{1}代表取第二个
     * @param day
     * @return
     */
    Integer countRegisterByDay(String day);
}
