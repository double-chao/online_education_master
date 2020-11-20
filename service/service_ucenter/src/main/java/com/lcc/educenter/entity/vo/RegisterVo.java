package com.lcc.educenter.entity.vo;

import com.lcc.servicebase.valid.AddGroup;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
public class RegisterVo {

    @ApiModelProperty(value = "昵称")
    @NotEmpty(message = "昵称不能为空", groups = {AddGroup.class})
    @Length(min = 6, max = 16, message = "昵称只能在6-16位之间", groups = {AddGroup.class})
    private String nickname;

    @ApiModelProperty(value = "手机号")
    @NotEmpty(message = "手机号码不能为空", groups = {AddGroup.class})
    @Pattern(regexp = "^[1]([3-9])[0-9]{9}$", message = "手机号码格式不正确", groups = {AddGroup.class})
    private String mobile;

    @ApiModelProperty(value = "密码")
    @NotEmpty(message = "密码不能为空", groups = {AddGroup.class})
    @Length(min = 6, max = 16, message = "密码必须为6-16位", groups = {AddGroup.class})
    private String password;

    @ApiModelProperty(value = "验证码")
    @NotEmpty(message = "验证码不能为空", groups = {AddGroup.class})
    private String code;
}
