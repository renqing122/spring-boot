package com.heartsuit.showcase.util;

import java.time.LocalDate;
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
    public static void main(String[] args) {
        DateUtil date = new DateUtil();
        System.out.println(date.getCurrentDate()); //2019-08-30
        System.out.println(date.addDay(date.getCurrentDate(),"20"));
        System.out.println(date.addMonth(date.getCurrentDate(),"20"));
        System.out.println(date.getDaysOfMonth());
    }
}
