package com.heartsuit.showcase.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    private LocalDate date = LocalDate.now();

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String addDay(String str,String day){
        LocalDate startDate = LocalDate.parse(str,DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate newDate = startDate.plusDays(Integer.parseInt(day));
        return newDate.toString();
    }

    public String addMonth(String str,String month){
        LocalDate startDate = LocalDate.parse(str,DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate newDate = startDate.plusMonths(Integer.parseInt(month));
        return newDate.toString();
    }

    public String getCurrentDate(){
        return LocalDate.now().toString();
    }

    public int getDaysOfMonth() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 传入时间小于现在时间返回 "before" 大于返回"after" 相等返回"equal"
     * @param testDate
     * @return
     */
    public String isLaterThanToday(String testDate){
        LocalDate startDate = LocalDate.parse(testDate,DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        if(startDate.isBefore(LocalDate.now()))
            return "before";
        else if(startDate.isAfter(LocalDate.now()))
            return "after";
        else
            return "equal";
    }

//    public void LocalDateToUdate() {
//        LocalDate localDate = LocalDate.now();
//        ZoneId zone = ZoneId.systemDefault();
//        Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
//        java.util.Date date = Date.from(instant);
//    }
//
//    public void UDateToLocalDate() {
//        java.util.Date date = new java.util.Date();
//        Instant instant = date.toInstant();
//        ZoneId zone = ZoneId.systemDefault();
//        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
//        LocalDate localDate = localDateTime.toLocalDate();
//    }

    public static void main(String[] args) {
        DateUtil date = new DateUtil();
//        System.out.println(date.getCurrentDate()); //2019-08-30
//        System.out.println(date.addDay(date.getCurrentDate(),"20"));
//        System.out.println(date.addMonth(date.getCurrentDate(),"20"));
//        System.out.println(date.getDaysOfMonth());
        System.out.println(date.isLaterThanToday("2019-08-30"));
        System.out.println(date.isLaterThanToday("2018-08-30"));
        System.out.println(date.isLaterThanToday("2020-08-30"));
        System.out.println(date.isLaterThanToday("2019-09-01"));
    }
}
