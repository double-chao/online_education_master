package com.lcc.vod.vo;

import com.aliyun.oss.ClientException;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;
/**
 * <p>
 * 阿里云客户端初始化
 * </p>
 *
 * @author chaochao
 * @since 2020-05-30
 */
public class InitVodClient {

    public static DefaultAcsClient initVodClient(String accessKeyId, String accessKeySecret) throws ClientException {
        String regionId = "cn-shanghai";  // 点播服务接入区域  固定写法
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        return client;
    }
}
