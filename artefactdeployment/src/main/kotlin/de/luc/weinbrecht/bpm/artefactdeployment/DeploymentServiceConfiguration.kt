package de.luc.weinbrecht.bpm.artefactdeployment

import de.luc.weinbrecht.bpm.artefactdeployment.adapter.out.camundaapi.ProcessDeploymentService
import de.luc.weinbrecht.bpm.artefactdeployment.adapter.out.file.ProcessFileReader
import de.luc.weinbrecht.bpm.artefactdeployment.domain.DeploymentService
import de.luc.weinbrecht.bpm.artefactdeployment.usecase.`in`.ProcessArtefactDeployment
import de.luc.weinbrecht.bpm.artefactdeployment.usecase.out.DeploymentCommand
import de.luc.weinbrecht.bpm.artefactdeployment.usecase.out.ReadDeploymentFilesCommand
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource
import org.springframework.web.reactive.function.client.WebClient

@Configuration
internal open class DeploymentServiceConfiguration {

    @Bean
    open fun readDeploymentFilesCommand(
        @Value("\${process-artifacts.base-path:classpath*:/process/**/*}")
        resources: Array<Resource>
    ): ReadDeploymentFilesCommand =
        ProcessFileReader(resources.toList())

    @Bean
    open fun webClient(
        @Value("\${camunda.bpm.client.base-url}")
        camundaEngineBaseUrl: String = "http://localhost:8080/engine-rest"
    ): WebClient =
        WebClient
            .builder()
            .baseUrl(camundaEngineBaseUrl)
            .build()

    @Bean
    open fun deploymentCommand(webClient: WebClient): DeploymentCommand = ProcessDeploymentService(webClient)

    @Bean
    open fun processArtefactDeployment(
        readDeploymentFilesCommand: ReadDeploymentFilesCommand,
        deploymentCommand: DeploymentCommand,
        ): ProcessArtefactDeployment = DeploymentService(readDeploymentFilesCommand, deploymentCommand)
}