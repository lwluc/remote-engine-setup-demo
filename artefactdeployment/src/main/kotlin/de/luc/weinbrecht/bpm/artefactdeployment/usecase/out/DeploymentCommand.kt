package de.luc.weinbrecht.bpm.artefactdeployment.usecase.out

import de.luc.weinbrecht.bpm.artefactdeployment.domain.DeploymentException
import org.springframework.core.io.Resource

fun interface DeploymentCommand {
    @Throws(DeploymentException::class)
    fun deploy(file: Resource)
}