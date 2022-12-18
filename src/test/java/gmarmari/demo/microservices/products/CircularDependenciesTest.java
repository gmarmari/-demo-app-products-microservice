package gmarmari.demo.microservices.products;

import jdepend.framework.JDepend;
import jdepend.framework.JavaPackage;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class CircularDependenciesTest {

    private static final String PROJECT_DIR = "../demo-app-products-microservice/target/classes";

    private final JDepend jdepend = new JDepend();

    @SuppressWarnings("unchecked")
    @Test
    void assertNoCircularDependencies() throws IOException {
        jdepend.addDirectory(PROJECT_DIR);
        jdepend.analyze();
        Collection<JavaPackage> javaPackages = jdepend.getPackages();
        List<String> javaPackagesWithCircularDependencies = new ArrayList<>();

        for (JavaPackage javaPackage : javaPackages) {
            if (javaPackage.containsCycle()) {
                javaPackagesWithCircularDependencies.add(javaPackage.getName());
            }
        }
        if (!javaPackagesWithCircularDependencies.isEmpty()) {
            throw new AssertionError("Circular dependencies in project in packages: " + javaPackagesWithCircularDependencies);
        }
    }





}
