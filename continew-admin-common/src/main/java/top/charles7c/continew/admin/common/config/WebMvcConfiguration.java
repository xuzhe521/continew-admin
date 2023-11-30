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

package top.charles7c.continew.admin.common.config;

import java.util.List;
import java.util.Objects;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import top.charles7c.continew.admin.common.config.properties.LocalStorageProperties;
import top.charles7c.continew.starter.core.constant.StringConstants;

/**
 * Web MVC 配置
 *
 * @author Charles7c
 * @since 2022/12/11 19:40
 */
@EnableWebMvc
@Configuration
@RequiredArgsConstructor
public class WebMvcConfiguration implements WebMvcConfigurer {

    private final LocalStorageProperties localStorageProperties;
    private final MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter;

    /**
     * 静态资源处理器配置
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        LocalStorageProperties.LocalStoragePath path = localStorageProperties.getPath();
        String avatarUtl = "file:" + path.getAvatar().replace(StringConstants.BACKSLASH, StringConstants.SLASH);
        String fileUrl = "file:" + path.getFile().replace(StringConstants.BACKSLASH, StringConstants.SLASH);
        registry.addResourceHandler(localStorageProperties.getFilePattern()).addResourceLocations(fileUrl)
            .setCachePeriod(0);
        registry.addResourceHandler(localStorageProperties.getAvatarPattern()).addResourceLocations(avatarUtl)
            .setCachePeriod(0);
    }

    /**
     * 解决 Jackson2ObjectMapperBuilderCustomizer 配置不生效的问题
     * <p>
     * MappingJackson2HttpMessageConverter 对象在程序启动时创建了多个，移除多余的，保证只有一个
     * </p>
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 自定义 converters 时，需要手动在最前面添加 ByteArrayHttpMessageConverter
        // 否则 Spring Doc OpenAPI 的 /*/api-docs/**（例如：/v3/api-docs/default）接口响应内容会变为 Base64 编码后的内容，最终导致接口文档解析失败
        // 详情请参阅：https://github.com/springdoc/springdoc-openapi/issues/2143
        converters.add(new ByteArrayHttpMessageConverter());
        converters.removeIf(MappingJackson2HttpMessageConverter.class::isInstance);
        if (Objects.isNull(mappingJackson2HttpMessageConverter)) {
            converters.add(new MappingJackson2HttpMessageConverter());
        } else {
            converters.add(mappingJackson2HttpMessageConverter);
        }
    }
}