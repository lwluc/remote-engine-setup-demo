package de.luc.weinbrecht.bpm.artefactdeployment.domain

class DeploymentException(message: String, e: Exception?) : RuntimeException(message, e)