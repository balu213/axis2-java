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

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.axis2.jaxws.i18n.Messages;
import org.apache.axis2.jaxws.message.databinding.ClassFinder;
import org.apache.axis2.jaxws.util.ClassUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class ClassFinderImpl implements ClassFinder {
	private static final Log log = LogFactory.getLog(ClassFinderImpl.class);
	/* (non-Javadoc)
	 * @see org.apache.axis2.jaxws.message.databinding.ClassFinder#getClassesFromJarFile(java.lang.String, java.lang.ClassLoader)
	 */
	public ArrayList<Class> getClassesFromJarFile(String pkg, ClassLoader cl) throws ClassNotFoundException {
		try {
	    	ArrayList<Class> classes = new ArrayList<Class>();
	    	URLClassLoader ucl = (URLClassLoader)cl;
	        URL[] srcURL = ucl.getURLs();
	        String path = pkg.replace('.', '/');
	        //Read resources as URL from class loader.
	        for(URL url:srcURL){
	        	if("file".equals(url.getProtocol())){
	        		File f = new File(url.getPath());
	        		//If file is not of type directory then its a jar file
	        		if(f.exists() && !f.isDirectory()){
	        			try{
		        			JarFile jf = new JarFile(f);
		        			Enumeration<JarEntry> entries = jf.entries();
		        			//read all entries in jar file
		        			while(entries.hasMoreElements()){
		        				JarEntry je = entries.nextElement();
		        				String clazzName = je.getName();
		        				if(clazzName!=null && clazzName.endsWith(".class")){
			        				//Add to class list here.
			        				clazzName = clazzName.substring (0, clazzName.length() - 6);
			        	            clazzName = clazzName.replace ('/', '.').replace ('\\', '.').replace (':', '.');
			        	            //We are only going to add the class that belong to the provided package.
			        				if(clazzName.startsWith(pkg)){
			        					try {
		        							 Class clazz = Class.forName(clazzName, 
		        	                                    false, 
		        	                                    Thread.currentThread().getContextClassLoader());
		        	                            // Don't add any interfaces or JAXWS specific classes.  
		        	                            // Only classes that represent data and can be marshalled 
		        	                            // by JAXB should be added.
		        	                         if(!clazz.isInterface()
		        	                                 && ClassUtils.getDefaultPublicConstructor(clazz) != null
		        	                                 && !ClassUtils.isJAXWSClass(clazz)){
		        	                             if (log.isDebugEnabled()) {
		        	                                 log.debug("Adding class: " + clazzName);
		        	                             }
		        	                             classes.add(clazz);
		        	                             
		        	                         }
		        	             	        //catch Throwable as ClassLoader can throw an NoClassDefFoundError that
		        	             	        //does not extend Exception, so lets catch everything that extends Throwable
		        	                        //rather than just Exception.
		        	                       }catch (Throwable e) {
		        	                         if (log.isDebugEnabled()) {
		        	                             log.debug("Tried to load class " + clazzName + " while constructing a JAXBContext.  This class will be skipped.  Processing Continues." );
		        	                             log.debug("  The reason that class could not be loaded:" + e.toString());
		        	                         }
		        	                         e.printStackTrace();
		        	                       }
		        					}
		        				}
		        			}
	        			}catch(IOException e){
	        				throw new ClassNotFoundException(Messages.getMessage("ClassUtilsErr4"));
	        			}
	        		}
	        	}
	        }
	        return classes;
	    } catch (Exception e) {
	           throw new ClassNotFoundException(e.getMessage());
	    }
	   
	}

}
