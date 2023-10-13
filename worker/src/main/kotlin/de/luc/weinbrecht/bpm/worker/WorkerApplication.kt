package de.luc.weinbrecht.bpm.worker

import de.luc.weinbrecht.bpm.artefactdeployment.EnableDeployment
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableDeployment
class WorkerApplication

fun main(args: Array<String>) {
	runApplication<WorkerApplication>(*args)
}
