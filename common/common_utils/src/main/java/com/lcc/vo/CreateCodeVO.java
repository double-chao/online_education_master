package com.lcc.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

/**
 * @Author Administrator
 * @Description 生成代码的VO类
 * @Date 2020/12/9  17:11
 */
@Data
public class CreateCodeVO {

    @ApiModelProperty(value = "项目路径")
    private String path;

    @ApiModelProperty(value = "注释作者名字")
    private String authorName;

    @ApiModelProperty(value = "重新生成时文件是否覆盖")
    private boolean override = false;

    @ApiModelProperty(value = "数据库的url")
    @Value("${spring.datasource.url}")
    private String sqlUrl;

    @ApiModelProperty(value = "数据库的驱动名")
    @Value("${spring.datasource.driver-class-name}")
    private String driverName;

    @ApiModelProperty(value = "数据库的用户名")
    @Value("${spring.datasource.username}")
    private String username;

    @ApiModelProperty(value = "数据库的密码")
    @Value("${spring.datasource.password}")
    private String password;

    @ApiModelProperty(value = "父包模块名")
    private String moduleName;

    @ApiModelProperty(value = "父包名,如果为空,将下面子包名必须写全部,否则就只需写子包名")
    private String parent;

    @ApiModelProperty(value = "数据库的表名")
    private String tableName;

    @ApiModelProperty(value = "去掉生成实体时去掉表前缀")
    private boolean deletePrefix = true;

}
