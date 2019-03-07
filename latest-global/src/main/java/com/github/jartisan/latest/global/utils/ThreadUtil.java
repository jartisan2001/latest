package com.github.jartisan.latest.global.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/****
 * ThreadUtil
 * @author jalen
 *
 */
public class ThreadUtil {
	
	private static final Logger log = LogManager.getLogger(ThreadUtil.class);
	
	public static String invokerName(int levels) {
		return Thread.currentThread().getStackTrace()[levels + 2].toString();
	}

	public static void safeSleep(long millis) {
		if (millis > 0L) {
			long initialTime = System.currentTimeMillis();
			try {
				Thread.sleep(millis);
			} catch (InterruptedException e) {
				// sleep again the remaining time
				log.warn("thread sleep :",e.getMessage());
				Thread.currentThread().interrupt();
				safeSleep(millis - System.currentTimeMillis() + initialTime);
			}
		}
	}

}
