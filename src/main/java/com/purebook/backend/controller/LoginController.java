package com.purebook.backend.controller;

import net.sf.json.JSONObject;
import com.purebook.backend.util.AesCbcUtil;
import com.purebook.backend.util.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.purebook.backend.entity.User;
import com.purebook.backend.service.UserService;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "v1/login")
public class LoginController {

    @Autowired
    UserService userService;

    //encryptedData 明文,加密数据
    //iv            加密算法的初始向量
    //code          用户允许登录后，回调内容会带上 code（有效期五分钟），
    //              开发者需要将 code 发送到开发者服务器后台，使用code 换取 session_key api，
    //              将 code 换成 openid 和 session_key
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public Map decodeUserInfo(@RequestBody Map<String, String> request) {
        Map<String, Object> map = new HashMap<>();

        //登录凭证不能为空
        if (request.get("code") == null || request.get("code").length() == 0) {
            map.put("status", 0);
            map.put("msg", "code 不能为空");
            return map;
        }

        //小程序唯一标识   (在微信小程序管理后台获取)
        String wxspAppid = "";
        //小程序的 app secret (在微信小程序管理后台获取)
        String wxspSecret = "";
        //授权（必填）
        String grant_type = "authorization_code";


        //向微信服务器 使用登录凭证 code 获取 session_key 和 openid
        //请求参数
        String params = "appid=" + wxspAppid + "&secret=" + wxspSecret + "&js_code=" +
                request.get("code") + "&grant_type=" + grant_type;
        //发送请求
        String sr = HttpRequest.sendGet("https://api.weixin.qq.com/sns/jscode2session", params);
        //解析相应内容（转换成json对象）
        JSONObject json = JSONObject.fromObject(sr);
        //获取会话密钥（session_key）
        String session_key = json.get("session_key").toString();
        //用户的唯一标识（openid）
        String openid = (String) json.get("openid");


        User user = new User(openid, new Timestamp(System.currentTimeMillis()));
        user.setName(request.get("userName"));
        user.setAvatar(request.get("userAvatar"));
        userService.addUser(user);
        System.out.print(user.getName());


        //对encryptedData加密数据进行AES解密
        try {
            System.out.print(
                    request.get("encryptedData") + "\n" + request.get("iv") + "\n" + request.get("code") + "\n");

            String result = AesCbcUtil.decrypt(
                    request.get("encryptedData"), session_key, request.get("iv"), "UTF-8");
            //System.out.print(result+ "is");

            if (null != result && result.length() > 0) {
                map.put("status", 1);
                map.put("msg", "解密成功");

                JSONObject userInfoJSON = JSONObject.fromObject(result);
                Map<String, Object> userInfo = new HashMap<>();
                userInfo.put("openId", userInfoJSON.get("openId"));
                userInfo.put("nickName", userInfoJSON.get("nickName"));
                userInfo.put("gender", userInfoJSON.get("gender"));
                userInfo.put("city", userInfoJSON.get("city"));
                userInfo.put("province", userInfoJSON.get("province"));
                userInfo.put("country", userInfoJSON.get("country"));
                userInfo.put("avatarUrl", userInfoJSON.get("avatarUrl"));
                userInfo.put("unionId", userInfoJSON.get("unionId"));
                map.put("userInfo", userInfo);
                return map;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("status", 0);
        map.put("msg", "解密失败");
        return map;
    }

}
