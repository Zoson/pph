package com.pengpenghui.domain.entity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by zoson on 7/20/15.
 */
public class Duration {
    private Date begin;
    private Date end;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    public Duration(String begin,String end){
        this.begin = stringToDate(begin);
        this.end = stringToDate(end);
    }
    public Duration(Date begin,Date end){
        this.begin = begin;
        this.end = end;
    }
    public Date stringToDate(String time){
        String[] arr_date = time.split("-");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, Integer.parseInt(arr_date[0]));
        calendar.set(Calendar.MONTH, Integer.parseInt(arr_date[1]));
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(arr_date[2]));
        Date date  = calendar.getTime();
        return date;
    }
    public String toString_beginDate(){
        String str = format.format(begin);
        if (str == null){
            return begin.toString();
        }else{
            return str;
        }
    }
    public String toString_endDate(){
        String str = format.format(end);
        if (str == null){
            return end.toString();
        }else{
            return str;
        }
    }
    public String toString(){
        return toString_beginDate()+" "+toString_endDate();
    }
    public void setBegin(String begin){
        this.begin = stringToDate(begin);
    }
    public void setEnd(String end){
        this.end = stringToDate(end);
    }
}
