package de.luc.weinbrecht.bpm.artefactdeployment.domain

import de.luc.weinbrecht.bpm.artefactdeployment.usecase.out.DeploymentCommand
import de.luc.weinbrecht.bpm.artefactdeployment.usecase.out.ReadDeploymentFilesCommand
import io.mockk.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File

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
        val fileMock: File = mockk<File>()
        every { fileMock.name } returns "Test"
        every { processFileReaderMock.getDeploymentFiles() } returns listOf(fileMock)
        every { processDeploymentServiceMock.deploy(fileMock) } just runs

        classUnderTest.deploy()
    }

    @Test
    fun should_read_files_catch_error_on_deploy_and_continue() {
        val fileMock1: File = mockk<File>()
        every { fileMock1.name } returns "Test 01"
        val fileMock2: File = mockk<File>()
        every { fileMock2.name } returns "Test 02"
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