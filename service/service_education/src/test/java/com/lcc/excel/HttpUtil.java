package com.lcc.excel;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lcc.servicebase.exceptionhandler.BadException;
import com.lcc.servicebase.exceptionhandler.CodeEnum;
import com.lcc.util.MD5;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Administrator
 * @Description 测试调用远程http接口，思考乐
 * @Date 2020/9/24  11:15
 */
public class HttpUtil {

    public static void main(String[] args) {
        JSONObject bodyJson = new JSONObject();
        LocalDateTime now = LocalDateTime.now();
        bodyJson.put("startTime", "2020-09-20");
        bodyJson.put("rootId", "skledu");
        JSONObject headJson = new JSONObject();
        long time = System.currentTimeMillis();
        String md5Str = "body=" + bodyJson.toJSONString() + "&timestamp=" + time + "&secret=skledu_ac899f73f1c945cca276a166799c7968";
        headJson.put("sign", MD5.getMD5String(md5Str));
        headJson.put("timestamp", String.valueOf(time));
        System.out.println(headJson.toJSONString());
        System.out.println("-------结束--------");
        String url = "http://hr.skledu.com:8081/RedseaPlatform/openapi/v1/organization/getArganizationAllInfo.mob";

        String sendPostStr = sendPost(url, headJson.toJSONString(), bodyJson.toJSONString());

        JSONObject jsonObject = JSON.parseObject(sendPostStr);
        JSONArray data = (JSONArray) jsonObject.get("data");
        List<String> list = JSONArray.parseArray(data.toJSONString(), String.class);
        for (String s : list) {
            System.out.println(s);
        }

    }

    public static String sendPost(String URL, String headjson, String bodyjson) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(URL);
        post.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        try {
            List<NameValuePair> nvps = new ArrayList<>();
            nvps.add(new BasicNameValuePair("head", headjson));
            nvps.add(new BasicNameValuePair("body", bodyjson));
            post.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
            // 发送请求
            HttpResponse httpResponse = client.execute(post);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return EntityUtils.toString(httpResponse.getEntity(), "utf-8");
            } else {
                throw new BadException(CodeEnum.OPERATE_EXCEPTION);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
