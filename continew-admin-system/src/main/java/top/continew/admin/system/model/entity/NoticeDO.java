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

package top.continew.admin.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import top.continew.starter.extension.crud.model.entity.BaseDO;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 公告实体
 *
 * @author Charles7c
 * @since 2023/8/20 10:55
 */
@Data
@TableName(value = "sys_notice",autoResultMap = true)
public class NoticeDO extends BaseDO {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 类型
     */
    private String type;

    /**
     * 生效时间
     */
    private LocalDateTime effectiveTime;

    /**
     * 终止时间
     */
    private LocalDateTime terminateTime;

    /**
     * 通知范围
     */
    private Integer noticeScope;

    /**
     * 通知用户
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> noticeUsers;
}