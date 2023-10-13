package de.luc.weinbrecht.bpm.artefactdeployment.domain

import de.luc.weinbrecht.bpm.artefactdeployment.usecase.out.DeploymentCommand
import de.luc.weinbrecht.bpm.artefactdeployment.usecase.out.ReadDeploymentFilesCommand
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
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
}