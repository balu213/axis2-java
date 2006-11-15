
package org.apache.axis2.jaxws.sample.faults;

import javax.jws.WebService;
import javax.xml.ws.WebFault;
import javax.xml.ws.WebServiceClient;

import org.test.faults.FaultyWebServiceFault;


/**
 * This class was generated by the JAXWS SI.
 * JAX-WS RI 2.0_01-b15-fcs
 * Generated source version: 2.0
 * 
 */
@WebFault(faultBean="org.test.faults.FaultyWebServiceFault", name = "FaultyWebServiceFault", targetNamespace = "http://org/test/faults")
public class FaultyWebServiceFault_Exception
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private FaultyWebServiceFault faultInfo;
    
    /**
     * 
     * @param faultInfo
     * @param message
     */
    public FaultyWebServiceFault_Exception(String message, FaultyWebServiceFault faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param faultInfo
     * @param message
     * @param cause
     */
    public FaultyWebServiceFault_Exception(String message, FaultyWebServiceFault faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: duke.org.FaultyWebServiceFault
     */
    public FaultyWebServiceFault getFaultInfo() {
        return faultInfo;
    }

}
