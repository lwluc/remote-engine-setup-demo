package de.luc.weinbrecht.bpm.artefactdeployment.adapter.out.file

import de.luc.weinbrecht.bpm.artefactdeployment.usecase.out.ReadDeploymentFilesCommand
import java.io.File
import java.nio.file.Files

class ProcessFileReader: ReadDeploymentFilesCommand {
    override fun getDeploymentFiles(): List<File> {
        return File(this.javaClass.classLoader.getResource("")?.path ?: "").walkTopDown()
            .filter { !Files.isDirectory(it.toPath()) }
            .filter {
                it.path.endsWith(".bpmn") ||
                it.path.endsWith(".dmn") ||
                it.path.endsWith(".form") }
            .toList()
    }
}