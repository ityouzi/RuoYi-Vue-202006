package com.ityouzi.common.utils.ip;

import com.alibaba.fastjson.JSONObject;
import com.ityouzi.common.utils.StringUtils;
import com.ityouzi.common.utils.http.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Auther: lizhen
 * @Date: 2020-06-14 15:04
 * @Description: 获取地址类
 */
public class AddressUtils {

    private static final Logger logger = LoggerFactory.getLogger(AddressUtils.class);

    public static final String IP_URL = "http://ip.taobao.com/service/getIpInfo.php";

    public static String getRealAddressByIP(String ip){
        String address = "XX XX";
        //内网不查询
        if (IpUtils.internalIp(ip)){
            return "内网IP";
        }
        String rspStr = HttpUtils.sendPost(IP_URL, "ip=" + ip);
        if (StringUtils.isEmpty(rspStr)){
            logger.error("获取地理位置异常 {}", ip);
            return address;
        }
        JSONObject obj = JSONObject.parseObject(rspStr);
        JSONObject data = obj.getObject("data", JSONObject.class);
        String region = data.getString("region");
        String city = data.getString("city");
        address = region + " " + city;
        return address;
    }

}
