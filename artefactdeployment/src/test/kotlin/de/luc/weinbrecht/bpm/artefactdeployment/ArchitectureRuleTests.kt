package de.luc.weinbrecht.bpm.artefactdeployment

import com.tngtech.archunit.core.domain.JavaClasses
import com.tngtech.archunit.core.importer.ClassFileImporter
import com.tngtech.archunit.lang.ArchRule
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses
import org.junit.jupiter.api.Test

private const val DOMAIN = "..domain.."
private const val USE_CASE = "..usecase.."
private const val ADAPTER = "..adapter.."

interface ArchitectureRuleTests {
    fun basePackage(): String

    @Test
    fun domain_use_case_should_not_import_adapters() {
        val classes: JavaClasses = ClassFileImporter().importPackages(basePackage())
        val rule: ArchRule = noClasses()
            .that().resideInAPackage(DOMAIN)
            .should().accessClassesThat().resideInAPackage(ADAPTER)
        rule.check(classes)
    }

    @Test
    fun use_case_should_not_import_adapters() {
        val classes: JavaClasses = ClassFileImporter().importPackages(basePackage())
        val rule: ArchRule = noClasses()
            .that().resideInAPackage(USE_CASE)
            .should().accessClassesThat().resideInAPackage(ADAPTER)
        rule.check(classes)
    }
}