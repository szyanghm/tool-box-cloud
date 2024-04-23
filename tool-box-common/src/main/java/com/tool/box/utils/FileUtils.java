package com.tool.box.utils;

import cn.hutool.core.text.StrPool;
import com.tool.box.enums.SystemCodeEnum;
import com.tool.box.exception.InternalApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
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

    /**
     * java获取文件的md5值
     *
     * @param fis 输入流
     * @return hash值
     */
    public static String md5HashCode(InputStream fis) {
        try {
            //拿到一个MD5转换器,如果想使用SHA-1或SHA-256，则传入SHA-1,SHA-256
            MessageDigest md = MessageDigest.getInstance("MD5");

            //分多次将一个文件读入，对于大型文件而言，比较推荐这种方式，占用内存比较少。
            byte[] buffer = new byte[1024];
            int length = -1;
            while ((length = fis.read(buffer, 0, 1024)) != -1) {
                md.update(buffer, 0, length);
            }
            fis.close();
            //转换并返回包含16个元素字节数组,返回数值范围为-128到127
            byte[] md5Bytes = md.digest();
            BigInteger bigInt = new BigInteger(1, md5Bytes);//1代表绝对值
            return bigInt.toString(16);//转换为16进制
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * MultipartFile转File
     *
     * @param file MultipartFile
     * @return File
     */
    public static File multipartFileToFile(MultipartFile file) {
        File convFile;
        if (file.equals("") || file.getSize() <= 0) {
            throw new InternalApiException(SystemCodeEnum.FILE_IS_EMPTY_OR_ITS_SIZE_IS_ZERO);
        }
        try {
            InputStream inputStream = file.getInputStream();
            convFile = new File(file.getOriginalFilename());
            FileOutputStream fos = new FileOutputStream(convFile);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
            fos.close();
            inputStream.close();
        } catch (IOException e) {
            log.error(SystemCodeEnum.MULTIPART_FILE_TO_FILE_EXCEPTION.getMessage(), StrPool.COLON, e.getMessage());
            throw new InternalApiException(SystemCodeEnum.MULTIPART_FILE_TO_FILE_EXCEPTION);
        }
        return convFile;
    }
}
