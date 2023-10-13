package de.luc.weinbrecht.bpm.artefactdeployment.adapter.out.camundaapi

import de.luc.weinbrecht.bpm.artefactdeployment.domain.DeploymentException
import de.luc.weinbrecht.bpm.artefactdeployment.usecase.out.DeploymentCommand
import org.springframework.core.io.FileSystemResource
import org.springframework.http.client.MultipartBodyBuilder
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException
import java.io.File


class ProcessDeploymentService(
    private val webClient: WebClient
): DeploymentCommand {
    @Throws(DeploymentException::class)
    override fun deploy(file: File) {
        val body = MultipartBodyBuilder().apply {
            part("deployment-name", file.name.substringBefore("."))
            part("deploy-changed-only", "true")
            part("data", FileSystemResource(file))
        }

        try {
            webClient.post()
                .uri("/deployment/create")
                .body(BodyInserters.fromMultipartData(body.build()))
                .retrieve()
                .bodyToMono(String::class.java)
                .block()
        } catch (e: WebClientResponseException) {
            throw DeploymentException("Could not deploy process artifact (${file.name}) to the remote engine", e)
        }
    }
}
