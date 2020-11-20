package com.lcc.vod;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.vod.model.v20170321.CreateUploadVideoRequest;
import com.aliyuncs.vod.model.v20170321.CreateUploadVideoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;

import java.io.File;

import static com.aliyun.vod.upload.common.Util.decodeBase64;

/**
 * @Author Administrator
 * @Description 基于OSS上传视频到视频点播
 * @Date 2020/8/17  11:04
 */
public class TestVodUploadByOSS {


    /**
     * 使用AK初始化VOD客户端
     * @param accessKeyId
     * @param accessKeySecret
     * @return
     * @throws ClientException
     */
    public static DefaultAcsClient initVodClient(String accessKeyId, String accessKeySecret) throws ClientException {
        String regionId = "cn-shanghai";  // 点播服务接入区域  固定写法
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        return client;
    }


    /**
     * 根据视频iD获取视频播放凭证
     * @throws Exception
     */
    public static void getPlayAuth() throws Exception{

        DefaultAcsClient client = InitObject.initVodClient("LTAI4G3qQbaBSR4bNMuTcX3f",
                "hGsyTJZhAdacvohIg2JI8DFKAg9NP0");
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
        request.setVideoId("1c475f6c825e4cc1afc24f25c1464c1e");
        response = client.getAcsResponse(request);
        System.out.println("playAuth:"+response.getPlayAuth());
    }

    /**
     * 使用上传凭证和地址初始化OSS客户端（注意需要先Base64解码并Json Decode再传入）
     * @param uploadAuth
     * @param uploadAddress
     * @return
     */
    public static OSSClient initOssClient(JSONObject uploadAuth, JSONObject uploadAddress) {
        String endpoint = uploadAddress.getString("Endpoint");
        String accessKeyId = uploadAuth.getString("AccessKeyId");
        String accessKeySecret = uploadAuth.getString("AccessKeySecret");
        String securityToken = uploadAuth.getString("SecurityToken");
        return new OSSClient(endpoint, accessKeyId, accessKeySecret, securityToken);
    }

    /**
     * 上传本地文件
     * @param ossClient
     * @param uploadAddress
     * @param localFile
     */
    public static void uploadLocalFile(OSSClient ossClient, JSONObject uploadAddress, String localFile){
        String bucketName = uploadAddress.getString("Bucket");
        String objectName = uploadAddress.getString("FileName");
        File file = new File(localFile);
        ossClient.putObject(bucketName, objectName, file);
    }

    /**
     * 获取视频上传地址和凭证
     * @param vodClient
     * @return
     */
    public static CreateUploadVideoResponse createUploadVideo(DefaultAcsClient vodClient) throws com.aliyuncs.exceptions.ClientException {
        CreateUploadVideoRequest request = new CreateUploadVideoRequest();
        request.setFileName("vod_test_xxx.mp4");
        request.setTitle("基于OSS上传视频");
        //request.setDescription("this is desc");
        //request.setTags("tag1,tag2");
        //request.setCoverURL("http://vod.aliyun.com/test_cover_url.jpg");
        //request.setCateId(-1L);
        //request.setTemplateGroupId("");
        //request.setWorkflowId("");
        //request.setStorageLocation("");
        //request.setAppId("app-1000000");
        //设置请求超时时间
        request.setSysReadTimeout(1000);
        request.setSysConnectTimeout(1000);
        return vodClient.getAcsResponse(request);
    }

    public static void main(String[] args) {
        //您的AccessKeyId
        String accessKeyId = "LTAI4G3qQbaBSR4bNMuTcX3f";
        //您的AccessKeySecret
        String accessKeySecret = "hGsyTJZhAdacvohIg2JI8DFKAg9NP0";
        //需要上传到VOD的本地视频文件的完整路径，需要包含文件扩展名
        String localFile = "G:\\java_document_file\\2020.5在线教育网站资料\\项目资料\\1-阿里云上传测试视频\\1234.mp4";
        try {
            // 初始化VOD客户端并获取上传地址和凭证
            DefaultAcsClient vodClient = initVodClient(accessKeyId, accessKeySecret);
            CreateUploadVideoResponse createUploadVideoResponse = createUploadVideo(vodClient);

            // 执行成功会返回VideoId、UploadAddress和UploadAuth
            String videoId = createUploadVideoResponse.getVideoId();

            JSONObject uploadAuth = JSONObject.parseObject(decodeBase64(createUploadVideoResponse.getUploadAuth()));
            JSONObject uploadAddress = JSONObject.parseObject(decodeBase64(createUploadVideoResponse.getUploadAddress()));

            // 使用UploadAuth和UploadAddress初始化OSS客户端
            OSSClient ossClient = initOssClient(uploadAuth, uploadAddress);
            // 上传文件，注意是同步上传会阻塞等待，耗时与文件大小和网络上行带宽有关
            uploadLocalFile(ossClient, uploadAddress, localFile);

            System.out.println("Put local file succeed, VideoId : " + videoId);
            System.out.println("Put local file succeed, uploadAuth : "+uploadAuth);
            System.out.println("Put local file succeed, uploadAddress : "+uploadAddress);
        } catch (Exception e) {
            System.out.println("Put local file fail, ErrorMessage : " + e.getLocalizedMessage());
        }
    }


}