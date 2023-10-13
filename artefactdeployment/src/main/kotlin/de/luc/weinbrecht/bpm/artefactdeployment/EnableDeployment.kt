package de.luc.weinbrecht.bpm.artefactdeployment

import org.springframework.context.annotation.Import

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Import(*[DeploymentServiceConfiguration::class, DeploymentRunner::class])
annotation class EnableDeployment()
