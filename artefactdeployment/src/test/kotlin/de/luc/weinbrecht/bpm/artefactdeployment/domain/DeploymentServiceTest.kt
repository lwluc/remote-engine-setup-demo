package de.luc.weinbrecht.bpm.artefactdeployment.domain

import de.luc.weinbrecht.bpm.artefactdeployment.usecase.out.DeploymentCommand
import de.luc.weinbrecht.bpm.artefactdeployment.usecase.out.ReadDeploymentFilesCommand
import io.mockk.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.core.io.Resource

class DeploymentServiceTest {

    private lateinit var classUnderTest: DeploymentService

    private val processFileReaderMock: ReadDeploymentFilesCommand = mockk<ReadDeploymentFilesCommand>()

    private val processDeploymentServiceMock: DeploymentCommand = mockk<DeploymentCommand>()

    @BeforeEach
    fun setUp() {
        classUnderTest = DeploymentService(processFileReaderMock, processDeploymentServiceMock)
    }

    @Test
    fun should_read_files_and_deploy() {
        val fileMock: Resource = mockk()
        every {  fileMock.filename } returns "Test.bpmn"
        every { processFileReaderMock.getDeploymentFiles() } returns listOf(fileMock)
        every { processDeploymentServiceMock.deploy(fileMock) } just runs

        classUnderTest.deploy()
    }

    @Test
    fun should_read_files_catch_error_on_deploy_and_continue() {
        val fileMock1: Resource = mockk()
        every {  fileMock1.filename } returns "Test 01.bpmn"
        val fileMock2: Resource = mockk()
        every {  fileMock2.filename } returns "Test 02.bpmn"
        every { processFileReaderMock.getDeploymentFiles() } returns listOf(fileMock1, fileMock2)
        every { processDeploymentServiceMock.deploy(fileMock1) } throws DeploymentException("Test", Exception())
        every { processDeploymentServiceMock.deploy(fileMock2) } just runs

        classUnderTest.deploy()

        verifySequence {
            processDeploymentServiceMock.deploy(fileMock1)
            processDeploymentServiceMock.deploy(fileMock2)
        }
    }
}