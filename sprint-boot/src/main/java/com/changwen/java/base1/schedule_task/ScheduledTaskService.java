package com.changwen.java.base1.schedule_task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 定时(计划)任务
 * @author changwen on 2017/7/21.
 */
@Service
public class ScheduledTaskService {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

    /**
     * cron表达式详解：一个cron表达式有至少6个（也可能7个）有空格分隔的时间元素。按顺序依次为
     * 1-秒（0~59） 2-分钟（0~59） 3-小时（0~23） 4-天（0~31）
     * 5-月（0~11） 6-星期（1~7 1=SUN 或 SUN，MON，TUE，WED，THU，FRI，SAT）7-年份（1970－2099）
     * 其中每个元素可以是一个值(如6),一个连续区间(9-12),一个间隔时间(8-18/4)(/表示每隔4小时),一个列表(1,3,5),通配符。
     * 由于"月份中的日期"和"星期中的日期"这两个元素互斥的,必须要对其中一个设置?。
     *
     *  - 表示一个指定的范围；
     *  , 表示附加一个可能值；
     *  "*" 代表所有可能的值
     *  "/" 字符用来指定数值的增量   例如：在子表达式（分钟）里的“3/20”表示从第3分钟开始，每20分钟（它和“3，23，43”）的含义一样
     *  "？" 表示未说明的值，即不关心它为何值字符。仅被用于天（月）和天（星期）两个子表达式，表示不指定值。当2个子表达式其中之一被指定了值以后，为了避免冲突，需要将另一个子表达式的值设为“？”
     *  "L" 字符仅被用于天（月）和天（星期）两个子表达式，它是单词“last”的缩写
            如果在“L”前有具体的内容，它就具有其他的含义了。例如：“6L”表示这个月的倒数第６天
            注意：在使用“L”参数时，不要指定列表或范围，因为这会导致问题
     *  W 字符代表着平日(Mon-Fri)，并且仅能用于日域中。它用来指定离指定日的最近的一个平日。大部分的商业处理都是基于工作周的，所以 W 字符可能是非常重要的。
            例如，日域中的 15W 意味着 "离该月15号的最近一个平日。" 假如15号是星期六，那么 trigger 会在14号(星期五)触发，因为星期四比星期一离15号更近。
     *  C：代表“Calendar”的意思。它的意思是计划所关联的日期，如果日期没有被关联，则相当于日历中所有日期。例如5C在日期字段中就相当于日历5日以后的第一天。1C在星期字段中相当于星期日后的第一天。
     */
    // cron是UNIX和Linux系统下的定时作业  每天11点28分执行
    @Scheduled(cron = "0 28 11 ? * *")
    public void cron() {
        System.out.println("cron:在指定时间：" + sdf.format(new Date()) + " 执行");
    }

    // （如果某次任务开始时上次任务还没有结束，那么在上次任务执行完成时，当前任务会立即执行）
    @Scheduled(fixedRate = 5000)
    public void fixedRate() {
        System.out.println("fixedRate:每隔五秒执行一次 " + sdf.format(new Date()));
    }

    @Scheduled(fixedDelay = 1000)
    public void fixedDelay() {
        System.out.println("fixedDelay: 上次任务结束后1S后再次执行 " + sdf.format(new Date()));
    }
}
