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
package org.apache.axis2.databinding;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.HashMap;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import org.apache.axiom.om.OMDataSource;
import org.apache.axiom.om.OMDataSourceExt;
import org.apache.axiom.om.OMException;
import org.apache.axiom.om.OMOutputFormat;
import org.apache.axiom.om.QNameAwareOMDataSource;
import org.apache.axiom.om.util.StAXUtils;
import org.apache.axis2.databinding.utils.writer.MTOMAwareOMBuilder;

public abstract class AbstractADBDataSource implements OMDataSourceExt, QNameAwareOMDataSource {
    protected QName parentQName;
    
    HashMap map = null;  // Map of properties

    public AbstractADBDataSource(QName parentQName) {
        this.parentQName = parentQName;
    }

    public final String getLocalName() {
        return parentQName.getLocalPart();
    }

    public final String getNamespaceURI() {
        return parentQName.getNamespaceURI();
    }

    public final String getPrefix() {
        return parentQName.getPrefix();
    }

    /**
     * @param output
     * @param format
     * @throws XMLStreamException
     * @see OMDataSource#serialize(java.io.OutputStream, org.apache.axiom.om.OMOutputFormat)
     */
    public void serialize(OutputStream output, OMOutputFormat format) throws XMLStreamException {
        XMLStreamWriter xmlStreamWriter = StAXUtils.createXMLStreamWriter(output);
        serialize(xmlStreamWriter);
        xmlStreamWriter.flush();
    }

    /**
     * @param writer
     * @param format
     * @throws XMLStreamException
     * @see OMDataSource#serialize(java.io.Writer, org.apache.axiom.om.OMOutputFormat)
     */
    public void serialize(Writer writer, OMOutputFormat format) throws XMLStreamException {
        XMLStreamWriter xmlStreamWriter = StAXUtils.createXMLStreamWriter(writer);
        serialize(xmlStreamWriter);
        xmlStreamWriter.flush();
    }

    /**
     * @throws XMLStreamException
     * @see org.apache.axiom.om.OMDataSource#getReader()
     */
    public XMLStreamReader getReader() throws XMLStreamException {
        MTOMAwareOMBuilder mtomAwareOMBuilder = new MTOMAwareOMBuilder();
        serialize(mtomAwareOMBuilder);
        return mtomAwareOMBuilder.getOMElement().getXMLStreamReader();
    }

    /**
     * Returns true if reading the backing object is destructive.
     * An example of an object with a destructive read is an InputSteam.
     * The owning OMSourcedElement uses this information to detemine if OM tree
     * expansion is needed when reading the OMDataSourceExt.
     * @return boolean
     */
    public boolean isDestructiveRead() {
        return false;
    }
    
    /**
     * Returns true if writing the backing object is destructive.
     * An example of an object with a destructive write is an InputStream.
     * The owning OMSourcedElement uses this information to detemine if OM tree
     * expansion is needed when writing the OMDataSourceExt.
     * @return boolean
     */
    public boolean isDestructiveWrite() {
        return false;
    }
    
    /**
     * Returns a InputStream representing the xml data
     * @param encoding String encoding of InputStream
     * @return InputStream
     */
    public InputStream getXMLInputStream(String encoding) throws UnsupportedEncodingException {
        return new ByteArrayInputStream(getXMLBytes(encoding));
    }
    
    /**
     * Returns a byte[] representing the xml data
     * @param encoding String encoding of InputStream
     * @return byte[]
     * @see getXMLInputStream
     */
    public byte[] getXMLBytes(String encoding) throws UnsupportedEncodingException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        OMOutputFormat format = new OMOutputFormat();
        format.setCharSetEncoding(encoding);
        try {
            serialize(baos, format);
        } catch (XMLStreamException e) {
            new OMException(e);
        }
        return baos.toByteArray();
    }
    
    public OMDataSourceExt copy() {
        return null;
    }
    
    public Object getProperty(String key) {
        if (map == null) {
            return null;
        }
        return map.get(key);
    }

    public Object setProperty(String key, Object value) {
        if (map == null) {
            map = new HashMap();
        }
        return map.put(key, value);
    }

    public boolean hasProperty(String key) {
        if (map == null) {
            return false;
        } 
        return map.containsKey(key);
    }
}