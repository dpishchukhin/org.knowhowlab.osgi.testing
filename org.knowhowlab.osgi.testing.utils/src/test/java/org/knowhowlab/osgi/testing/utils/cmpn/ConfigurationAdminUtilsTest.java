package org.knowhowlab.osgi.testing.utils.cmpn;

import junit.framework.Assert;
import org.junit.Test;
import org.osgi.framework.Constants;
import org.osgi.framework.Filter;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author dpishchukhin
 */
public class ConfigurationAdminUtilsTest {
    @Test(expected = NullPointerException.class)
    public void testCreateConfigurationFilter_allNulls() throws Exception {
        ConfigurationAdminUtils.createConfigurationFilter(null, null, null);
    }

    @Test
    public void testCreateConfigurationFilter_pid() throws Exception {
        Filter filter = ConfigurationAdminUtils.createConfigurationFilter("test", null, null);
        Assert.assertNotNull(filter);
        Assert.assertEquals("(" + Constants.SERVICE_PID + "=test)", filter.toString());
    }

    @Test
    public void testCreateConfigurationFilter_pid_location() throws Exception {
        Filter filter = ConfigurationAdminUtils.createConfigurationFilter("test", null, "test2");
        Assert.assertNotNull(filter);
        Assert.assertEquals("(&(" + Constants.SERVICE_PID + "=test)(" + ConfigurationAdmin.SERVICE_BUNDLELOCATION + "=test2))", filter.toString());
    }

    @Test
    public void testCreateConfigurationFilter_pid_factory() throws Exception {
        Filter filter = ConfigurationAdminUtils.createConfigurationFilter("test", "test1", null);
        Assert.assertNotNull(filter);
        Assert.assertEquals("(&(" + Constants.SERVICE_PID + "=test)(" + ConfigurationAdmin.SERVICE_FACTORYPID + "=test1))", filter.toString());
    }
}
