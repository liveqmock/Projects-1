package com.newsoft.foundation.util;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Define a support class to use the simple thread pool.
 * 
 * @author George Guo
 * 
 */
public class ThreadPoolSupport {
	private Log logger = LogFactory.getLog(ThreadPoolSupport.class);

	/**
	 * the core pool size
	 */
	private int corePoolSize = 3;

	/**
	 * the max pool size
	 */
	private int maxPoolSize = 10;

	/**
	 * the time to keep thread alive when free, time unit is second.
	 */
	private int keepAliveSeconds = 30;

	/**
	 * the max waiting queue size
	 */
	private int maxWaitingTasks = 2000;

	/**
	 * A simple thread pool executor.
	 */
	private ThreadPoolExecutor threadPoolExecutor;

	/**
	 * Destroy the thread pool
	 */
	synchronized public void destroy() {
		if (threadPoolExecutor != null && !threadPoolExecutor.isShutdown()) {
			if (logger.isInfoEnabled()) {
				logger.info("To shutdown the thread pool excutor: [" + threadPoolExecutor + "]");
			}

			try {
				threadPoolExecutor.shutdown();
			} catch (Exception e) {
				logger.error("Failed to shutdown the thread pool excutor: [" + threadPoolExecutor + "]");
				return;
			}

			if (logger.isInfoEnabled()) {
				logger.info("Succeed to shutdown the thread pool excutor: [" + threadPoolExecutor + "]");
			}
		}
	}

	public void executeTask(ThreadTask task) {
		try {
			getThreadPoolExecutor().execute(new ThreadWorker(task));
		} catch (Exception e) {
			logger.error("Error when try to execute task: " + task, e);
		}
	}

	class ThreadWorker implements Runnable {

		private ThreadTask task;

		public ThreadWorker(ThreadTask task) {
			this.task = task;
		}

		@Override
		public void run() {
			if (task == null) {
				// Should never happen.
				return;
			}

			if (logger.isDebugEnabled()) {
				logger.debug("To execute thread work...");
			}

			long startTimeMili = System.currentTimeMillis();

			task.doTask();

			long costTimeMili = System.currentTimeMillis() - startTimeMili;

			if (logger.isDebugEnabled()) {
				logger.debug("Finished execute thread work, cost time mili: " + costTimeMili);
			}

		}

	}

	/**
	 * @param corePoolSize
	 *            the corePoolSize to set
	 */
	public void setCorePoolSize(int corePoolSize) {
		this.corePoolSize = corePoolSize;
	}

	/**
	 * @param maxPoolSize
	 *            the maxPoolSize to set
	 */
	public void setMaxPoolSize(int maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}

	/**
	 * @param keepAliveSeconds
	 *            the keepAliveSeconds to set
	 */
	public void setKeepAliveSeconds(int keepAliveSeconds) {
		this.keepAliveSeconds = keepAliveSeconds;
	}

	/**
	 * @param maxWaitingTasks
	 *            the maxWaitingTasks to set
	 */
	public void setMaxWaitingTasks(int maxWaitingTasks) {
		this.maxWaitingTasks = maxWaitingTasks;
	}

	/**
	 * @return the threadPoolExecutor
	 */
	public ThreadPoolExecutor getThreadPoolExecutor() {
		if (threadPoolExecutor != null) {
			return threadPoolExecutor;
		}

		synchronized (this) {
			if (threadPoolExecutor == null) {
				threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveSeconds,
						TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(maxWaitingTasks, true));

				if (logger.isInfoEnabled()) {
					logger.info("Succeed to create a new thread pool excutor: [" + threadPoolExecutor + "]");
				}
			}
		}
		return threadPoolExecutor;
	}

}
