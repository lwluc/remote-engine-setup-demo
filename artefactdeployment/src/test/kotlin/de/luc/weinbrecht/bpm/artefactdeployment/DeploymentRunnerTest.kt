package de.luc.weinbrecht.bpm.artefactdeployment

import de.luc.weinbrecht.bpm.artefactdeployment.usecase.`in`.ProcessArtefactDeployment
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DeploymentRunnerTest {

    private val processArtefactDeploymentMock: ProcessArtefactDeployment = mockk<ProcessArtefactDeployment>()

    private lateinit var classUnderTest: DeploymentRunner

    @BeforeEach
    fun setUp() {
        classUnderTest = DeploymentRunner(processArtefactDeploymentMock)
    }

    @Test
    fun should_deploy_on_run() {
        every { processArtefactDeploymentMock.deploy() } just runs

        classUnderTest.run()
    }

    @Test
    fun should_catch_exception() {
        every { processArtefactDeploymentMock.deploy() } throws RuntimeException("Test")

        classUnderTest.run()
    }
}