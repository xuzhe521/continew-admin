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

package top.charles7c.cnadmin.system.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import top.charles7c.cnadmin.system.mapper.UserRoleMapper;
import top.charles7c.cnadmin.system.model.entity.UserRoleDO;
import top.charles7c.cnadmin.system.service.UserRoleService;

/**
 * 用户和角色业务实现类
 *
 * @author Charles7c
 * @since 2023/2/20 21:30
 */
@Service
@RequiredArgsConstructor
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleMapper userRoleMapper;

    @Override
    public void save(List<Long> roleIds, Long userId) {
        // 删除原有关联
        userRoleMapper.lambdaUpdate().eq(UserRoleDO::getUserId, userId).remove();
        // 保存最新关联
        List<UserRoleDO> userRoleList =
            roleIds.stream().map(roleId -> new UserRoleDO(userId, roleId)).collect(Collectors.toList());
        userRoleMapper.insertBatch(userRoleList);
    }

    @Override
    public Long countByRoleIds(List<Long> roleIds) {
        return userRoleMapper.lambdaQuery().in(UserRoleDO::getRoleId, roleIds).count();
    }

    @Override
    public List<Long> listRoleIdByUserId(Long userId) {
        return userRoleMapper.selectRoleIdByUserId(userId);
    }

    @Override
    public void deleteByUserIds(List<Long> userIds) {
        userRoleMapper.lambdaUpdate().in(UserRoleDO::getUserId, userIds).remove();
    }
}
