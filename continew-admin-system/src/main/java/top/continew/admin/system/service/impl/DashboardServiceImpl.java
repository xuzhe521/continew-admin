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

package top.continew.admin.system.service.impl;

import cn.hutool.core.util.NumberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import top.continew.admin.system.model.resp.dashboard.*;
import top.continew.admin.system.service.DashboardService;
import top.continew.admin.system.service.LogService;
import top.continew.admin.system.service.NoticeService;

import java.math.BigDecimal;
import java.util.List;

/**
 * 仪表盘业务实现
 *
 * @author Charles7c
 * @since 2023/9/8 21:32
 */
@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final LogService logService;
    private final NoticeService noticeService;

    @Override
    public DashboardTotalResp getTotal() {
        DashboardTotalResp totalResp = logService.getDashboardTotal();
        Long todayPvCount = totalResp.getTodayPvCount();
        Long yesterdayPvCount = totalResp.getYesterdayPvCount();
        BigDecimal newPvCountFromYesterday = NumberUtil.sub(todayPvCount, yesterdayPvCount);
        BigDecimal newPvFromYesterday = (0 == yesterdayPvCount)
            ? BigDecimal.valueOf(100)
            : NumberUtil.round(NumberUtil.mul(NumberUtil.div(newPvCountFromYesterday, yesterdayPvCount), 100), 1);
        totalResp.setNewPvFromYesterday(newPvFromYesterday);
        return totalResp;
    }

    @Override
    public List<DashboardAccessTrendResp> listAccessTrend(Integer days) {
        return logService.listDashboardAccessTrend(days);
    }

    @Override
    public List<DashboardNoticeResp> listNotice() {
        return noticeService.listDashboard();
    }

    @Override
    public List<DashboardChartCommonResp> getAnalysisTimeslot() {
        return logService.listDashboardAnalysisTimeslot();
    }

    @Override
    public List<DashboardChartCommonResp> getAnalysisGeo() {
        return logService.listDashboardAnalysisGeo(10);
    }

    @Override
    public List<DashboardChartCommonResp> getAnalysisModule() {
        return logService.listDashboardAnalysisModule(5);
    }

    @Override
    public List<DashboardChartCommonResp> getAnalysisOs() {
        return logService.listDashboardAnalysisOs(5);
    }

    @Override
    public List<DashboardChartCommonResp> getAnalysisBrowser() {
        return logService.listDashboardAnalysisBrowser(5);
    }
}
