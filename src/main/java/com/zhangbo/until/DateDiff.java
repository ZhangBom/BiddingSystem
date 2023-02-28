package com.zhangbo.until;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateDiff {
    public String DateWeekend(String Datetime){
        int DateYear= Integer.parseInt(StringUtils.substringBefore(Datetime, "年"));
        int DateMoth= Integer.parseInt(StringUtils.substringBetween(Datetime, "年", "月"));
        int DateDay= Integer.parseInt(StringUtils.substringBetween(Datetime, "月", "日"));
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.YEAR,DateYear);
        calendar.set(Calendar.MONTH,DateMoth-1);
        //使开标时间为报名结束后的俩天
        calendar.set(Calendar.DATE,DateDay+5);
//        获取这个日期是周几
        int w =calendar.get(Calendar.DAY_OF_WEEK);
//        如果这个日期是星期五则改到2天后
        if(w>5){
            calendar.add(Calendar.DATE,2);
        }
        //重新格式化
        int year = calendar.get(Calendar.YEAR);//年份数值
        int moth = calendar.get(Calendar.MONTH)+1;//年份数值
        int day = calendar.get(Calendar.DATE);//年份数值
        String DateTime=year+"年"+moth+"月"+day+"日";
        return DateTime;
    }
    //获取当前时间
    public String getNow(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);//年份数值
        int moth = calendar.get(Calendar.MONTH)+1;//年份数值
        int day = calendar.get(Calendar.DATE);//年份数值
        int hour=calendar.get(Calendar.HOUR);
        int minute=calendar.get(Calendar.MINUTE);
        String DateTime=year+"年"+moth+"月"+day+"日"+hour+"时"+minute+"分";
        return DateTime;
    }
}