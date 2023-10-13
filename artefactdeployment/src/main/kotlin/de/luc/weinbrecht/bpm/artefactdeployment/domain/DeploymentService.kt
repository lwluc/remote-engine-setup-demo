package de.luc.weinbrecht.bpm.artefactdeployment.domain

import de.luc.weinbrecht.bpm.artefactdeployment.Logging
import de.luc.weinbrecht.bpm.artefactdeployment.adapter.out.camundaapi.DeploymentException
import de.luc.weinbrecht.bpm.artefactdeployment.log
import de.luc.weinbrecht.bpm.artefactdeployment.usecase.`in`.ProcessArtefactDeployment
import de.luc.weinbrecht.bpm.artefactdeployment.usecase.out.DeploymentCommand
import de.luc.weinbrecht.bpm.artefactdeployment.usecase.out.ReadDeploymentFilesCommand

class DeploymentService(
    private val processFileReader: ReadDeploymentFilesCommand,
    private val processDeploymentService: DeploymentCommand,
): ProcessArtefactDeployment, Logging {
    override fun deploy() {
        val deployments = processFileReader.getDeploymentFiles()
        log().debug("Found {} artifacts to deploy", deployments.size)

        deployments.forEach {
            log().trace("Deploying artifact {}", it.name)

            try {
                processDeploymentService.deploy(it)
            } catch (e: DeploymentException) {
                log().error("Could not deploy artifact {}", it.name, e)
            }
        }
    }
}