package com.wapwag.woss.modules.sys.listener;

import com.mysql.jdbc.AbandonedConnectionCleanupThread;
import com.wapwag.woss.common.task.TaskControl;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

import static com.wapwag.woss.common.task.TaskControl.stopTask;

public class CountStartListener extends HttpServlet implements ServletContextListener {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory.getLogger(CountStartListener.class);

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		logger.info("===== shutdown hooks triggered =====");

		AbandonedConnectionCleanupThread.checkedShutdown();

		logger.info("===== start deregister jdbc driver =====");

		Enumeration<Driver> registeredDriver = DriverManager.getDrivers();

		while (registeredDriver.hasMoreElements()) {
			try {
				Driver driver = registeredDriver.nextElement();

				//deregister jdbc driver to avoid memory leak when the application redeployed
				DriverManager.deregisterDriver(driver);
				logger.info("===== deregister driver:{}=====", driver);
			} catch (SQLException e) {
				logger.error("===== deregister driver failed for:{} =====", ExceptionUtils.getStackTrace(e));
			}
		}

		logger.info("===== deregister jdbc driver complete =====");

		logger.info("===== start close timer task =====");

		// stop timer task to avoid thread can't be stoped
		stopTask();

		logger.info("===== close timer task successfully =====");

		logger.info("===== shutdown hooks completed =====");
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		//启动定时任务
        TaskControl.initTask();
		
	}
}
