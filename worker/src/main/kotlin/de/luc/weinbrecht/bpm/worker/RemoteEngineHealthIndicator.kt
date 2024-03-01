package de.luc.weinbrecht.bpm.worker

import org.springframework.boot.actuate.health.Health
import org.springframework.boot.actuate.health.HealthIndicator
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientException


@Component
class ExternalServiceHealthIndicator(
    private val webClient: WebClient,
): HealthIndicator {

    private val healthBuilder = Health.Builder()

    override fun health(): Health =
        if (isRemoteEngineUp()) healthBuilder.up().build()
        else healthBuilder.down().build()

    private fun isRemoteEngineUp(): Boolean {
        try {
            webClient.get()
                .uri("/version")
                .retrieve()
                .bodyToMono(String::class.java)
                .block()
            return true
        } catch (e: WebClientException) {
            return false
        }
    }
}