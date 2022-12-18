package gmarmari.demo.microservices.products;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import org.junit.jupiter.api.Test;


public class CleanArchitectureTest {

    private static final String PROJECT_PACKAGES = "gmarmari.demo.microservices.products";

    @Test
    public void repositoriesAreOnlyAccessedByUseCases() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages(PROJECT_PACKAGES);

        DescribedPredicate<JavaClass> predicate = new DescribedPredicate<JavaClass>("Repositories are only accessed by use cases") {
            @Override
            public boolean test(JavaClass javaClass) {
                return javaClass.getSimpleName().endsWith("UseCase")
                        || javaClass.getSimpleName().endsWith("Test");
            }
        };

        ArchRule rule = ArchRuleDefinition.classes().that()
                .haveSimpleNameEndingWith("Repository")
                .should().onlyBeAccessed()
                .byClassesThat(predicate);


        rule.check(importedClasses);
    }

    @Test
    public void servicesAreOnlyAccessedByAdapters() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages(PROJECT_PACKAGES);

        DescribedPredicate<JavaClass> predicate = new DescribedPredicate<JavaClass>("Services are only accessed by adapters") {
            @Override
            public boolean test(JavaClass javaClass) {
                return javaClass.getSimpleName().endsWith("Adapter")
                        || javaClass.getSimpleName().endsWith("Test");
            }
        };

        ArchRule rule = ArchRuleDefinition.classes().that()
                .haveSimpleNameEndingWith("Service")
                .should().onlyBeAccessed()
                .byClassesThat(predicate);

        rule.check(importedClasses);
    }

    @Test
    public void adaptersAreOnlyAccessedByControllers() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages(PROJECT_PACKAGES);

        DescribedPredicate<JavaClass> predicate = new DescribedPredicate<JavaClass>("Adapters are only accessed by controllers (or from self)") {
            @Override
            public boolean test(JavaClass javaClass) {
                return javaClass.getSimpleName().endsWith("Controller")
                        || javaClass.getSimpleName().endsWith("Adapter")
                        || javaClass.getSimpleName().endsWith("Test");
            }
        };

        ArchRule rule = ArchRuleDefinition.classes().that()
                .haveSimpleNameEndingWith("Adapter")
                .should().onlyBeAccessed()
                .byClassesThat(predicate);

        rule.check(importedClasses);
    }



}
