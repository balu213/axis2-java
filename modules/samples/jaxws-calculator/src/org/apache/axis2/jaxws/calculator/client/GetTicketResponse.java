/*
* Licensed to the Apache Software Foundation (ASF) under one
* or more contributor license agreements. See the NOTICE file
* distributed with this work for additional information
* regarding copyright ownership. The ASF licenses this file
* to you under the Apache License, Version 2.0 (the
* "License"); you may not use this file except in compliance
* with the License. You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied. See the License for the
* specific language governing permissions and limitations
* under the License.
*/

package org.apache.axis2.jaxws.calculator.client;

import javax.xml.ws.wsaddressing.W3CEndpointReference;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getTicketResponse", namespace = "http://calculator.jaxws.axis2.apache.org", propOrder = {
        "_return"
        })
public class GetTicketResponse {

    @XmlElement(name = "return")
    protected W3CEndpointReference _return;

    /**
     * Gets the value of the return property.
     *
     * @return possible object is
     *         {@link W3CEndpointReference }
     */
    public W3CEndpointReference getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     *
     * @param value allowed object is
     *              {@link W3CEndpointReference }
     */
    public void setReturn(W3CEndpointReference value) {
        this._return = value;
    }

}