package gmarmari.demo.microservices.products;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

public class ServicesAreTransactionalTest {

    @Test
    public void servicesAreTransactional() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("gmarmari.demo.microservices.products.services.usecase");

        ArchRule rule = ArchRuleDefinition.classes().that()
                .areAnnotatedWith(Service.class)
                .should().beAnnotatedWith(Transactional.class);
        rule.check(importedClasses);
    }
}
