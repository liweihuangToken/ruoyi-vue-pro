package cn.iocoder.yudao.module.system.controller.app.weixin;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "微信公众号")
@RestController
@RequestMapping("/system/wx-mp")
@Validated
@Slf4j
public class AppWxMpController {

    @Resource
    private WxMpService mpService;

    // TODO @芋艿：需要额外考虑个问题；多租户下，如果每个小程序一个微信公众号，则会存在多个 appid；
    @PostMapping("/create-jsapi-signature")
    @Operation(summary = "创建微信 JS SDK 初始化所需的签名",
        description = "参考 https://developers.weixin.qq.com/doc/offiaccount/OA_Web_Apps/JS-SDK.html 文档")
    public CommonResult<WxJsapiSignature> createJsapiSignature(@RequestParam("url") String url) throws WxErrorException {
        return success(mpService.createJsapiSignature(url));
    }

}
