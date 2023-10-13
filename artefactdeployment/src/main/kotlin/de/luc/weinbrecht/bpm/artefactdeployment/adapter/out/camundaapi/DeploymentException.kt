package de.luc.weinbrecht.bpm.artefactdeployment.adapter.out.camundaapi

class DeploymentException(message: String, e: Exception?) : RuntimeException(message, e)