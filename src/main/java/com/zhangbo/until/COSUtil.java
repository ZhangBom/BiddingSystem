package com.zhangbo.until;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.DeleteObjectRequest;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.region.Region;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Random;

public class COSUtil {

    // 存储桶名称
    private static final String BUCKET_NAME = "zhangbo-1306119450";
    //secretId 秘钥id
    private static final String SECRET_ID = "AKID9Jya44zpfVJiz8JLI9sSpW6Wmy7AA6vb";
    //SecretKey 秘钥
    private static final String SECRET_KEY = "oRmvhMxQmz6SddS2Wq2OwLZcanvvpXAb";
    // 访问域名
    public static final String URL = "https://zhangbo-1306119450.cos.ap-chengdu.myqcloud.com/";
    // 创建COS 凭证
    private static final COSCredentials credentials = new BasicCOSCredentials(SECRET_ID, SECRET_KEY);
    // 配置 COS 区域 就购买时选择的区域 我这里是 成都（chengdu）
    private static final ClientConfig clientConfig = new ClientConfig(new Region("ap-chengdu"));

    /**
     * 接收文件，要存储文件的文件夹路径
     * @param file
     * @param FOLDER
     * @return
     */
    public static String uploadfile(MultipartFile file, String FOLDER) {
        // 创建 COS 客户端连接
        COSClient cosClient = new COSClient(credentials, clientConfig);
        //生成文件名
        String fileName = file.getOriginalFilename();
        try {
            String substring = fileName.substring(fileName.lastIndexOf("."));
            File localFile = File.createTempFile(String.valueOf(System.currentTimeMillis()), substring);
            file.transferTo(localFile);
            Random random = new Random();
            fileName = FOLDER + random.nextInt(10000) + System.currentTimeMillis() + substring;
            // 将 文件上传至 COS
            PutObjectRequest objectRequest = new PutObjectRequest(BUCKET_NAME, fileName, localFile);
            cosClient.putObject(objectRequest);
            return URL+fileName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            cosClient.shutdown();
        }
    }

    /**
     * 接收文件路径拼接后执行删除
     *
     * @param filepath
     * @return
     */
    public static boolean deletefile(String filepath) {
        // 创建 COS 客户端连接
        COSClient cosClient = new COSClient(credentials, clientConfig);
        try {
            // 指定要删除的 bucket 和路径
            filepath= StringUtils.substringAfter(filepath, URL);
            cosClient.deleteObject(BUCKET_NAME, filepath);
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            cosClient.shutdown();
        }
    }
}