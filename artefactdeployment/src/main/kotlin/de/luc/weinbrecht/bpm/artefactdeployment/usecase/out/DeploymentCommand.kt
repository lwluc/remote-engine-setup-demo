package de.luc.weinbrecht.bpm.artefactdeployment.usecase.out

import de.luc.weinbrecht.bpm.artefactdeployment.domain.DeploymentException
import java.io.File

fun interface DeploymentCommand {
    @Throws(DeploymentException::class)
    fun deploy(file: File)
}