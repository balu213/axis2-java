package org.apache.axis2.jaxws.sample;

import java.awt.Image;
import java.io.File;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.namespace.QName;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;
import javax.xml.ws.Service.Mode;
import javax.xml.ws.soap.SOAPBinding;

import junit.framework.TestCase;

import org.apache.axis2.jaxws.provider.DataSourceImpl;
import org.test.mtom.ImageDepot;
import org.test.mtom.ObjectFactory;
import org.test.mtom.SendImage;
import org.test.mtom.SendImageResponse;

public class MtomSampleTests extends TestCase {

    private static final QName QNAME_SERVICE = new QName("urn://mtom.test.org", "MtomSampleService");
    private static final QName QNAME_PORT    = new QName("urn://mtom.test.org", "MtomSample");
    private static final String URL_ENDPOINT = "http://localhost:8080/axis2/services/MtomSampleService";
    private static final String IMAGE_DIR = "test-resources"+File.separator+"image";   
    
    /*
     * Enable attachment Optimization through the SOAPBinding method 
     * -- setMTOMEnabled([true|false])
     * Using SOAP11
     */
    public void testSendImageAttachmentAPI11() throws Exception {
        System.out.println("----------------------------------");
        System.out.println("test: " + getName());
        
        String imageResourceDir = IMAGE_DIR;
        
        //Create a DataSource from an image 
        File file = new File(imageResourceDir+File.separator+"test.jpg");
        ImageInputStream fiis = new FileImageInputStream(file);
        Image image = ImageIO.read(fiis);
        DataSource imageDS = new DataSourceImpl("image/jpeg","test.jpg",image);
        
        //Create a DataHandler with the String DataSource object
        DataHandler dataHandler = new DataHandler(imageDS);
        
        //Store the data handler in ImageDepot bean
        ImageDepot imageDepot = new ObjectFactory().createImageDepot();
        imageDepot.setImageData(dataHandler);
        
        SendImage request = new ObjectFactory().createSendImage();
        request.setInput(imageDepot);
        
        //Create the necessary JAXBContext
        JAXBContext jbc = JAXBContext.newInstance("org.test.mtom");
        
        // Create the JAX-WS client needed to send the request
        Service service = Service.create(QNAME_SERVICE);
        service.addPort(QNAME_PORT, SOAPBinding.SOAP11HTTP_BINDING, URL_ENDPOINT);
        Dispatch<Object> dispatch = service.createDispatch(QNAME_PORT, jbc, Mode.PAYLOAD);
        
        //Enable attachment optimization
        SOAPBinding binding = (SOAPBinding) dispatch.getBinding();
        binding.setMTOMEnabled(true);
        
        SendImageResponse response = (SendImageResponse) dispatch.invoke(request);
        
        assertNotNull(response);
        assertNotNull(response.getOutput().getImageData());
    }
    
    /*
     * Enable attachment optimization using the SOAP11 binding
     * property for MTOM.
     */
    public void testSendImageAttachmentProperty11() throws Exception {
        System.out.println("----------------------------------");
        System.out.println("test: " + getName());
        
        String imageResourceDir = IMAGE_DIR;
        
        //Create a DataSource from an image 
        File file = new File(imageResourceDir+File.separator+"test.jpg");
        ImageInputStream fiis = new FileImageInputStream(file);
        Image image = ImageIO.read(fiis);
        DataSource imageDS = new DataSourceImpl("image/jpeg","test.jpg",image);
        
        //Create a DataHandler with the String DataSource object
        DataHandler dataHandler = new DataHandler(imageDS);
        
        //Store the data handler in ImageDepot bean
        ImageDepot imageDepot = new ObjectFactory().createImageDepot();
        imageDepot.setImageData(dataHandler);
        
        SendImage request = new ObjectFactory().createSendImage();
        request.setInput(imageDepot);
        
        //Create the necessary JAXBContext
        JAXBContext jbc = JAXBContext.newInstance("org.test.mtom");
        
        // Create the JAX-WS client needed to send the request with soap 11 binding
        // property for MTOM
        Service service = Service.create(QNAME_SERVICE);
        service.addPort(QNAME_PORT, SOAPBinding.SOAP11HTTP_MTOM_BINDING, URL_ENDPOINT);
        Dispatch<Object> dispatch = service.createDispatch(QNAME_PORT, jbc, Mode.PAYLOAD);
        
        SendImageResponse response = (SendImageResponse) dispatch.invoke(request);
        
        assertNotNull(response);
        assertNotNull(response.getOutput().getImageData());
    }
    
    /*
     * Enable attachment optimization using both the SOAP11 binding
     * property for MTOM and the Binding API
     */
    public void testSendImageAttachmentAPIProperty11() throws Exception {
        System.out.println("----------------------------------");
        System.out.println("test: " + getName());
        
        String imageResourceDir = IMAGE_DIR;
        
        //Create a DataSource from an image 
        File file = new File(imageResourceDir+File.separator+"test.jpg");
        ImageInputStream fiis = new FileImageInputStream(file);
        Image image = ImageIO.read(fiis);
        DataSource imageDS = new DataSourceImpl("image/jpeg","test.jpg",image);
        
        //Create a DataHandler with the String DataSource object
        DataHandler dataHandler = new DataHandler(imageDS);
        
        //Store the data handler in ImageDepot bean
        ImageDepot imageDepot = new ObjectFactory().createImageDepot();
        imageDepot.setImageData(dataHandler);
        
        SendImage request = new ObjectFactory().createSendImage();
        request.setInput(imageDepot);
        
        //Create the necessary JAXBContext
        JAXBContext jbc = JAXBContext.newInstance("org.test.mtom");
        
        // Create the JAX-WS client needed to send the request with soap 11 binding
        // property for MTOM
        Service service = Service.create(QNAME_SERVICE);
        service.addPort(QNAME_PORT, SOAPBinding.SOAP11HTTP_MTOM_BINDING, URL_ENDPOINT);
        Dispatch<Object> dispatch = service.createDispatch(QNAME_PORT, jbc, Mode.PAYLOAD);
        
        
        //Enable attachment optimization
        SOAPBinding binding = (SOAPBinding) dispatch.getBinding();
        binding.setMTOMEnabled(true);
        
        SendImageResponse response = (SendImageResponse) dispatch.invoke(request);
        
        assertNotNull(response);
        assertNotNull(response.getOutput().getImageData());
    }
    
    /*
     * Enable attachment optimization using both the SOAP12 binding
     * property for MTOM
     */
    
    public void testSendImageAttachmentProperty12() throws Exception {
        System.out.println("----------------------------------");
        System.out.println("test: " + getName());
        
        String imageResourceDir = IMAGE_DIR;
        
        //Create a DataSource from an image 
        File file = new File(imageResourceDir+File.separator+"test.jpg");
        ImageInputStream fiis = new FileImageInputStream(file);
        Image image = ImageIO.read(fiis);
        DataSource imageDS = new DataSourceImpl("image/jpeg","test.jpg",image);
        
        //Create a DataHandler with the String DataSource object
        DataHandler dataHandler = new DataHandler(imageDS);
        
        //Store the data handler in ImageDepot bean
        ImageDepot imageDepot = new ObjectFactory().createImageDepot();
        imageDepot.setImageData(dataHandler);
        
        SendImage request = new ObjectFactory().createSendImage();
        request.setInput(imageDepot);
        
        //Create the necessary JAXBContext
        JAXBContext jbc = JAXBContext.newInstance("org.test.mtom");
        
        // Create the JAX-WS client needed to send the request with soap 11 binding
        // property for MTOM
        Service service = Service.create(QNAME_SERVICE);
        service.addPort(QNAME_PORT, SOAPBinding.SOAP12HTTP_MTOM_BINDING, URL_ENDPOINT);
        Dispatch<Object> dispatch = service.createDispatch(QNAME_PORT, jbc, Mode.PAYLOAD);
        
        SendImageResponse response = (SendImageResponse) dispatch.invoke(request);
        
        assertNotNull(response);
        assertNotNull(response.getOutput().getImageData());
    }
    
    
    /*
     * Enable attachment optimization using both the SOAP12 binding API
     * for MTOM
     */
    public void testSendImageAttachmentAPI12() throws Exception {
        System.out.println("----------------------------------");
        System.out.println("test: " + getName());
        
        String imageResourceDir = IMAGE_DIR;
        
        //Create a DataSource from an image 
        File file = new File(imageResourceDir+File.separator+"test.jpg");
        ImageInputStream fiis = new FileImageInputStream(file);
        Image image = ImageIO.read(fiis);
        DataSource imageDS = new DataSourceImpl("image/jpeg","test.jpg",image);
        
        //Create a DataHandler with the String DataSource object
        DataHandler dataHandler = new DataHandler(imageDS);
        
        //Store the data handler in ImageDepot bean
        ImageDepot imageDepot = new ObjectFactory().createImageDepot();
        imageDepot.setImageData(dataHandler);
        
        SendImage request = new ObjectFactory().createSendImage();
        request.setInput(imageDepot);
        
        //Create the necessary JAXBContext
        JAXBContext jbc = JAXBContext.newInstance("org.test.mtom");
        
        // Create the JAX-WS client needed to send the request with soap 11 binding
        // property for MTOM
        Service service = Service.create(QNAME_SERVICE);
        service.addPort(QNAME_PORT, SOAPBinding.SOAP12HTTP_BINDING, URL_ENDPOINT);
        Dispatch<Object> dispatch = service.createDispatch(QNAME_PORT, jbc, Mode.PAYLOAD);
        
        
        //Enable attachment optimization
        SOAPBinding binding = (SOAPBinding) dispatch.getBinding();
        binding.setMTOMEnabled(true);
        
        SendImageResponse response = (SendImageResponse) dispatch.invoke(request);
        
        assertNotNull(response);
        assertNotNull(response.getOutput().getImageData());
    }
    
}
