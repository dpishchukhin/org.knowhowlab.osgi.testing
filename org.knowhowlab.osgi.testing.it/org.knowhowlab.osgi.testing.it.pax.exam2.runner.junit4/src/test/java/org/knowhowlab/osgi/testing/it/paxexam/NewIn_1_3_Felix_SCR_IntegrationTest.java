package org.knowhowlab.osgi.testing.it.paxexam;

import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;

import static org.ops4j.pax.exam.CoreOptions.mavenBundle;
import static org.ops4j.pax.exam.OptionUtils.combine;

/**
 * Felix SCR tests
 *
 * @author dpishchukhin
 */
public class NewIn_1_3_Felix_SCR_IntegrationTest extends AbstractTest {

    /**
     * Install test bundle and Felix SCR
     *
     * @return config
     */
    @Configuration
    public static Option[] customTestConfiguration() {
        return combine(baseConfiguration(),
                mavenBundle().groupId("org.apache.felix").artifactId("org.apache.felix.eventadmin").version("1.3.2"),
                mavenBundle().groupId("org.knowhowlab.osgi").artifactId("org.knowhowlab.osgi.testing.it.test.bundle").
                        version(System.getProperty("project.version")).noUpdate().noStart());
    }
}
