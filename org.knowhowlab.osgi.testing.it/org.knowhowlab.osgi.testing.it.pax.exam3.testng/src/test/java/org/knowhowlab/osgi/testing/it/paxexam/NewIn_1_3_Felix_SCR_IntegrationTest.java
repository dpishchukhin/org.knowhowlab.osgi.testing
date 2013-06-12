package org.knowhowlab.osgi.testing.it.paxexam;

import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;

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
                mavenBundle("org.knowhowlab.osgi", "org.knowhowlab.osgi.testing.it.test.bundle", System.getProperty("project.version")).noUpdate().noStart());
    }
}
