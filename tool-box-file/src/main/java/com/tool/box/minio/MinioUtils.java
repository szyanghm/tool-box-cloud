package com.tool.box.minio;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.ObjectUtil;
import com.tool.box.enums.SystemCodeEnum;
import com.tool.box.exception.InternalApiException;
import com.tool.box.utils.SystemUtils;
import com.tool.box.vo.OssFileVO;
import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.DeleteObject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Base64Util;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.stream.Stream;

/**
 * @Author v_haimiyang
 * @Date 2024/3/20 16:12
 * @Version 1.0
 */
@Slf4j
@Component
public class MinioUtils {

    /**
     * MinIO客户端
     */
    @Resource
    private MinioClient client;

    /**
     * 配置类
     */
    @Resource
    private MinioProperties ossProperties;

    /**
     * 格式化时间
     */
    private static final String DATE_FORMAT = "yyyyMMdd";

    /**
     * 字符集
     */
    private static final String ENCODING = "UTF-8";

    /**
     * 存储桶是否存在
     *
     * @param bucketName 存储桶名称
     * @return boolean
     */
    public boolean bucketExists(String bucketName) {
        try {
            return client.bucketExists(BucketExistsArgs.builder().bucket(getBucketName(bucketName)).build());
        } catch (Exception e) {
            log.error("minio bucketExists Exception:{}", e);
        }
        return false;
    }

    /**
     * @Description: 创建 存储桶
     * @Param bucketName: 存储桶名称
     * @Date: 2023/8/2 11:28
     */
    public void makeBucket(String bucketName) {
        try {
            if (!client.bucketExists(BucketExistsArgs.builder().bucket(getBucketName(bucketName)).build())) {
                client.makeBucket(MakeBucketArgs.builder().bucket(getBucketName(bucketName)).build());
                log.info("minio makeBucket success bucketName:{}", bucketName);
            }
        } catch (Exception e) {
            log.error("minio makeBucket Exception:{}", e);
        }
    }

    /**
     * 获取文件信息
     *
     * @param fileName 存储桶文件名称
     * @return InputStream
     */
    public OssFileVO getOssInfo(String fileName) {
        try {
            StatObjectResponse stat = client.statObject(
                    StatObjectArgs.builder().bucket(getBucketName(ossProperties.getBucketName())).object(fileName)
                            .build());
            OssFileVO ossFile = new OssFileVO();
            ossFile.setName(ObjectUtil.isEmpty(stat.object()) ? fileName : stat.object());
            ossFile.setFilePath(ossFile.getName());
            ossFile.setDomain(getOssHost(ossProperties.getBucketName()));
            ossFile.setHash(String.valueOf(stat.hashCode()));
            ossFile.setSize(stat.size());
            ossFile.setPutTime(DateUtil.date(stat.lastModified().toLocalDateTime()));
            ossFile.setContentType(stat.contentType());
            return ossFile;
        } catch (Exception e) {
            log.error(SystemUtils.getErrorMsg(SystemCodeEnum.FILE_UPLOAD_FAILED.getMsg(), e.getMessage()));
        }
        return null;
    }

    /**
     * 上传文件
     *
     * @param folderName 上传的文件夹名称
     * @param fileName   上传文件名
     * @param file       上传文件类
     * @return BladeFile
     */
    @SneakyThrows
    public OssFileVO upLoadFile(String folderName, String fileName, MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new InternalApiException(SystemCodeEnum.SYSTEM_BUSY);
        }
        // 文件大小
        if (file.getSize() > 5 * 1024 * 1024) {
            throw new InternalApiException(SystemCodeEnum.UPLOADING_FILES_CANNOT_EXCEED_5M);
        }
        String suffix = getFileExtension(file.getOriginalFilename());
        // 文件后缀判断
        if (!CollUtil.contains(ossProperties.getFileExt(), suffix)) {
            throw new InternalApiException(SystemCodeEnum.FILE_TYPE_NOT);
        }
        try {
            return upLoadFile(folderName, fileName, suffix, file.getInputStream());
        } catch (Exception e) {
            log.error(SystemUtils.getErrorMsg(SystemCodeEnum.FILE_UPLOAD_FAILED.getMsg(), e.getMessage()));
            throw new InternalApiException(SystemCodeEnum.FILE_UPLOAD_FAILED);
        }
    }

    /**
     * 获取文件后缀名
     *
     * @param fullName 文件全名
     * @return {String}
     */
    public static String getFileExtension(String fullName) {
        Assert.notNull(fullName, "minio file fullName is null.");
        String fileName = new File(fullName).getName();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }

    /**
     * 上传文件
     *
     * @param folderName 上传的文件夹名称
     * @param fileName   存储桶对象名称
     * @param suffix     文件后缀名
     * @param stream     文件流
     * @return BladeFile
     */
    public OssFileVO upLoadFile(String folderName, String fileName, String suffix, InputStream stream) {
        try {
            return upLoadFile(ossProperties.getBucketName(), folderName, fileName, suffix, stream,
                    "application/octet" + "-stream");
        } catch (Exception e) {
            log.error("minio upLoadFile Exception:{}", e);
        }
        return null;
    }

    /**
     * @Description: 上传文件
     * @Param bucketName: 存储桶名称
     * @Param folderName: 上传的文件夹名称
     * @Param fileName: 上传文件名
     * @Param suffix: 文件后缀名
     * @Param stream: 文件流
     * @Param contentType: 文件类型
     */
    @SneakyThrows
    public OssFileVO upLoadFile(String bucketName, String folderName, String fileName, String suffix, InputStream stream,
                                String contentType) {
        if (!bucketExists(bucketName)) {
            log.info("minio bucketName is not creat");
            makeBucket(bucketName);
        }
        OssFileVO file = new OssFileVO();
        String originalName = fileName;
        String filePath = getFilePath(folderName, fileName, suffix);
        client.putObject(PutObjectArgs.builder().bucket(getBucketName(bucketName)).object(filePath)
                .stream(stream, stream.available(), -1).contentType(contentType).build());
        file.setOriginalName(originalName);
        file.setName(filePath);
        file.setDomain(getOssHost(bucketName));
        file.setFilePath(filePath);
        stream.close();
        log.info("minio upLoadFile success, filePath:{}", filePath);
        return file;
    }

    /**
     * 删除文件
     *
     * @param fileName 存储桶对象名称
     */
    public boolean removeFile(String fileName) {
        try {
            client.removeObject(
                    RemoveObjectArgs.builder().bucket(getBucketName(ossProperties.getBucketName())).object(fileName)
                            .build());
            log.info("minio removeFile success, fileName:{}", fileName);
            return true;
        } catch (Exception e) {
            log.error("minio removeFile fail, fileName:{}, Exception:{}", fileName, e);
        }
        return false;
    }

    /**
     * 批量删除文件
     *
     * @param fileNames 存储桶对象名称集合
     */
    public boolean removeFiles(List<String> fileNames) {
        try {
            Stream<DeleteObject> stream = fileNames.stream().map(DeleteObject::new);
            client.removeObjects(RemoveObjectsArgs.builder().bucket(getBucketName(ossProperties.getBucketName()))
                    .objects(stream::iterator).build());
            log.info("minio removeFiles success, fileNames:{}", fileNames);
            return true;
        } catch (Exception e) {
            log.error("minio removeFiles fail, fileNames:{}, Exception:{}", fileNames, e);
        }
        return false;
    }

    /**
     * @Description: 下载文件
     * @Param response: 响应
     * @Param fileName: 文件名
     * @Param filePath: 文件路径
     */
    public void downloadFile(HttpServletResponse response, String fileName, String filePath) {
        GetObjectResponse is = null;
        try {
            GetObjectArgs getObjectArgs =
                    GetObjectArgs.builder().bucket(ossProperties.getBucketName()).object(filePath)
                            .build();
            is = client.getObject(getObjectArgs);
            // 设置文件ContentType类型，这样设置，会自动判断下载文件类型
            response.setContentType("application/x-msdownload");
            response.setCharacterEncoding(ENCODING);
            // 设置文件头：最后一个参数是设置下载的文件名并编码为UTF-8
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, ENCODING));
            IoUtil.copy(is, response.getOutputStream());
            log.info("minio downloadFile success, filePath:{}", filePath);
        } catch (Exception e) {
            log.error("minio downloadFile Exception:{}", e);
        } finally {
            IoUtil.close(is);
        }
    }

    /**
     * 获取文件外链
     *
     * @param bucketName bucket名称
     * @param fileName   文件名称
     * @param expires    过期时间
     * @return url
     */
    public String getUrl(String bucketName, String fileName, Integer expires) {
        String link = "";
        try {
            link = client.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder().method(Method.GET).bucket(getBucketName(bucketName))
                            .object(fileName).expiry(expires).build());
        } catch (Exception e) {
            log.error("minio getPresignedObjectUrl is fail, fileName:{}", fileName);
        }
        return link;
    }

    /**
     * 根据规则生成存储桶名称规则
     *
     * @param bucketName 存储桶名称
     * @return String
     */
    private String getBucketName(String bucketName) {
        return bucketName;
    }

    /**
     * 根据规则生成文件路径
     *
     * @param folderName       上传的文件夹名称
     * @param originalFilename 原始文件名
     * @param suffix           文件后缀名
     * @return string 上传的文件夹名称/yyyyMMdd/原始文件名_时间戳.文件后缀名
     */
    private String getFilePath(String folderName, String originalFilename, String suffix) {
        return StrPool.SLASH + String.join(StrPool.SLASH, folderName, DateUtil.date().toString(DATE_FORMAT),
                originalFilename) + StrPool.C_UNDERLINE + DateUtil.current() + StrPool.DOT + suffix;
    }

    /**
     * 获取域名
     *
     * @param bucketName 存储桶名称
     * @return String
     */
    public String getOssHost(String bucketName) {
        return ossProperties.getEndpoint() + StrPool.SLASH + getBucketName(bucketName);
    }

    public static void main(String[] args) {
        String str = Base64Util.encode("http://127.0.0.1:9000/mini-tool//test/20240325/1711350880483_1711350880772.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=minioadmin%2F20240326%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20240326T014725Z&X-Amz-Expires=50000&X-Amz-SignedHeaders=host&X-Amz-Signature=115f9d25cd77959f5011c44e5c36bf05162bddec5228867cda99b5ffb41ba277");
        System.out.println(str);
    }

}
