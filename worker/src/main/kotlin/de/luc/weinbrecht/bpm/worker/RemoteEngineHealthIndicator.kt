package de.luc.weinbrecht.bpm.worker

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.actuate.health.Health
import org.springframework.boot.actuate.health.HealthIndicator
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientException


@Component
class RemoteEngineHealthIndicator(
    private val webClient: WebClient,
    @Value("\${camunda.bpm.client.base-url}")
    private val camundaEngineBaseUrl: String = "http://localhost:8080/engine-rest"
): HealthIndicator {

    private val healthBuilder = Health.Builder()
        .withDetail("rest-api", camundaEngineBaseUrl)

    override fun health(): Health {
        val remoteEngineVersion = getRemoteEngineVersion()?.version
        healthBuilder.withDetail("camunda-version", remoteEngineVersion ?: "null")

        return if (remoteEngineVersion != null) healthBuilder.up().build()
        else healthBuilder.down().build()
    }

    private fun getRemoteEngineVersion(): CamundaVersion? {
        try {
            val version = webClient.get()
                .uri("/version")
                .retrieve()
                .bodyToMono(CamundaVersion::class.java)
                .block()
            return version
        } catch (e: WebClientException) {
            return null
        }
    }
}

data class CamundaVersion(
    val version: String
)
