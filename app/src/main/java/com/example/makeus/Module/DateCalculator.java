package com.example.makeus.Module;

import com.example.makeus.Model.Soldier;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateCalculator {
    public void getDischargeDay(long enlistment_day) throws ParseException {
        SimpleDateFormat formatter_one = new SimpleDateFormat ( "yyyyMMddhh",Locale.KOREA );
        SimpleDateFormat formatter_two = new SimpleDateFormat ( "yyyy-MM-dd" );
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());

        ParsePosition pos = new ParsePosition (0);
        Date frmTime = formatter_one.parse (Long.toString(enlistment_day), pos );
        String outString = formatter_two.format (frmTime);
        Date d = formatter_two.parse("2017-01-03");
        Date b = formatter_two.parse(outString);
        System.out.println("1: " +outString);

        long dDay = b.getTime() - d.getTime();
        long diffdays = dDay / (24 * 60 * 60 *1000);
        int i = (int)(diffdays / 14) + 1;
        int discharge = 640 - i;
        System.out.println("2: " +discharge);

        Calendar cal = Calendar.getInstance();
        cal.setTime(b);
        Date currentTime = cal.getTime();
        String year = yearFormat.format(currentTime);
        int yyyy = Integer.parseInt(year);
        int year_flag = 0;
        int tmpMonth = cal.get(Calendar.MONTH) + 1;
        System.out.println("2-1: " + yyyy);
        System.out.println("2-1: " + tmpMonth);
        System.out.println("2-1: " + cal.get(Calendar.DAY_OF_MONTH));

        cal.setTime(b);
        cal.add(Calendar.DAY_OF_MONTH, discharge);
        int nDay = cal.get(Calendar.DAY_OF_MONTH);
        int bonus = 0;
        if (((double) discharge / 30) - (discharge / 30) == 0.0) bonus = 0;
        else if (((double) discharge / 30) > 18.6) bonus = 1;
        else bonus = 2;
        for (int k = 1; k < discharge / 30 + bonus; k++) {
            if(tmpMonth == 2){
                if (year_flag == 0) {
                    if (((yyyy % 4) == 0 && (yyyy % 100) != 0) || (yyyy % 100 == 0) && (yyyy % 400 == 0)) {
                        nDay ++;
                        System.out.println("5: " + nDay);
                        System.out.println("6: " + "윤년 +1");
                        year_flag = 1;
                    }
                }else if(year_flag == -1) {
                    year_flag = -2;
                    nDay --;
                }

                nDay -= 2;
                System.out.println("7: " + nDay);
                System.out.println("8: " + "-2");
            }
            tmpMonth += 1;

            if(tmpMonth == 7) {
                nDay ++;
                System.out.println("9: " + nDay);
                System.out.println("10: " + "+1");
            }

            if(tmpMonth > 12){
                if(year_flag == 1) { year_flag = -1; }
                tmpMonth = 1;
                yyyy += 1;
                System.out.println("3: " + "월: " + tmpMonth);
                System.out.println("4: " +"년도: " + yyyy);
                System.out.println("4-1: " +"일: " + nDay);
            }
        }

        if(nDay < 0) {
            if ((tmpMonth % 2 == 0) && tmpMonth != 2) {
                nDay += 30;
            }else if ((tmpMonth % 2 == 0) && tmpMonth == 2) {
                if(year_flag == 1) { nDay += 29; }
                else{ nDay += 28; }
            }else if(tmpMonth % 2 == 1) {
                nDay += 31;
            }
        }else if(nDay == 0) {
            if ((tmpMonth % 2 == 0) && tmpMonth != 2) {
                nDay = 30;
            }else if ((tmpMonth % 2 == 0) && tmpMonth == 2) {
                if(year_flag == 1) { nDay = 29; }
                else{ nDay = 28; }
            }else if(tmpMonth % 2 == 1) {
                nDay = 31;
            }
        }

        System.out.println("11: " + nDay);
        System.out.println("12: " + tmpMonth);
        System.out.println("13: " + yyyy);

        StringBuffer sbDate = new StringBuffer();
        sbDate.append (yyyy);
        if (tmpMonth < 10) sbDate.append("0");
        System.out.println(sbDate);
        sbDate.append(tmpMonth);
        if (nDay < 10) sbDate.append("0");
        System.out.println(sbDate);
        sbDate.append(nDay);

        System.out.println(sbDate);
    }

    public void getPersonalityTestDay(long transfer_day, String rank){
        Calendar cal = Calendar.getInstance();
        int m = cal.get(Calendar.MONTH) + 1;
        int y = cal.get(Calendar.YEAR);

        if(rank == "이병" || rank == "일병") {
            /*SimpleDateFormat formatter_one = new SimpleDateFormat ( "yyyyMMddhh",Locale.KOREA );
            SimpleDateFormat formatter_two = new SimpleDateFormat ( "yyyy-MM-dd" );
            SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());

            ParsePosition pos = new ParsePosition (0);
            Date frmTime = formatter_one.parse (Long.toString(enlistment_day), pos );
            String outString = formatter_two.format (frmTime);
            Date d = formatter_two.parse("2017-01-03");
            Date b = formatter_two.parse(outString);
            System.out.println("1: " +outString);

            long dDay = b.getTime() - d.getTime();
            long diffdays = dDay / (24 * 60 * 60 *1000);
            int i = (int)(diffdays / 14) + 1;
            int discharge = 640 - i;
            */
        }
    }

    public void getHeathTestDay(Soldier soldier) {
        /*
        SimpleDateFormat formatter_one = new SimpleDateFormat ( "yyyyMMddhh",Locale.KOREA );
        SimpleDateFormat formatter_two = new SimpleDateFormat ( "yyyy-MM-dd" );
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());

        ParsePosition pos = new ParsePosition (0);
        Date frmTime = formatter_one.parse (Long.toString(enlistment_day), pos );
        String outString = formatter_two.format (frmTime);
        Date d = formatter_two.parse("2017-01-03");
        Date b = formatter_two.parse(outString);
        System.out.println("1: " +outString);

        long dDay = b.getTime() - d.getTime();
        long diffdays = dDay / (24 * 60 * 60 *1000);
        int i = (int)(diffdays / 14) + 1;
        int discharge = 640 - i;
         */
    }
}
