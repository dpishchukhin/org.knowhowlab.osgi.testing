## Introduction

OSGi specific assertions and utility classes that help to write OSGi integration/system tests.

## Usage

### Add Maven dependencies:
    <dependency>
        <groupId>org.knowhowlab.osgi</groupId>
        <artifactId>org.knowhowlab.osgi.testing.utils</artifactId>
        <version>1.0.0</version>
    </dependency>
    <dependency>
        <groupId>org.knowhowlab.osgi</groupId>
        <artifactId>org.knowhowlab.osgi.testing.utils</artifactId>
        <version>1.0.0</version>
    </dependency>

### Add dependency in PaxExam tests
    mavenBundle().groupId("org.knowhowlab.osgi").artifactId("org.knowhowlab.osgi.testing.utils").version("1.0.0"),
    mavenBundle().groupId("org.knowhowlab.osgi").artifactId("org.knowhowlab.osgi.testing.assertions").version("1.0.0")

## There is a comparison of the same test with and without OSGiLab testing assertions and utils.

### With

    @Test
    public void test_With_OSGiAssertions() throws BundleException, InvalidSyntaxException {
        // asserts that test bundle is installed
        assertBundleAvailable("org.osgilab.testing.it.commons.test.bundle");
        // asserts that test bundle is resolved
        assertBundleState(Bundle.RESOLVED, "org.osgilab.testing.it.commons.test.bundle");
        // gets bundle instance
        Bundle bundle = findBundle(bc, "org.osgilab.testing.it.commons.test.bundle");
        // asserts that test service is unavailable
        assertServiceUnavailable("org.osgi.testing.it.commons.testbundle.service.Echo");
        // start bundle
        bundle.start();
        // asserts that test bundle is active
        assertBundleState(Bundle.ACTIVE, "org.osgilab.testing.it.commons.test.bundle");
        // asserts that test service is available within 2 seconds
        assertServiceAvailable("org.osgi.testing.it.commons.testbundle.service.Echo", 2, TimeUnit.SECONDS);
        // asserts that test service with custom properties is available
        assertServiceAvailable(and(create(Echo.class), eq("testkey", "testvalue")));
        // gets service by class and filter
        Echo echo = ServiceUtils.getService(bc, Echo.class, eq("testkey", "testvalue"));
        // asserts service method call
        Assert.assertEquals("test", echo.echo("test"));
        // stops bundle
        bundle.stop();
        // asserts that test bundle is resolved
        assertBundleState(Bundle.RESOLVED, "org.osgilab.testing.it.commons.test.bundle");
        // asserts that test service is unregistered
        assertServiceUnavailable(Echo.class);
    }

### Without:

    @Test
    public void test_Without_OSGiAssertions() throws BundleException, InterruptedException, InvalidSyntaxException {
        ServiceTracker packageAdminTracker = new ServiceTracker(bc, PackageAdmin.class.getName(), null);
        packageAdminTracker.open();
        PackageAdmin packageAdmin = (PackageAdmin) packageAdminTracker.getService();
        Assert.assertNotNull(packageAdmin);
        packageAdminTracker.close();
        Bundle[] bundles = packageAdmin.getBundles("org.osgilab.testing.it.commons.test.bundle", null);
        // asserts that test bundle is installed
        Assert.assertNotNull(bundles);
        Assert.assertTrue(bundles.length > 0);
        // gets bundle instance
        Bundle bundle = bundles[0];
        // asserts that test bundle is resolved
        Assert.assertEquals(Bundle.RESOLVED, bundle.getState());
        ServiceTracker serviceTracker1 = new ServiceTracker(bc, "org.osgi.testing.it.commons.testbundle.service.Echo", null);
        serviceTracker1.open();
        Assert.assertEquals(0, serviceTracker1.size());
        // start bundle
        bundle.start();
        // asserts that test bundle is active
        Assert.assertEquals(Bundle.ACTIVE, bundle.getState());
        // asserts that test service is available within 2 seconds
        Assert.assertNotNull(serviceTracker1.waitForService(2000));
        // asserts that test service with custom properties is available
        ServiceTracker serviceTracker2 = new ServiceTracker(bc, FrameworkUtil.createFilter(
                "(&(" + Constants.OBJECTCLASS + "=org.osgi.testing.it.commons.testbundle.service.Echo)" +
                        "(testkey=testvalue))"), null);
        serviceTracker2.open();
        Assert.assertTrue(serviceTracker2.size() > 0);
        // gets service by class and filter
        Echo echo = (Echo) serviceTracker2.getService();
        // asserts service method call
        Assert.assertEquals("test", echo.echo("test"));
        // stops bundle
        bundle.stop();
        // asserts that test bundle is resolved
        Assert.assertEquals(Bundle.RESOLVED, bundle.getState());
        // asserts that test service is unregistered
        Assert.assertEquals(0, serviceTracker1.size());
    }


As you can see with OSGi assertions and utils you can concentrate on your functionality testing without any low-level OSGi API calls.

## Blog with more samples and tutorials

[http://knowhowlab.org](http://knowhowlab.org)