package com.example.makeus.Module;

import android.util.Log;

import com.example.makeus.Model.Soldier;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateCalculator {

        final String DISCHARGE = "DISCHARGE";

    public void getDischargeDay(long enlistment_day) throws ParseException {
        // 1. 단축이전 군복무 일수구하기
        // 입대한날짜 + (21개월 * 30일 = 630)
        // 입대한달을 따로(less than or equal to 30일)
        // + 임대한달 이후 31일 달의 갯수 => 총 20달 체크  31일인 달: 1, 3, 5, 7, 8, 10, 12
        // + (30일 - 입대한달의 복무수)
        // + 그해가 윤달인가? 윤달이면 -1 아니면 -2
        // 2. 입대한달이 2017-01-03 > x  인경우 복무단축일자 적용 x?         -   적용 x
        // 3. 입대한달이 2017-01-03 <= x <= 2020-06-15 인가?                 - 1일 ~ 90일
        // 4. 입대한달이 2020-06-16 부터인가?                                -   3개월
        //

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyy-MMM-dd", Locale.KOREA);
        Date elistmentDate = new Date(enlistment_day);

        Calendar cal = Calendar.getInstance();
        cal.setTime(elistmentDate);
        int year = cal.get(Calendar.YEAR);
        int month = (cal.get(Calendar.MONTH) + 1);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int restFromEnlistmentDay = 0;

        // 입대한 달에 복무가능한 날 갯수 계산
        if(isMonthwith31days(month)) {
            restFromEnlistmentDay = (31 - day) + 1;
        } else if( month == 2 ) {
            if(isLeapMonth(year)) {
                restFromEnlistmentDay = (29 - day) + 1;
            } else {
                restFromEnlistmentDay = (29 - day) + 1;
            }
        } else {
            restFromEnlistmentDay = (30 - day) + 1;
        }

        // 입대한달 이후 20개월동안 31일 달과 28일 혹은 29일의 총합 연산
        int additionalDay = 0;

        for(int i=0; i<20; i++) {
            // 달 업데이트 이후, 달이 다음해로 지내는지 체크
            month = ((month + 1) % 12);

            // 다음달이 1월이면 year 업데이트
            if(month == 1) {
                year++;
            }

            if(isMonthwith31days(month)) {
                additionalDay++;
            } else if( month == 2 ) {
                if(isLeapMonth(year)) {
                    additionalDay -= 1;
                } else {
                    additionalDay -= 2;
                }
            }
        }

        //


        // ----------------------------------------------------------------------------------------
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

        int i = 0;

        if((int)(diffdays%14) != 0) {
            i = (int)(diffdays / 14) + 1;       // suscipicious point
        } else {
            i = (int)(diffdays / 14);
        }

        int discharge = 640 - i;    // difficult to define the whole period is 640 days

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

        if (((double) discharge / 30) - (discharge / 30) == 0.0) {
            bonus = 0;
        }
        else if (((double) discharge / 30) > 18.6) {
            bonus = 1;
        }
        else {
            bonus = 2;
        }

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
        */

    }

    private boolean isMonthwith31days(int month) {
        // 1, 3, 5, 7, 8, 10, 12
        if(month == 0 || month == 1 || month == 3 || month == 5
        || month == 7 || month == 8 || month == 10 || month == 12) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isLeapMonth(int year) {
        if(year % 4 == 0) {
            return true;
        } else {
            return false;
        }
    }

    public void getPersonalityTestDay(long transfer_day, String rank){
        Calendar curCal = Calendar.getInstance();
        int month = curCal.get(Calendar.MONTH) + 1;
        int year = curCal.get(Calendar.YEAR);

        if(rank.equals("이병") || rank.equals("일병")) {
            // Compare transfer_day with current date
            Calendar transferCal = Calendar.getInstance();
            transferCal.setTime(new Date(transfer_day));

            //nt yearDiff = curCal.get(Calendar.YEAR) -

        }
    }

    public void getHealthScreeningDay(Soldier soldier) {

    }
}
