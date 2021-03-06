package com.trendyol.scheduler.jobs;

import com.trendyol.scheduler.domain.ScheduledJob;
import com.trendyol.scheduler.service.JobSynchronizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class InternalJobExecutorTemplate {

    private static final Logger LOGGER = LoggerFactory.getLogger(InternalJobExecutorTemplate.class);

    private final JobSynchronizer jobSynchronizer;

    public InternalJobExecutorTemplate(JobSynchronizer jobSynchronizer) {
        this.jobSynchronizer = jobSynchronizer;
    }

    public void run(ScheduledJob scheduledJob, Runnable runnable) {
        boolean assignableToThisExecution = jobSynchronizer.isAssignableToThisExecution(scheduledJob);
        if (assignableToThisExecution) {
            LOGGER.debug("Internal task is being run. Internal Scheduled Job: '{}'", scheduledJob.getName());
            runnable.run();
            LOGGER.debug("Internal task completed successfully. Internal Scheduled Job: '{}'", scheduledJob.getName());
        } else {
            LOGGER.warn("This Internal Task is assigned to other instance. Internal Scheduled Job: '{}'", scheduledJob.getName());
        }
    }
}
