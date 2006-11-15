
package org.apache.axis2.jaxws.sample.faultsservice;

import org.test.polymorphicfaults.*;
import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAXWS SI.
 * JAX-WS RI 2.0_01-b15-fcs
 * Generated source version: 2.0
 * 
 */
@WebFault(name = "InvalidTickerFault", targetNamespace = "http://org/test/polymorphicfaults")
public class InvalidTickerFault
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private String faultInfo;

    /**
     * 
     * @param message
     * @param faultInfo
     */
    public InvalidTickerFault(String message, String faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param cause
     * @param message
     * @param faultInfo
     */
    public InvalidTickerFault(String message, String faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: java.lang.String
     */
    public String getFaultInfo() {
        return faultInfo;
    }

}
