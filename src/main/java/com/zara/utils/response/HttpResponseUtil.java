package com.zara.utils.response;


import com.alibaba.fastjson.JSON;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Title: HTTP 接口协作工具
 * Description: TODO
 * Date: 2021/4/17 20:16
 *
 * @author JiaQi Ding
 * @version 1.0
 */
public final class HttpResponseUtil {

    public static <T> void sendJson(HttpServletResponse response, ResultDTO<T> data) {
        String body = (JSON.toJSONString(data));
        response.setContentType("application/json; charset=utf-8");
        response.setCharacterEncoding("utf-8");
        try (
                ServletOutputStream outputStream = response.getOutputStream()
        ) {
            outputStream.write(body.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
