package com.lcc.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.lcc.servicebase.exceptionhandler.BadException;
import com.lcc.vod.service.VodService;
import com.lcc.vod.utils.AliyunConstantUtils;
import com.lcc.vod.vo.InitVodClient;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * 阿里云视频服务类
 * </p>
 *
 * @author chaochao
 * @since 2020-05-30
 */
@Service
public class VodServiceImpl implements VodService {

    @Override
    public String uploadVideoAly(MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            //title：上传之后显示名称
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
            DefaultAcsClient client = InitVodClient.initVodClient(AliyunConstantUtils.ACCESS_KEY_ID, AliyunConstantUtils.ACCESS_KEY_SECRET);
            DeleteVideoRequest request = new DeleteVideoRequest();
            //利用apache包下的工具类，把集合转换成用逗号隔开的字符串形式 如："1,2,3"
            String ids = StringUtils.join(videoIdList.toArray(), ",");
            request.setVideoIds(ids); // 阿里云sdk方法是中间用逗号隔开
            client.getAcsResponse(request);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadException(20001, "删除视频失败");
        }
    }

}
