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
    //获取当前时间 年 月 日 时 分
    public String getNow(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);//年份数值
        int moth = calendar.get(Calendar.MONTH)+1;//年份数值
        int day = calendar.get(Calendar.DATE);//年份数值
        int hour=calendar.get(Calendar.HOUR);
        int minute=calendar.get(Calendar.MINUTE);
        String m= String.valueOf(moth),d= String.valueOf(day),h= String.valueOf(hour),mi= String.valueOf(minute);
        if (moth<10){
            m="0"+m;
        }
        if (day<10){
            d="0"+d;
        }
        if (hour<10){
            h="0"+h;
        }
        if (minute<10){
            mi="0"+mi;
        }
        String DateTime=year+"年"+m+"月"+d+"日"+h+"时"+mi+"分";
        return DateTime;
    }
    //YYYY-MM-DD hh:mm:ss
    public String getNowhhhh(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);//年份数值
        int moth = calendar.get(Calendar.MONTH)+1;//年份数值
        int day = calendar.get(Calendar.DATE);//年份数值
        int hour=calendar.get(Calendar.HOUR);
        int minute=calendar.get(Calendar.MINUTE);
        int second=calendar.get(Calendar.SECOND);
        String m= String.valueOf(moth),d= String.valueOf(day),h= String.valueOf(hour),mi= String.valueOf(minute),sec=String.valueOf(second);
        if (moth<10){
            m="0"+m;
        }
        if (day<10){
            d="0"+d;
        }
        if (hour<10){
            h="0"+h;
        }
        if (minute<10){
            mi="0"+mi;
        }
        if (second<10){
            sec="0"+sec;
        }
        String DateTime=year+"-"+m+"-"+d+" "+h+":"+mi+":"+sec;
        return DateTime;
    }
}
