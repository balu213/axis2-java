package org.apache.axis2.deployment;

import junit.framework.TestCase;
import org.apache.axis2.AxisFault;
import org.apache.axis2.context.ConfigurationContextFactory;
import org.apache.axis2.description.AxisService;
import org.apache.axis2.engine.AxisConfiguration;
import org.apache.axis2.phaseresolver.PhaseException;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
/*
* Copyright 2004,2005 The Apache Software Foundation.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*
*
*/

public class OperationExcludeTest extends TestCase {
    AxisConfiguration er;

    public void testExcludeOperations() throws PhaseException,
            DeploymentException,
            AxisFault,
            XMLStreamException {
        String filename = System.getProperty("basedir")+"/test-resources/deployment/exculeRepo";
        er = ConfigurationContextFactory.createConfigurationContextFromFileSystem(filename, null)
                .getAxisConfiguration();
        AxisService service = er.getService("excludeService");
        assertNotNull(service);
        assertNotNull(service.getOperation(new QName("deduc")));
        assertNull(service.getOperation(new QName("add")));
    }

}

