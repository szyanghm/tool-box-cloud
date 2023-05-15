package com.tool.box.utils.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

/**
 * mybatis-plus代码生成工具类
 *
 * @Author v_haimiyang
 * @Date 2022/10/8 15:30
 * @Version 1.0
 */
public class GeneratorUtils {

    /**
     * 数据库连接地址
     */
    private final static String MYSQL_URL = "jdbc:mysql://localhost:3306/tool_box?serverTimezone=UTC&characterEncoding=utf-8&useSSL=false";
    /**
     * 数据库账户
     */
    private final static String MYSQL_ACCOUNT = "root";
    /**
     * 数据库密码
     */
    private final static String MYSQL_PASSWORD = "123456";
    /**
     * 作者
     */
    private final static String AUTHOR = "v_haimiyang";
    /**
     * 代码生成的路径
     */
    private final static String PACKAGE_ADDR = "D:\\my_project\\tool-box-cloud\\tool-box-common\\src\\main\\java\\com\\tool\\box\\utils\\generator\\test";
    /**
     * xxMapper.xml文件生成的路径
     */
    private final static String XML_ADDR = "D:\\my_project\\tool-box-cloud\\tool-box-common\\src\\main\\java\\com\\tool\\box\\utils\\generator\\mapper";
    public static void main(String[] args) {
        FastAutoGenerator.create(MYSQL_URL, MYSQL_ACCOUNT, MYSQL_PASSWORD)
                .globalConfig(builder -> {
                    builder.author(AUTHOR) // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir(PACKAGE_ADDR); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.tool.box") // 设置父包名
                            //.moduleName("system") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, XML_ADDR)); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("t_test") // 设置需要生成的表名
                            .addTablePrefix("t_")
                            .entityBuilder().addIgnoreColumns("id", "create_by", "create_time", "update_by", "update_time", "is_delete")
                    ; // 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
