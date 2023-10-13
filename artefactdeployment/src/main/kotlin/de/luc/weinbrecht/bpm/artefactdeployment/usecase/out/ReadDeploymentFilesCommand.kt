package de.luc.weinbrecht.bpm.artefactdeployment.usecase.out

import java.io.File

fun interface ReadDeploymentFilesCommand {
    fun getDeploymentFiles(): List<File>
}