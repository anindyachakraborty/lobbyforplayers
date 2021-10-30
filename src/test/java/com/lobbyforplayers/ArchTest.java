package com.lobbyforplayers;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.lobbyforplayers");

        noClasses()
            .that()
            .resideInAnyPackage("com.lobbyforplayers.service..")
            .or()
            .resideInAnyPackage("com.lobbyforplayers.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..com.lobbyforplayers.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
