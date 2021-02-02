package com.lcc.vod.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClient;
import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.CreateUploadVideoRequest;
import com.aliyuncs.vod.model.v20170321.CreateUploadVideoResponse;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.lcc.servicebase.exceptionhandler.BadException;
import com.lcc.servicebase.exceptionhandler.CodeEnum;
import com.lcc.vod.service.VodService;
import com.lcc.vod.utils.AliyunConstantUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import static com.aliyun.vod.upload.common.Util.decodeBase64;
import static com.lcc.vod.vo.InitVodClient.initVodClient;

/**
 * <p>
 * 阿里云视频服务类
 * </p>
 *
 * @author chaochao
 * @since 2020-05-30
 */
@Slf4j
@Service
public class VodServiceImpl implements VodService {

    @Override
    public String uploadVideoAly(MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            //title：上传之后显示名称
            assert fileName != null;
            String title = fileName.substring(0, fileName.lastIndexOf("."));
            //inputStream：上传文件输入流
            InputStream inputStream = file.getInputStream();
            UploadStreamRequest request = new UploadStreamRequest(AliyunConstantUtils.ACCESS_KEY_ID,
                    AliyunConstantUtils.ACCESS_KEY_SECRET, title, fileName, inputStream);
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);

            String videoId = null;
            if (response.isSuccess()) {
                videoId = response.getVideoId();
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。
                // 其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                videoId = response.getVideoId();
            }
            return videoId;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void removeMoreAlyVideo(List<String> videoIdList) {
        try {
            DefaultAcsClient client = initVodClient(AliyunConstantUtils.ACCESS_KEY_ID, AliyunConstantUtils.ACCESS_KEY_SECRET);
            DeleteVideoRequest request = new DeleteVideoRequest();
            //利用apache包下的工具类，把集合转换成用逗号隔开的字符串形式 如："1,2,3"
            String ids = StringUtils.join(videoIdList.toArray(), ",");
            request.setVideoIds(ids); // 阿里云sdk方法是中间用逗号隔开
            client.getAcsResponse(request);
        } catch (Exception e) {
            throw new BadException(CodeEnum.DELETED_VIDEO_FAILED);
        }
    }

    @Override
    public String uploadVideoAlyByOSS(String localFile) {
        try {
            // 初始化VOD客户端并获取上传地址和凭证
            DefaultAcsClient vodClient = initVodClient(AliyunConstantUtils.ACCESS_KEY_ID, AliyunConstantUtils.ACCESS_KEY_SECRET);
            CreateUploadVideoResponse createUploadVideoResponse = createUploadVideo(vodClient);

            // 执行成功会返回VideoId、UploadAddress和UploadAuth
            String videoId = createUploadVideoResponse.getVideoId();

            JSONObject uploadAuth = JSONObject.parseObject(decodeBase64(createUploadVideoResponse.getUploadAuth()));
            JSONObject uploadAddress = JSONObject.parseObject(decodeBase64(createUploadVideoResponse.getUploadAddress()));

            // 使用UploadAuth和UploadAddress初始化OSS客户端
            OSSClient ossClient = initOssClient(uploadAuth, uploadAddress);
            // 上传文件，注意是同步上传会阻塞等待，耗时与文件大小和网络上行带宽有关
            uploadLocalFile(ossClient, uploadAddress, localFile);

            log.info("Put local file succeed, VideoId : " + videoId);
            return videoId;
        } catch (Exception e) {
            throw new BadException(CodeEnum.UPLOAD_VIDEO_FAILED);
        }
    }

    /**
     * 获取视频上传地址和凭证
     *
     * @param vodClient
     * @return
     */
    private CreateUploadVideoResponse createUploadVideo(DefaultAcsClient vodClient) throws com.aliyuncs.exceptions.ClientException {
        CreateUploadVideoRequest request = new CreateUploadVideoRequest();
        request.setFileName("xxxtest.mp4");
        request.setTitle("基于OSS上传视频");
        //设置请求超时时间
        request.setSysReadTimeout(1000);
        request.setSysConnectTimeout(1000);
        return vodClient.getAcsResponse(request);
    }

    /**
     * 使用上传凭证和地址初始化OSS客户端（注意需要先Base64解码并Json Decode再传入）
     *
     * @param uploadAuth
     * @param uploadAddress
     * @return
     */
    private OSSClient initOssClient(JSONObject uploadAuth, JSONObject uploadAddress) {
        String endpoint = uploadAddress.getString("Endpoint");
        String accessKeyId = uploadAuth.getString("AccessKeyId");
        String accessKeySecret = uploadAuth.getString("AccessKeySecret");
        String securityToken = uploadAuth.getString("SecurityToken");
        return new OSSClient(endpoint, accessKeyId, accessKeySecret, securityToken);
    }

    /**
     * 上传本地文件
     *
     * @param ossClient
     * @param uploadAddress
     * @param localFile
     */
    private void uploadLocalFile(OSSClient ossClient, JSONObject uploadAddress, String localFile) {
        String bucketName = uploadAddress.getString("Bucket");
        String objectName = uploadAddress.getString("FileName");
        File file = new File(localFile);
        ossClient.putObject(bucketName, objectName, file);
    }
}
