package de.luc.weinbrecht.bpm.artefactdeployment.adapter.out.file

import io.kotest.matchers.collections.shouldContainOnly
import org.junit.jupiter.api.Test

class ProcessFileReaderTest {

    private val classUnderTest = ProcessFileReader()

    @Test
    fun should_read_files_from_resources() {
        val result = classUnderTest.getDeploymentFiles()

        result.map { it.name }.shouldContainOnly("Test.bpmn", "Test.dmn", "Test.form")
    }
}