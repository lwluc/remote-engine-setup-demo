package de.luc.weinbrecht.bpm.artefactdeployment.usecase.out

import org.springframework.core.io.Resource

fun interface ReadDeploymentFilesCommand {
    fun getDeploymentFiles(): List<Resource>
}