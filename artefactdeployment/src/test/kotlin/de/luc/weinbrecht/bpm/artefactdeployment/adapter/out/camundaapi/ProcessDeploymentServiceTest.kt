package de.luc.weinbrecht.bpm.artefactdeployment.adapter.out.camundaapi

import de.luc.weinbrecht.bpm.artefactdeployment.domain.DeploymentException
import io.kotest.assertions.assertSoftly
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.string.shouldStartWith
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.core.io.FileSystemResource
import org.springframework.core.io.Resource
import org.springframework.web.reactive.function.client.WebClient
import java.io.File


class ProcessDeploymentServiceTest {

    companion object {

        lateinit var mockWebServer: MockWebServer

        @JvmStatic
        @BeforeAll
        fun setUp() {
            mockWebServer = MockWebServer()
            mockWebServer.start()
        }

        @JvmStatic
        @AfterAll
        fun tearDown() {
            mockWebServer.shutdown()
        }
    }

    private lateinit var classUnderTest: ProcessDeploymentService

    val fileMock: File = File.createTempFile("Test", ".bpmn")

    @BeforeEach
    fun setUpClassUnderTest() {
        val webclient = WebClient.builder()
            .baseUrl("http://localhost:${mockWebServer.port}")
            .build()
        classUnderTest = ProcessDeploymentService(webclient)
    }

    @Test
    fun should_deploy_via_post() {
        val resource: Resource = FileSystemResource(fileMock)
        mockWebServer.enqueue(MockResponse().setResponseCode(200))

        classUnderTest.deploy(resource)

        val request = mockWebServer.takeRequest()

        assertSoftly {
            request.method shouldBe "POST"
            request.headers.get("Content-Type") shouldStartWith "multipart/form-data"

            // TODO: Better way to test content
            val body = request.body.readUtf8()
            body shouldContain "deployment-name"
            body shouldContain "deploy-changed-only"
            body shouldContain "data"
        }
    }

    @Test
    fun should_throw_on_404() {
        val resource: Resource = FileSystemResource(fileMock)
        mockWebServer.enqueue(MockResponse().setResponseCode(404))

        val exception = shouldThrow<DeploymentException> {
            classUnderTest.deploy(resource)
        }
        exception.message shouldStartWith "Could not deploy process artifact "
    }

    @Test
    fun should_throw_on_500() {
        val resource: Resource = FileSystemResource(fileMock)
        mockWebServer.enqueue(MockResponse().setResponseCode(500))

        val exception = shouldThrow<DeploymentException> {
            classUnderTest.deploy(resource)
        }
        exception.message shouldStartWith "Could not deploy process artifact "
    }
}