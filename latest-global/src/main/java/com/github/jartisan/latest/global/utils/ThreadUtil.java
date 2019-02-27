package com.github.jartisan.latest.global.utils;

import java.io.PrintStream;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/****
 * ThreadUtil
 * @author ejb3
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
				safeSleep(millis - System.currentTimeMillis() + initialTime);
				
			}
		}
	}

	public static void printThreadStacks(PrintStream ps) {
		printThreadStacks(ps, false);
	}

	public static void printThreadStacks(PrintStream ps, boolean printSystemThreads) {
		Set<String> systemThreadsNames = buildSystemThreadsNames();
		Map<Thread, StackTraceElement[]> threadMap = Thread.getAllStackTraces();
		for (Thread thread : threadMap.keySet()) {
			if (printSystemThreads || !systemThreadsNames.contains(thread.getName())) {
				ps.println(thread.getName());
				StackTraceElement[] trace = threadMap.get(thread);
				for (StackTraceElement traceElement : trace) {
					ps.println("\tat " + traceElement);
				}
			}
		}
	}

	private static Set<String> buildSystemThreadsNames() {
		Set<String> systemThreadsNames = new HashSet<>();
		systemThreadsNames.add("Attach Listener");
		systemThreadsNames.add("Monitor Ctrl-Break");
		systemThreadsNames.add("Finalizer");
		systemThreadsNames.add("Signal Dispatcher");
		systemThreadsNames.add("Reference Handler");
		return systemThreadsNames;
	}
}
