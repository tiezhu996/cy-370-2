package com.generated.ldlibseat.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.generated.ldlibseat.model.FeatureItem;
import com.generated.ldlibseat.model.KpiItem;
import com.generated.ldlibseat.model.OperationRecord;
import com.generated.ldlibseat.model.OverviewResponse;

@Service
public class OverviewService {
  public OverviewResponse getOverview() {
    return new OverviewResponse(
      "图书馆座位预约管理系统",
      "ldlibseat",
      "面向高校图书馆，提供座位可视化预约、签到管理和学习小组研讨间预约的智能化系统。",
      List.of(new FeatureItem(1, "楼层座位图可视化", "以楼层平面图展示所有座位分布，区分普通座位、靠窗座位、带电源座位，用颜色标识空闲/已约/使用中状态。", "已上线", "88%"),
        new FeatureItem(2, "按时段预约与选座", "用户选择日期和时段（最小单位30分钟或1小时），在座位图上点选空闲座位，系统自动校验时段冲突，预约成功生成二维码。", "排期中", "31 单"),
        new FeatureItem(3, "扫码签到与违规释放", "预约开始前后15分钟内扫码签到，超时未签到自动释放座位并标记违约，使用结束需扫码签退，记录实际使用时长。", "巡检中", "10 项"),
        new FeatureItem(4, "座位偏好记忆", "系统自动记录用户历史预约偏好（如常选楼层、靠窗座位），下次预约时优先推荐相似座位，提升预约效率。", "优化中", "4 级"),
        new FeatureItem(5, "学习小组研讨间预约", "提供独立研讨间（4-8人）预约功能，发起人邀请组员加入，预约成功后所有组员收到通知，研讨间配备白板和投影设备记录。", "可导出", "28 条")),
      List.of(new KpiItem("今日处理", "114", "+12%", "primary"),
        new KpiItem("预约/订单", "55", "+8%", "warm"),
        new KpiItem("履约率", "91%", "+3%", "cool"),
        new KpiItem("待处理", "8", "需跟进", "neutral")),
      List.of(new OperationRecord("ldlibseat-1", "楼层座位图可视化", "运营组", "已上线", "88%", "高"),
        new OperationRecord("ldlibseat-2", "按时段预约与选座", "管理员", "排期中", "31 单", "中"),
        new OperationRecord("ldlibseat-3", "扫码签到与违规释放", "服务台", "巡检中", "10 项", "低"),
        new OperationRecord("ldlibseat-4", "座位偏好记忆", "财务组", "优化中", "4 级", "高"),
        new OperationRecord("ldlibseat-5", "学习小组研讨间预约", "审核组", "可导出", "28 条", "中"))
    );
  }
}
