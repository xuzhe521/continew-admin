/*
 * Copyright (c) 2022-present Charles7c Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package top.charles7c.cnadmin.webapi.controller.auth;

import java.time.Duration;

import lombok.RequiredArgsConstructor;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wf.captcha.base.Captcha;

import cn.hutool.core.util.IdUtil;

import top.charles7c.cnadmin.auth.config.properties.CaptchaProperties;
import top.charles7c.cnadmin.auth.model.vo.CaptchaVO;
import top.charles7c.cnadmin.common.model.vo.R;
import top.charles7c.cnadmin.common.util.RedisUtils;

/**
 * 验证码 API
 *
 * @author Charles7c
 * @since 2022/12/11 14:00
 */
@Tag(name = "验证码 API")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/captcha", produces = MediaType.APPLICATION_JSON_VALUE)
public class CaptchaController {

    private final CaptchaProperties captchaProperties;

    @Operation(summary = "获取图片验证码", description = "获取图片验证码（Base64编码，带图片格式：data:image/gif;base64）")
    @GetMapping("/img")
    public R<CaptchaVO> getImageCaptcha() {
        // 生成验证码
        Captcha captcha = captchaProperties.getCaptcha();

        // 保存验证码
        String uuid = IdUtil.simpleUUID();
        String captchaKey = RedisUtils.formatKey(captchaProperties.getKeyPrefix(), uuid);
        RedisUtils.setCacheObject(captchaKey, captcha.text(),
            Duration.ofMinutes(captchaProperties.getExpirationInMinutes()));

        // 返回验证码
        CaptchaVO captchaVo = new CaptchaVO().setUuid(uuid).setImg(captcha.toBase64());
        return R.ok(captchaVo);
    }
}
