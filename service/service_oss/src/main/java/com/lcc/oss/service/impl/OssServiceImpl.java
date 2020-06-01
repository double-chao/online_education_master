package com.lcc.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.lcc.oss.service.OssService;
import com.lcc.oss.utils.ConstantPropertiesUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

/**
 * @Author: 一十六
 * @Description: 上传文件
 * @Date: 2020/5/27 15:06
 */
@Service
public class OssServiceImpl implements OssService {

    //上传头像到oss
    @Override
    public String uploadFileAvatar(MultipartFile file) {
        // OSS中的值
        String endpoint = ConstantPropertiesUtil.END_POINT; //地域节点
        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID; // id
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET; // 秘钥
        String bucketName = ConstantPropertiesUtil.BUCKET_NAME; // 存储节点名字

        try {
            // 创建OSS实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            //获取上传文件输入流
            InputStream inputStream = file.getInputStream();
            //获取文件名称
            String fileName = file.getOriginalFilename();
            //1 在文件名称里面添加随机唯一的值 顺便把 "-" 替换掉
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            // 拼接uuid
            fileName = uuid + fileName;
            //2 把文件按照日期进行分类
            //获取当前日期
            String datePath = new DateTime().toString("yyyy/MM/dd"); //用日期成文件夹路径
            //拼接日期
            fileName = datePath + "/" + fileName;
            //调用oss方法实现上传
            //第一个参数  Bucket名称
            //第二个参数  上传到oss文件路径和文件名称   aa/bb/xxx.jpg
            //第三个参数  上传文件输入流
            ossClient.putObject(bucketName, fileName, inputStream);
            // 关闭OSSClient。
            ossClient.shutdown();
            //把上传之后文件路径返回
            //需要把上传到阿里云oss路径手动拼接出来
            //https://edu-online-chao.oss-cn-shenzhen.aliyuncs.com/xxx.jpg ,在oos控制配置中查看文件存储的路径格式
            String url = "https://" + bucketName + "." + endpoint + "/" + fileName;
            return url;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
