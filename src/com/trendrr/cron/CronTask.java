/**
 * 
 */
package com.trendrr.cron;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.trendrr.oss.Reflection;


/**
 * @author Dustin Norlander
 * @created Mar 3, 2011
 * 
 */
public class CronTask {

	protected Log log = LogFactory.getLog(CronTask.class);
	
	protected SchedulingPattern pattern;
	protected Method method;
	protected Class cls;
	protected Object object = null;
	
	
	

	public CronTask(Class cls, Method method, SchedulingPattern pattern) {
		this.pattern = pattern;
		this.method = method;
		this.cls = cls;
	}
	
	public CronTask(Class cls, Method method, String pattern) throws InvalidPatternException {
		this(cls, method, new SchedulingPattern(pattern));
	}
	
	public SchedulingPattern getPattern() {
		return pattern;
	}

	public Method getMethod() {
		return method;
	}

	/**
	 * the instance that the execute method will invoke on.  if null a new instance will be created
	 * @return
	 */
	public Object getObject() {
		return object;
	}
	
	/**
	 * this will set the actual instance that the execute method will invoke on.
	 * @param obj
	 */
	public void setObject(Object obj) {
		this.object = obj;
	}
	
	public Class getCls() {
		return cls;
	}
	
	/**
	 * Executes the method.  If this.object is set then that the method will be invoked on that, otherwise
	 * a new instance will be instantiated and invoked.
	 * @param args
	 * @return
	 * @throws Exception 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public Object execute(Object ...args) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, Exception {
		if (this.object == null) {
			return this.method.invoke(Reflection.defaultInstance(cls), args);
		} else {
			return this.method.invoke(this.object, args);
		}
	}
}
