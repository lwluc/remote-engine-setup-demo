package de.luc.weinbrecht.bpm.artefactdeployment

import de.luc.weinbrecht.bpm.artefactdeployment.usecase.`in`.ProcessArtefactDeployment
import org.springframework.boot.CommandLineRunner

class DeploymentRunner(
    private val processArtefactDeployment: ProcessArtefactDeployment
): CommandLineRunner, Logging {

    override fun run(vararg args: String?) {
        log().debug("Executing command line runner to deploy process artefacts")
        processArtefactDeployment.deploy()
    }
}

