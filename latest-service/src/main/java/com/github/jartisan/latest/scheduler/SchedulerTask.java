package com.github.jartisan.latest.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import com.github.jartisan.latest.global.consts.Const;
import com.github.jartisan.latest.service.github.GithubService;
import com.github.jartisan.latest.service.sonatype.SonatypeService;

/***
 * 订时任务
 * @author jalen
 */
@Component
public class SchedulerTask {
	
	private static final Logger log = LoggerFactory.getLogger(SchedulerTask.class);
	
	@Autowired
	private SonatypeService sonatypeService;
	
	@Autowired
	private GithubService githubService;
	
	private int count =0;
    /***
     * 每整点小时
     */
    @Scheduled(cron = "0 0 0/1 * * ?")
    private void sonatypeMavenTask(){
    	sonatypeService.discover(Const.CHECK_TYPE_MAVEN);
    	log.info("this is sonatype task runing {}",count++);
    }
    
    
    /***
     *  每隔1小时
     *  以一个固定延迟时间1000*60*60秒钟调用一次执行
     *	这个周期是以上一个调用任务的##完成时间##为基准，在上一个任务完成之后，1000*60*60 s后再次执行
     *  @Scheduled(fixedDelay = 1000*60*60)
     */
    @Scheduled(cron = "0 0 0/1 * * ?")
    private void sonatypeGithubTask(){
    	sonatypeService.discover(Const.CHECK_TYPE_GITHUB);
    	log.info("this is sonatype task runing {}",count++);
    }
   
   
   
   /***
    * 每天凌晨1点00分
    */
   @Scheduled(cron = "0 00 01 * * ?")
   public void everyDailyTask(){
	   String[] checkList = {Const.CHECK_TYPE_MAVEN,Const.CHECK_TYPE_GITHUB};
	   StopWatch stopwatch = new StopWatch("每日统计");
	   for(String checkType : checkList) {
		   stopwatch.start("list_"+checkType);
		   githubService.everyDaily(checkType);
		   stopwatch.stop();
	   }
	   log.info(stopwatch.prettyPrint());
   	log.info("this is sonatype task runing {}",count++);
   }
   
   
   /***
    *  每隔10分钟
    *  以一个固定延迟时间1000*60*60秒钟调用一次执行
    *	这个周期是以上一个调用任务的##完成时间##为基准，在上一个任务完成之后，1000*60*10 s后再次执行
    *  @Scheduled(fixedDelay = 1000*60*10)
    */
   @Scheduled(fixedDelay = 1000*60*10)
   public void syncReadmeTask(){
	   StopWatch stopwatch = new StopWatch("Readme统计");
	   stopwatch.start();
	   try {
		githubService.syncReadme();
	} catch (Exception e) {
		log.error("syncReadmeTask error : {} ",e);
	}
	   stopwatch.stop();
	   log.info("TaskCount : {} , TotalTimeSeconds : {} s", stopwatch.getTaskCount(), stopwatch.getTotalTimeSeconds());
   	   log.info("this is sonatype task runing {}",count++);
   }
}
