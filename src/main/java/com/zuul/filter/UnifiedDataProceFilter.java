package com.zuul.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.constants.ZuulConstants;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.zuul.model.BuildResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.util.StreamUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * @author: create QiangShW
 * @version: v1.0
 * @description: com.zuul.filter
 * @date:2019/7/6
 *
 * 返回数据统一处理过滤器 顺序是最后1位
 **/
@Slf4j
public class UnifiedDataProceFilter extends ZuulFilter {

    /**
     * 返回过滤器类型
     * PRE 前置过滤器
     * ROUTING  将请求路由到原应用地址
     * POST  后置过滤器
     * ERROR  上面3个过滤器任意1个出问题都由他处理异常
     *
     * @return
     */
    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
    }

    /**
     * 过滤器优先级
     * 数字越大优先级越低
     *
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 是否执行该过滤器
     *
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        Integer httpStatus = requestContext.getResponseStatusCode();
        String result = "";
        switch (httpStatus) {
            case 200:
                try {
                    InputStream inputStream = requestContext.getResponseDataStream();
                    String body = StreamUtils.copyToString(inputStream, Charset.forName("UTF-8"));
                    JSONObject jsonObject = JSONObject.parseObject(body);
                    log.info(jsonObject.toJSONString());
                    result = JSONArray.toJSONString(BuildResult.build(httpStatus, jsonObject));
                } catch (Exception e) {
                    log.error("异常-------->{}",e.toString());
                    result = JSONArray.toJSONString(BuildResult.build(500, requestContext.getRouteHost().getPath() + "异常", ""));
                }
                break;
            case 500:
                result = JSONArray.toJSONString(BuildResult.build(500, "服务器异常", ""));
                break;
            default:
                result = JSONArray.toJSONString(BuildResult.build(400, "", ""));

        }
        requestContext.setResponseBody(result);
        return null;
    }
}
