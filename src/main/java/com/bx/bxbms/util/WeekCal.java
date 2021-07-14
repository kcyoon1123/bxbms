package com.bx.bxbms.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component("weekCal")
public class WeekCal {
	
	public static String weekNum(String day) {
		// day yyyyMMdd
		String year = day.substring(0, 4);
		String month = day.substring(4, 6);
		String date = day.substring(6, 8);
		
		int intDAte = Integer.parseInt(date);
		String [] dateArray;
		
		int lastDay;
		int firstWeekLastday;
		int weekNum = 0;
		
		SimpleDateFormat formatFull = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat formatMonth = new SimpleDateFormat("yyyyMM");
		
		// 이번달 달력
		Calendar MonthCal = Calendar.getInstance();
		MonthCal.set(Integer.parseInt(year), Integer.parseInt(month)-1, 1);
		Date monthDate = MonthCal.getTime();
		
		// 전달의 달력
		Calendar prevMonthCal = Calendar.getInstance();
		prevMonthCal.set(Integer.parseInt(year), Integer.parseInt(month)-2, 1);
		Date prevMonthDate = prevMonthCal.getTime();
		String prevMonthString = formatFull.format(prevMonthDate);
		
		// 다음달 달력
		Calendar nextMonthCal = Calendar.getInstance();
		nextMonthCal.set(Integer.parseInt(year), Integer.parseInt(month), 1);
		Date nextMonthDate = nextMonthCal.getTime();
		
		lastDay = MonthCal.getActualMaximum(Calendar.DAY_OF_MONTH);
		dateArray = new String[lastDay];
		int lastInputDate = 0;
		
		//  행당 월, 1일 요일
		int firstDay = getWeekDay(year+month+"01");
		
		// 해당 월, 첫번째주의 일요일 날짜
		firstWeekLastday = 7-firstDay +2;
		
		// 첫번째주가 4일 미만일때는 첫번째주는 해당월의 x주차에 포함하지 않음
		// 남은 마지막주에서 날짜가 4미만일 경우 x주차에 포함하지 않음
		if(firstWeekLastday<4) {
			
			//해당 월의 첫번째 주 일요일까지는 전월의 마지막주차로 넣음
			for(int i=0; i<firstWeekLastday; i++) {
				dateArray[i] = formatMonth.format(prevMonthDate) + "0" + (totalWeekNum(prevMonthString));
			}
			// 중간에 있는 주 계산
			int loopRepeat = (lastDay - firstWeekLastday)/7;
			
			for(int i=0; i<loopRepeat; i++) {
				int startDate = firstWeekLastday+(7*i);
				for(int j=0; j<7; j++) {
					weekNum = i+1;
					dateArray[startDate+j] = formatMonth.format(monthDate)+"0"+weekNum;
					lastInputDate = startDate+j;
				}
			}
			
			// 나머지 남은 날짜 계산
			int loopRest = (lastDay - firstWeekLastday)%7;
			
			// 남은 일이 4일 이상이거나 0이면 해당 월의 주차로 계산한다.
			if(loopRest >= 4 || loopRest == 0) {
				weekNum++;
				for(int i=lastInputDate+1; i<lastDay; i++) {
					dateArray[i] = formatMonth.format(nextMonthDate)+"01";
				}
			}
			
		}else {
			// 첫번째주가 4일 이상일때
			 
			// 1일이 일요일이면 전월 마지막주차로 계산, 주의 시작은 월요일로 기준.
			String thisfirstDay = year+month+"01";
			if(getWeekDay(thisfirstDay) == 1) {
				dateArray[0] = formatMonth.format(prevMonthDate)+"0"+(totalWeekNum(prevMonthString));
				lastInputDate++;
			}
			
			// 첫번째주 1로 셋팅
			weekNum=1;
			for(int i=lastInputDate; i<firstWeekLastday; i++) {
				dateArray[i] = formatMonth.format(MonthCal.getTime())+"0"+weekNum;
			}
			
			// 중간에 있는 주 계산
			int loopRepeat = (lastDay - firstWeekLastday)/7;
			
			for(int i=0; i<loopRepeat; i++) {
				int startDate = firstWeekLastday+(7*i);
				for(int j=0; j<7; j++) {
					weekNum = i+2;
					dateArray[startDate+j] = formatMonth.format(monthDate)+"0"+weekNum;
					lastInputDate = startDate+j;
							
				}
			}
			
			// 나머지 남은 날짜 계산
			int loopRest = (lastDay - firstWeekLastday)%7;
			
			// 남은 일이 4일 이상이거나 0이면 해당월의 주차로 계산
			if(loopRest >= 4 || loopRest == 0) {
				weekNum++;
				for(int i = lastInputDate+1; i<lastDay; i++) {
					dateArray[i] = year+month+"0"+weekNum;
				}
			}else {
				// 남은일이 4일 미만이면 다음 월의 1주차로 계산
				for(int i=lastInputDate+1; i<lastDay; i++) {
					dateArray[i] = formatMonth.format(nextMonthDate)+"01";
							
				}
			}
		}
		return dateArray[Integer.parseInt(date)-1];		
		
	}
	// 한달의 총 주차 구하기 (첫주에 목요일이 포함되면 해당 달의 1주차로 계산)
	private static int totalWeekNum(String day) {
		String year = day.substring(0, 4);
		String month = day.substring(4, 6);
		String[] date = {"01","02","03","04"};
		int lastDay ;
		int firstWeekLastday;
		int weekNum=0;
		
		Calendar cal = Calendar.getInstance();
		cal.set(Integer.parseInt(year), Integer.parseInt(month)-1, 1);
		lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		// 해당 월, 1일 요일
		int firstDay = getWeekDay(year+month+"01");
		
		// 해당 월, 첫번째주의 일요일 날짜
		firstWeekLastday = 7-firstDay+2;
		
		// 첫번째주가 4일 미만일때는 첫번째주는 해당월의 x주차에 포함하지 않음
		// 남은 마지막주에 날짜가 4미만일 경우 x주차에 포함하지 않음
		if(firstWeekLastday<4) {
			weekNum = (lastDay - firstWeekLastday)/7;
			int tmp = (lastDay-firstWeekLastday)%7;
			if(tmp>=4) {
				weekNum++;
			}
			return weekNum;
		}else {
			weekNum = 1;
			weekNum += (lastDay-firstWeekLastday)/7;
			int tmp = (lastDay-firstWeekLastday)%7;
			if(tmp >=4) {
				weekNum++;
			}
			return weekNum;
		}
		
	}
	// 요일 구하기(1:일요일 ~ 7:토요일)
	private static int getWeekDay(String day) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.parseInt(day.substring(0,4)));
		cal.set(Calendar.MONTH, Integer.parseInt(day.substring(4, 6))-1);
		cal.set(Calendar.YEAR, Integer.parseInt(day.substring(6, 8)));
		int weekday = cal.get(Calendar.DAY_OF_WEEK);
		
		
		return weekday;
	}
	
		//현재 날짜 월요일
	 	public static String getCurMonday(){

	 		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	 		Calendar c = Calendar.getInstance();
	 		c.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
	 		return formatter.format(c.getTime());
	 	}



	 	//현재 날짜 일요일
	 	public static String getCurSunday(){
	 		SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd");
	 		Calendar c = Calendar.getInstance();

	 		c.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
	 		c.add(c.DATE,7);
	 		return formatter.format(c.getTime());
	 	}

	 	

	 	//현재 날짜 주차
	 	public static String getWeek(){
	 		Calendar c = Calendar.getInstance();
	 		String week = String.valueOf(c.get(Calendar.WEEK_OF_MONTH));

	 		return week;

	 	}

	 	//특정 년,월,주 차에 월요일 구하기
	 	public static String getMonday(String repdate){
	 		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	 		String yyyy = repdate.substring(0,4);
	 		String mm = repdate.substring(4,6);
	 		String wk = repdate.substring(6);
	 		
	 		
	 		Calendar c = Calendar.getInstance();
	 		int y=Integer.parseInt(yyyy);
	 		int m=Integer.parseInt(mm)-1;
	 		int w=Integer.parseInt(wk);
	 		
	 		c.set(Calendar.YEAR,y);
	 		c.set(Calendar.MONTH,m);
	 		c.set(Calendar.WEEK_OF_MONTH,w);
	 		c.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);

	 		return formatter.format(c.getTime());

	 	}

	 	

	 	//특정 년,월,주 차에 일요일 구하기
	 	public static String getSunday(String repdate){
	 		SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd");
	 		String yyyy = repdate.substring(0,4);
	 		String mm = repdate.substring(4,6);
	 		String wk = repdate.substring(6);
	 		Calendar c = Calendar.getInstance();

	 		int y=Integer.parseInt(yyyy);
	 		int m=Integer.parseInt(mm)-1;
	 		int w=Integer.parseInt(wk);
	 		
	 		c.set(Calendar.YEAR,y);
	 		c.set(Calendar.MONTH,m);
	 		c.set(Calendar.WEEK_OF_MONTH,w);
	 		c.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
	 		c.add(c.DATE,7);
	 		return formatter.format(c.getTime());

	 	}

}
