package com.lcc.eduservice.service.impl;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.lcc.eduservice.service.CodeGeneratorService;
import com.lcc.servicebase.exceptionhandler.BadException;
import com.lcc.vo.CreateCodeVO;
import org.springframework.stereotype.Service;

/**
 * @Author Administrator
 * @Description 代码生成器
 * @Date 2020/12/9  17:58
 */
@Service
public class CodeGeneratorServiceImpl implements CodeGeneratorService {

    @Override
    public boolean createCode(CreateCodeVO createCodeVO) {
        try {
            // 1、创建代码生成器
            AutoGenerator mpg = new AutoGenerator();
            // 2、全局配置
            GlobalConfig gc = new GlobalConfig();
            String path = createCodeVO.getPath();
            // 这里采用绝对路径
            gc.setOutputDir(path + "/src/main/java");
            gc.setAuthor(createCodeVO.getAuthorName());  // 类注释作者名字
            gc.setOpen(false); // 生成后是否打开资源管理器
            gc.setFileOverride(createCodeVO.isOverride()); // 重新生成时文件是否覆盖
            gc.setServiceName("%sService"); // 去掉Service接口的首字母I
            gc.setIdType(IdType.AUTO); // 主键策略 主键为自增长
            gc.setDateType(DateType.ONLY_DATE); // 定义生成的实体类中日期类型
            gc.setSwagger2(true); // 开启Swagger2模式
            mpg.setGlobalConfig(gc);

            // 3、数据源配置
            DataSourceConfig dsc = new DataSourceConfig();
            dsc.setUrl(createCodeVO.getSqlUrl());
            dsc.setDriverName(createCodeVO.getDriverName());
            dsc.setUsername(createCodeVO.getUsername());
            dsc.setPassword(createCodeVO.getPassword());
            dsc.setDbType(DbType.MYSQL);
            mpg.setDataSource(dsc);

            // 4、包配置
            PackageConfig pc = new PackageConfig();
            // 包 com.lcc.eduservice
            pc.setModuleName(createCodeVO.getModuleName());
            pc.setParent(createCodeVO.getParent());
            // 包 com.lcc.eduservice.controller
            pc.setController("controller"); // 控制类包名
            pc.setEntity("entity"); // 实体类制包名
            pc.setService("service"); // 服务类包名
            pc.setMapper("mapper"); // 映射类包名
            mpg.setPackageInfo(pc);

            // 5、策略配置
            StrategyConfig strategy = new StrategyConfig();
            strategy.setInclude(createCodeVO.getTableName()); // 数据库表名

            strategy.setNaming(NamingStrategy.underline_to_camel); // 数据库表映射到实体的命名策略
            if (createCodeVO.isDeletePrefix()) {
                strategy.setTablePrefix(pc.getModuleName() + "_"); // 生成实体时去掉表前缀
            }

            strategy.setColumnNaming(NamingStrategy.underline_to_camel); // 数据库表字段映射到实体的命名策略
            strategy.setEntityLombokModel(true); // lombok 模型 @Accessors(chain = true) setter链式操作

            strategy.setRestControllerStyle(true); // restful api风格控制器
            strategy.setControllerMappingHyphenStyle(true); // url中驼峰转连字符

            mpg.setStrategy(strategy);
            // 6、执行
            mpg.execute();
            return true;
        } catch (Exception e) {
            throw new BadException();
        }
    }
}
