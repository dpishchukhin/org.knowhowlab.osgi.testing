/*
 * Copyright (c) 2010 Dmytro Pishchukhin (http://knowhowlab.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.osgi.testing.it.springosgi;

import org.knowhowlab.osgi.testing.assertions.OSGiAssert;
import org.springframework.osgi.test.AbstractConfigurableBundleCreatorTests;

/**
 * Abstract test with all initializations
 *
 * @author dmytro.pishchukhin
 */
public abstract class AbstractTest extends AbstractConfigurableBundleCreatorTests {
    protected String[] getTestBundlesNames() {
        return new String[]{"org.junit, com.springsource.junit, 3.8.2",
                "org.knowhowlab.osgi.testing, commons, 1.0.1-SNAPSHOT"};
    }

    @Override
    protected void onSetUp() throws Exception {
        OSGiAssert.init(bundleContext);
    }
}
