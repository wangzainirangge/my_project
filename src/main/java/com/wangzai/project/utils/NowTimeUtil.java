package com.wangzai.project.utils;

import com.wangzai.project.entity.FindUrls;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NowTimeUtil {

    public static String nowTime(){
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowTime = dateFormat.format(date);
        return nowTime;
    }
    public static String getFileName(){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String filename = simpleDateFormat.format(date);
        return filename;
    }

    public static String dealDateFormat(String oldDate) {
        Date date1 = null;
        DateFormat df2 = null;
        try {
            oldDate= oldDate.replace("Z", " UTC");
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
            Date date = df.parse(oldDate);
            SimpleDateFormat df1 = new SimpleDateFormat ("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
            date1 = df1.parse(date.toString());
            df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return df2.format(date1);
    }

    public static List<FindUrls> SearchByTime(List<FindUrls> list, Date startTime, Date endTime){
        List<FindUrls> publicList = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (FindUrls findUrls:list){
            try {
                Date publicTime = format.parse(findUrls.getPublicTime());
                long find = publicTime.getTime();
                long start = startTime.getTime();
                long end = endTime.getTime();
                if (find >= start && find <= end && findUrls.getState()==1){
                    publicList.add(findUrls);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return publicList;
    }
}
