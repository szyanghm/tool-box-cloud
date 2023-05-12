package com.tool.box.utils.excel;


import java.util.List;

import cn.hutool.core.io.FileUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;

/**
 * @Author: v_jiaholiu
 * @Date: 2022/9/30
 */
public class ExcelReaderTableDataUtils {

    public static void main(String[] args) {

        ExcelReader reader = ExcelUtil
                .getReader(FileUtil.file("D:\\需求稿\\衍生品管理平台\\衍生品核算数据表.xlsx"),
                        0);//配置路径和sheet页
        int startRow = 1;//表名那行
        int endRow = 46;//逻辑删除那行
        List<List<Object>> read = reader.read(startRow-1,endRow-1);
        String text = "CREATE TABLE `" + read.get(0).get(0) + "` ( `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键ID',";
        for (List<Object> o : read){
            if (o.equals(read.get(0)) || o.equals(read.get(1)) || o.equals(read.get(2))){
                continue;
            }
            if ("VARCHAR".equalsIgnoreCase(String.valueOf(o.get(1))) || "text".equalsIgnoreCase(String.valueOf(o.get(1)))){
                String b = "`" + o.get(0) + "`" + " " + o.get(1) + "(" + o.get(2) + ") " + "COLLATE utf8mb4_bin DEFAULT NULL COMMENT " + "'" + o.get(3) + "',";
                text += b;
            }else if ("datetime".equalsIgnoreCase(String.valueOf(o.get(1))) || "date".equalsIgnoreCase(String.valueOf(o.get(1)))){
                String b = "`" + o.get(0) + "`" + " " + o.get(1) + " " + "DEFAULT NULL COMMENT " + "'" + o.get(3) + "',";
                text += b;
            }else {
                String b = "`" + o.get(0) + "`" + " " + o.get(1) + "(" + o.get(2) + ") " + "DEFAULT NULL COMMENT " + "'" + o.get(3) + "',";
                text += b;
            }

        }
        text += "PRIMARY KEY (`id`) USING BTREE,\n"
                + "  KEY `tenant_id` (`tenant_id`) USING BTREE\n"
                + ") ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='" + read.get(0).get(0) + "';";
        System.err.println(text);
    }

}
