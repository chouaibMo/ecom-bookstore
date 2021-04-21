package fr.uga.ecom.bookstore;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("fr.uga.ecom.bookstore");

        noClasses()
            .that()
                .resideInAnyPackage("fr.uga.ecom.bookstore.service..")
            .or()
                .resideInAnyPackage("fr.uga.ecom.bookstore.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..fr.uga.ecom.bookstore.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
