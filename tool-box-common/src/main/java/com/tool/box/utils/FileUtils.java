package com.tool.box.utils;

import com.tool.box.enums.SystemCodeEnum;
import com.tool.box.exception.InternalApiException;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

/**
 * 附件工具类
 *
 * @Author v_haimiyang
 * @Date 2024/3/20 10:59
 * @Version 1.0
 */
@Slf4j
public class FileUtils {

    public static String imageToBase64(File file) {
        String base64Image = "";
        try {
            Path path = Paths.get(file.getAbsolutePath());
            byte[] imageBytes = Files.readAllBytes(path);
            base64Image = Base64.getEncoder().encodeToString(imageBytes);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new InternalApiException(SystemCodeEnum.IMAGE_TO_BASE64_EXCEPTION);
        }
        return base64Image;
    }
}
