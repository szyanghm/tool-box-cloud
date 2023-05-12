package com.tool.box.utils.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

/**
 * @Author v_haimiyang
 * @Date 2022/10/8 15:30
 * @Version 1.0
 */
public class GeneratorUtils {

    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/tool_box?serverTimezone=UTC&characterEncoding=utf-8&useSSL=false", "root", "123456")
                .globalConfig(builder -> {
                    builder.author("v_haimiyang") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("D:\\my_project\\tool-box-cloud\\tool-box-common\\src\\main\\java\\com\\tool\\box\\utils\\generator\\test"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.tool.box") // 设置父包名
                            //.moduleName("system") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "D:\\my_project\\tool-box-cloud\\tool-box-common\\src\\main\\java\\com\\tool\\box\\utils\\generator\\test")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("t_test") // 设置需要生成的表名
                            .addTablePrefix("t_")
                            .entityBuilder().addIgnoreColumns("id", "create_by", "create_time", "update_by", "update_time","is_delete")
                    ; // 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
