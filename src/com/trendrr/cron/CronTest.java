/**
 * 
 */
package com.trendrr.cron;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import com.trendrr.common.enums.TimeFrame;
import com.trendrr.oss.IsoDateUtil;


/**
 * @author Dustin Norlander
 * @created May 19, 2011
 * 
 */
public class CronTest {

	protected Log log = LogFactory.getLog(CronTest.class);
	
	public static void main(String ...strings) {
		new CronTest().test();
		
	}
	
	@Test
	public void test() {
		Date date = IsoDateUtil.parse("2011-05-19T17:21:07Z");
		CronTasks tasks = new CronTasks();
		try {
			tasks.register(this.getClass());
		} catch (InvalidPatternException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		Calendar cal = Calendar.getInstance(TimeFrame.getTrendrrTimeZone());
		List<CronTask> t = new ArrayList<CronTask>();
		while (t.isEmpty()) {
			date = TimeFrame.MINUTES.add(date, 1);
//			System.out.println(date);
			cal.setTime(date);
			t = tasks.getTasksToExecute(cal);
		}
		for (CronTask task : t) {
			try {
				task.execute(date);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@Cron("daily")
	public void daily(Date date) {
		System.out.println("TO EXECUTE ON: " + date);
	}
}
