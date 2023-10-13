package de.luc.weinbrecht.bpm.worker

import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription
import org.camunda.bpm.client.task.ExternalTask
import org.camunda.bpm.client.task.ExternalTaskHandler
import org.camunda.bpm.client.task.ExternalTaskService
import org.springframework.stereotype.Component

@Component
@ExternalTaskSubscription("log-info")
class LogInfoTopicWorker: ExternalTaskHandler, Logging {
    override fun execute(externalTask: ExternalTask, externalTaskService: ExternalTaskService) {
        log().info(
            "Logging info from external task: BusinessKey {}, variables {}",
            externalTask.businessKey,
            externalTask.allVariables
        )
        externalTaskService.complete(externalTask)
    }
}