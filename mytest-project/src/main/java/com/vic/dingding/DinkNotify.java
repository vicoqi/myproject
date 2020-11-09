package com.vic.dingding;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import com.taobao.api.ApiException;
import org.apache.commons.codec.binary.Base64;
import org.junit.Before;
import org.junit.Test;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;


/**
 * @author: wangqp
 * @create: 2020-11-02 17:34
 */
public class DinkNotify {

    private static String timeAndSign = null;

    @Before
    public void sign() throws Exception {
        StringBuffer result = new StringBuffer("&timestamp=");
        Long timestamp = System.currentTimeMillis();
        result.append(timestamp).append("&sign=");
        String secret = "SECf2b70ca8b507e2ae1e7cf476f3e75fadb8ee869ab34bed495eb361d5155c0865";

        String stringToSign = timestamp + "\n" + secret;
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
        byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
        String sign = URLEncoder.encode(new String(Base64.encodeBase64(signData)),"UTF-8");
        System.out.println(sign);
        result.append(sign);
        timeAndSign = result.toString();
    }

    @Test
    public void send() throws ApiException {
        String url = "https://oapi.dingtalk.com/robot/send?access_token=cad451762de13cc3c67275fe9e939ea3f58cca657a3cac94faedb220952df0a7"+timeAndSign;
        DingTalkClient client = new DefaultDingTalkClient(url);
        System.out.println(url);

        OapiRobotSendRequest request = new OapiRobotSendRequest();
        request.setMsgtype("text");
        OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
        text.setContent("测试文本消息");
        request.setText(text);

        OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
        //被@人的手机号（在content里添加@人的手机号）
        //at.setAtMobiles(Arrays.asList("132xxxxxxxx"));
        //isAtAll 是否@所有人
        at.setIsAtAll(true);
        request.setAt(at);

//        request.setMsgtype("link");
//        OapiRobotSendRequest.Link link = new OapiRobotSendRequest.Link();
//        link.setMessageUrl("https://www.dingtalk.com/");
//        link.setPicUrl("");
//        link.setTitle("时代的火车向前开");
//        link.setText("这个即将发布的新版本，创始人xx称它为红树林。而在此之前，每当面临重大升级，产品经理们都会取一个应景的代号，这一次，为什么是红树林");
//        request.setLink(link);

//        request.setMsgtype("markdown");
//        OapiRobotSendRequest.Markdown markdown = new OapiRobotSendRequest.Markdown();
//        markdown.setTitle("杭州天气");
//        markdown.setText("#### 杭州天气 @156xxxx8827\n" +
//                "> 9度，西北风1级，空气良89，相对温度73%\n\n" +
//                "> ![screenshot](https://gw.alicdn.com/tfs/TB1ut3xxbsrBKNjSZFpXXcXhFXa-846-786.png)\n"  +
//                "> ###### 10点20分发布 [天气](http://www.thinkpage.cn/) \n");
//        request.setMarkdown(markdown);

        OapiRobotSendResponse response = client.execute(request);
    }
}
