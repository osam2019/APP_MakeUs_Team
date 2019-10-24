package com.example.makeus.Module;

import com.example.makeus.Model.Soldier;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateCalculator {

    final String DISCHARGE = "DISCHARGE";
    final int defaultNSDay = 630;
    final String initialDateStrForDeduct = "2017-01-03";
    final String lastDateStrFordeduct = "2020-06-16";

    public long getDischargeDay(long enlistment_day) {
        // 1. 군복무 단축 이전 총 군복무 필요 일수 총합
        // 입대한날짜 + (21개월 * 30일 = 630)
        // 입대한달을 따로(less than or equal to 30일)
        // 입대한 달을 포함한 31일 달의 갯수 => 총 21달 체크  31일인 달: 1, 3, 5, 7, 8, 10, 12
        Date enlistmentDate = new Date(enlistment_day);
        Calendar cal = Calendar.getInstance();
        cal.setTime(enlistmentDate);

        int year = cal.get(Calendar.YEAR);
        int month = (cal.get(Calendar.MONTH) + 1) % 12;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        // 입대한달 이후 20개월동안 31일 달과 28일 혹은 29일의 총합 연산
        int additionalDay = 0;
        // 입대일자가 2020-06-16 이후인경우는 3개월 단축.
        int additionalDayForLast3Month = 0;

        // 입대한 달 포함 21개월동안 31일인 달의 갯수만큼 증가
        // 2월은 윤달인 경우 1만큼 감소, 윤달이 아닌경우 2만큼 감소
        for(int i = 0; i < 21; i++) {
            if(isMonthWith31days(month)) {
                additionalDay++;

                if(i >= 18) {
                    additionalDayForLast3Month++;
                }

            } else if( month == 2 ) {
                if(isLeapYear(year)) {
                    additionalDay -= 1;

                    if(i >= 18) {
                        additionalDayForLast3Month -= 1;
                    }

                } else {
                    additionalDay -= 2;

                    if(i >= 18) {
                        additionalDayForLast3Month -= 2;
                    }

                }
            }

            // 달 업데이트 이후, 달이 다음해로 지내는지 체크
            month = ((month + 1) % 12);

            // 다음달이 1월이면 year 업데이트
            if(month == 1) {
                year++;
            }
        }

        // 총 군복무 일수 결과
        int totalNSDay = defaultNSDay + additionalDay;

        // 2. 군복무 단축일수
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Date initialDate = new Date();
        Date lastDate = new Date();
        try{
            initialDate = format.parse(initialDateStrForDeduct);
            lastDate = format.parse(lastDateStrFordeduct);
        } catch(ParseException e) {
            e.printStackTrace();
        }

        if(enlistmentDate.compareTo(initialDate) < 0) {
            // 입대한달이 2017-01-03 > x  인경우 복무단축용이 안되경우인가?

            // 입대일에 totalNSDay 추가한뒤 반환
            return addTotalNSDayToInitialDate(enlistmentDate, totalNSDay);

        } else if( (enlistmentDate.compareTo(initialDate) >= 0) && enlistmentDate.compareTo(lastDate) < 0 ){
            // 입대한달이 2017-01-03 <= x < 2020-06-16 인가? (1일 ~ 90일 적용)
            int noOfDeductedDay = getNoOfDeductedDay(enlistmentDate, initialDate);
            totalNSDay -= noOfDeductedDay;

            // 입대일에 totalNSDay 추가한뒤 반환
            return addTotalNSDayToInitialDate(enlistmentDate, totalNSDay);

        } else {
            // 입대한달이 2020-06-16 부터인가? (3개월 적용)
            int noOfDeductedDay = getNoOfDeductedDay(enlistmentDate, initialDate) + additionalDayForLast3Month;
            totalNSDay -= noOfDeductedDay;

            // 입대일에 totalNSDay 추가한뒤 반환
            return addTotalNSDayToInitialDate(enlistmentDate, totalNSDay);
        }

    }

    // 일수가 31일인 달인경우 참으로 반환
    // month == 0: DEC, month == 1: JAN, month == 3: MAR
    // month == 5: MAY, month == 7: JUL, month == 8: AUG
    // month == 10: OCT
    private boolean isMonthWith31days(int month) {
        // 1, 3, 5, 7, 8, 10, 12
        if(month == 0 || month == 1 || month == 3 || month == 5
                      || month == 7 || month == 8 || month == 10) {
            return true;
        } else {
            return false;
        }
    }

    // 윤달인경우 참으로 반환
    private boolean isLeapYear(int year) {
        if(year % 4 == 0) {
            return true;
        } else {
            return false;
        }
    }

    // 복무단축일 반환하는 메소드
    private int getNoOfDeductedDay(Date enlistmentDate, Date initialDate) {
        long diff = enlistmentDate.getTime() - initialDate.getTime();
        int diffDays = (int) (diff / (24 * 60 * 60 * 1000));
        return ((diffDays/14) + 1);
    }

    private long addTotalNSDayToInitialDate(Date enlistmentDate, int totalNSDay) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(enlistmentDate);
        cal.add(Calendar.DATE, totalNSDay - 1);
        return cal.getTime().getTime();
    }

    // ------------------------------------ 인성검사 ------------------------------------
    public long getPersonalityTestDueDate(long transfer_day, String rank) {
        if(isValidPersonalityTestDate(transfer_day, rank)) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date(transfer_day));
            cal.add(Calendar.MONTH, 1);
            return cal.getTime().getTime();
        }
        return 0;
    }

    public boolean isValidPersonalityTestDate(long transfer_day, String rank){
        // 전입신병의 조건으로만
        if(rank.equals("이병") || rank.equals("일병")) {
            return toPrivateOrPrivateFirstClassCase(transfer_day);
        }
        return false;
    }


    // ------------------------------------ 신체검사 ------------------------------------
    public boolean isHealthScreeningDay(Soldier soldier) {
        // 전입 신병일 경우 (전입 신병 신체 검사)
        if(soldier.rank.equals("이병") || soldier.rank.equals("일병")) {
            return toPrivateOrPrivateFirstClassCase(soldier.transferDay);
        } else if(soldier.rank.equals("상병")) {  // 상병 신체 검사
            return toCorporalCase(soldier.enlistmentDay);
        }
        return false;
    }

    // 전입신병 (일, 이병으로만) 케이스: 전입오고 1달 이내로 시행.
    private boolean toPrivateOrPrivateFirstClassCase(long transfer_date) {
        // Compare transfer date with current date
        Calendar curCal = Calendar.getInstance();
        Calendar transferCal = Calendar.getInstance();
        transferCal.setTime(new Date(transfer_date));

        int curMonth = curCal.get(Calendar.MONTH) + 1;
        int transMonth = transferCal.get(Calendar.MONTH) + 1;
        int curDay = curCal.get(Calendar.DAY_OF_MONTH);
        int transDay = transferCal.get(Calendar.DAY_OF_MONTH);

        int monthDiff = ((curCal.get(Calendar.MONTH) - transferCal.get(Calendar.MONTH)) % 12);
        int dayDiff = curCal.get(Calendar.DAY_OF_MONTH) - transferCal.get(Calendar.DAY_OF_MONTH);

        // 1. 전입일과 현재 개월이 아직 같은 달일경우
        // 2. 전입월이 지난달이고 현재 달에서 전입월의 일수만큼 넘지않거나 같은경우
        // 3. 전입월이 12월이고 다음달이 1월인 경우.
        if( monthDiff == 0 || ( (monthDiff == 1) && (dayDiff <= 0))
       || ( (monthDiff == -1) && (dayDiff <= 0) ) || ( (monthDiff == 11) && (dayDiff <= 0) )) {
            return true;
        }

        return false;
    }

    private boolean toCorporalCase(long enlistment_date) {
        // Compare enlistment date with current date
        Calendar curCal = Calendar.getInstance();
        Calendar enlistmentCal = Calendar.getInstance();
        enlistmentCal.setTime(new Date(enlistment_date));

        // This policy affect to current soldier's rank since Sep 2019
        // if Enlistment day is 1st day
        if (enlistmentCal.get(Calendar.DAY_OF_MONTH) == 1) {
            // 예를 들어서 올해 8월 군번이면서 8월 1일 입대자는 10월에 일병 자동진급, 내년 4월에 상병진급 예정
            // 즉, 이병 8,9월, 일병 10, 11, 12, 1, 2, 3월 총 8개월간 복무
            // cf. 7월 1일 입대자는 9월에 자동 진급!
            int enlistmentMonth = enlistmentCal.get(Calendar.MONTH) + 1;
            int monthForCorporal = (enlistmentMonth + 8) % 12;
            int curMonth = curCal.get(Calendar.MONTH) + 1;
            int monthDiff = curMonth - monthForCorporal;

            // 진급일과 현재 개월이 아직 같은 달일 경우. 진급일은 매달 1일마다 이뤄지므로
            // 다음달 이전으로 넘어가선 안된다.
            if (monthDiff == 0) {
                return true;
            }

        } else {            // else: Enlistment day is not 1st day. the rest of 1st day.
            // 예를 들어서 올해 8월 군번이면서 8월 1일 이후의 입대자들은 11월 일병 자동진급, 내년 5월에 상병 진급 예쩡
            // 즉, 이병 8, 9, 10월, 일병 11, 12, 1, 2, 3, 4월 총 9개월간 복무
            // cf.7월은 10월 자동지급, 6월은 9월 자동진급 (5, 6월 동시진급)
            int enlistmentMonth = enlistmentCal.get(Calendar.MONTH) + 1;
            int monthForCorporal = (enlistmentMonth + 9) % 12;
            int curMonth = curCal.get(Calendar.MONTH) + 1;
            int monthDiff = curMonth - monthForCorporal;

            // 진급일과 현재 개월이 아직 같은 달일 경우. 진급일은 매달 1일마다 이뤄지므로
            // 다음달 이전으로 넘어가선 안된다.
            if (monthDiff == 0) {
                return true;
            }
        }

        return false;
    }

}
