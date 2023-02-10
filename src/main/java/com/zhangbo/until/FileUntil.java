package com.zhangbo.until;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class FileUntil {

    private static final String purchase_filepath = "E:\\vueprojet\\vue_admin\\vue_admin\\src\\purchasefile\\";

    /**
     * 上传
     * @param file
     * @return
     */
    public static  Result  file_upload(MultipartFile file){
        if (file.isEmpty()) {
            return Result.resultFactory(Status.SERVER_FAIL);
        }
        String filename = file.getOriginalFilename();
        String filePath=purchase_filepath+filename;
        //创建目标文件
        File dest = new File(filePath);
        try {
            file.transferTo(dest);
            return Result.resultFactory(Status.SUBMIT_SUCCESS,filePath);
        } catch (IOException e) {
            return Result.resultFactory(Status.SERVER_FAIL);
        }
    }

    /**
     * 下载
     * 由于HTTP头部的默认编码为ISO-8859-1而我们上传文件于下载文件过程中，提取到的文件名都要通过HTTP头部。
     *所以我们需要在上传的时候对提取到的文件名进行转码为UTF-8，然后在下载时我们也要进行反向转码为ISO-8859-1.
     * @param res
     * @param filepath
     */
    public static Result file_download(HttpServletResponse res, String filepath) throws UnsupportedEncodingException {
        File file=new File(filepath);
        String fileName = file.getName();

        res.setHeader("content-type", "application/octet-stream");
        res.setContentType("application/octet-stream");
        res.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("utf-8"),"iso-8859-1"));

        byte[] buff = new byte[1024];
        FileInputStream bis = null;
        OutputStream os = null;
        try {
            os = res.getOutputStream();
            bis = new FileInputStream(file);
            int readTmp = 0;
            while ((readTmp = bis.read(buff)) != -1) {
                //并不是每次都能读到1024个字节，所有用readTmp作为每次读取数据	的长度，否则会出现文件损坏的错误
                os.write(buff, 0, readTmp);

            }
              return Result.resultFactory(Status.OPERATION_SUCCESS);
        } catch (IOException e) {
            e.printStackTrace();
            return Result.resultFactory(Status.SERVER_FAIL);
        } finally {
            if (bis != null) try {
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static Result purchase_file_delete(String filePath){
        if (FileSystemUtils.deleteRecursively(new File(filePath))){
            return Result.resultFactory(Status.DELETE_INFO_SUCCESS);
        }else {
            return Result.resultFactory(Status.DELETE_FAIL);
        }
    }


}
