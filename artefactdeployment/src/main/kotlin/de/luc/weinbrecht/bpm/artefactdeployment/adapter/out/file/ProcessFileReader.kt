package de.luc.weinbrecht.bpm.artefactdeployment.adapter.out.file

import de.luc.weinbrecht.bpm.artefactdeployment.usecase.out.ReadDeploymentFilesCommand
import org.springframework.core.io.Resource

class ProcessFileReader(
    private val resources: List<Resource>
): ReadDeploymentFilesCommand {

    override fun getDeploymentFiles(): List<Resource> {
        return resources
            .filter {
                it.filename?.endsWith(".bpmn") ?: false ||
                it.filename?.endsWith(".dmn") ?: false ||
                it.filename?.endsWith(".form") ?: false
            }
            .toList()
    }
}