package de.luc.weinbrecht.bpm.artefactdeployment

import org.springframework.context.annotation.Import

/**
 * Enable automatic deployment of *.bpmn, *.dmn, *.form resources during application startup.
 * Logs an error if the deployment is not possible. Does not stop the application from starting.
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Import(*[DeploymentServiceConfiguration::class, DeploymentRunner::class])
annotation class EnableDeployment()
