/*
 * Copyright 2004,2005 The Apache Software Foundation.
 * Copyright 2006 International Business Machines Corp.
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
 */
package org.apache.axis2.jaxws.message.databinding.impl;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;

import org.apache.axiom.om.OMElement;
import org.apache.axis2.jaxws.ExceptionFactory;
import org.apache.axis2.jaxws.i18n.Messages;
import org.apache.axis2.jaxws.message.Block;
import org.apache.axis2.jaxws.message.MessageException;
import org.apache.axis2.jaxws.message.databinding.JAXBBlockContext;
import org.apache.axis2.jaxws.message.factory.JAXBBlockFactory;
import org.apache.axis2.jaxws.message.impl.BlockFactoryImpl;
import org.apache.axis2.jaxws.util.XMLRootElementUtil;

/**
 * JAXBBlockFactoryImpl
 * Creates a JAXBBlock
 */
public class JAXBBlockFactoryImpl extends BlockFactoryImpl implements JAXBBlockFactory {
	

	/**
	 * Default Constructor required for Factory 
	 */
	public JAXBBlockFactoryImpl() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.apache.axis2.jaxws.message.BlockFactory#createFrom(org.apache.axiom.om.OMElement, java.lang.Object, javax.xml.namespace.QName)
	 */
	public Block createFrom(OMElement omElement, Object context, QName qName) throws XMLStreamException, MessageException {
		// The context for a JAXBFactory must be non-null and should be a JAXBBlockContext.
		if (context == null) {
			throw ExceptionFactory.makeMessageException(Messages.getMessage("JAXBBlockFactoryErr1", "null"), null);
		} else if (context instanceof JAXBBlockContext) {
			;
		} else {
			throw ExceptionFactory.makeMessageException(Messages.getMessage("JAXBBlockFactoryErr1", context.getClass().getName()), null);
		}
		return new JAXBBlockImpl(omElement, (JAXBBlockContext) context, qName, this);
	}

	/* (non-Javadoc)
	 * @see org.apache.axis2.jaxws.message.BlockFactory#createFrom(java.lang.Object, java.lang.Object, javax.xml.namespace.QName)
	 */
	public Block createFrom(Object businessObject, Object context, QName qName) throws MessageException {
		
		// The context must be non-null and should be a JAXBBlockContext.
		// For legacy reasons, a JAXBContext is also supported (and wrapped into a JAXBBlockContext)
		if (context == null) {
			throw ExceptionFactory.makeMessageException(Messages.getMessage("JAXBBlockFactoryErr1", "null"), null);
		} else if (context instanceof JAXBBlockContext) {
			;
		} else {
			throw ExceptionFactory.makeMessageException(Messages.getMessage("JAXBBlockFactoryErr1", context.getClass().getName()), null);
		}
        
        // The business object must be either a JAXBElement or a block with an @XmlRootElement qname.  The best way
        // to verify this is to get the QName from the business object.
        QName bQName = XMLRootElementUtil.getXmlRootElementQName(businessObject);
        if (bQName == null) {
            throw ExceptionFactory.makeMessageException(Messages.getMessage("JAXBBlockFactoryErr2", businessObject.getClass().getName()), null);
        }
        
        // If the business obect qname does not match the parameter, use the business object qname
        if (!bQName.equals(qName)) {
            qName = bQName;
        }
		try {
			return new JAXBBlockImpl(businessObject, (JAXBBlockContext) context, qName, this);
		} catch (JAXBException e) {
			throw ExceptionFactory.makeMessageException(e);
		}
	}

}