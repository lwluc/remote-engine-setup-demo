package de.luc.weinbrecht.bpm.artefactdeployment.adapter.out.file

import io.kotest.matchers.collections.shouldContainOnly
import org.junit.jupiter.api.Test
import org.springframework.core.io.FileSystemResource

class ProcessFileReaderTest {

    private val classUnderTest = ProcessFileReader(listOf(
        FileSystemResource(javaClass.classLoader.getResource("test-artifacts/Test.bpmn").file),
        FileSystemResource(javaClass.classLoader.getResource("test-artifacts/Test.dmn").file),
        FileSystemResource(javaClass.classLoader.getResource("test-artifacts/Test.form").file),
        FileSystemResource(javaClass.classLoader.getResource("test-artifacts/Test.txt").file),
    ))

    @Test
    fun should_read_files_from_resources() {
        val result = classUnderTest.getDeploymentFiles()

        result.map { it.filename }.shouldContainOnly("Test.bpmn", "Test.dmn", "Test.form")
    }
}