package org.cloudbus.cloudsim.container.utils;

import com.google.common.base.Splitter;
import com.google.common.base.StandardSystemProperty;
import com.google.common.collect.Lists;
import com.google.common.io.ByteStreams;
import com.google.common.io.CharStreams;
import com.google.common.io.Resources;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.Properties;

public class ResourceFinder {

    public static BufferedReader readResourceBufferedReader(String fileName) {
        InputStream in = null;
        try {
            URL url = Resources.getResource(fileName);
            in = url.openStream();
            return new BufferedReader(new InputStreamReader(in));
        } catch (IOException e) {
            return null;
        }
    }

    public static Document readXml(String fileName) throws JDOMException, IOException {
        SAXBuilder builder = new SAXBuilder();
        InputStream in = findResources(fileName);
        if (in != null) {
            Document doc = builder.build(in);
            in.close();
            return doc;
        } else {
            throw new IOException("can't find " + fileName + " in the classpath");
        }
    }

    public static InputStream findResources(String fileName) throws IOException {
        URL url;
        try {
            url = Resources.getResource(StandardSystemProperty.FILE_SEPARATOR.value() + fileName);
        } catch (IllegalArgumentException e) {
            try {
                url = Resources.getResource(fileName);
            } catch (IllegalArgumentException e1) {
                throw new IOException("can't find " + fileName + " in the classpath");
            }
        }
        if (url != null) {
            return url.openStream();
        } else {
            throw new IOException("something is wrong when loading the file " + fileName);
        }
    }

    /**
     * build a Properties object from a properties file.
     *
     * @param propertiesPath properties file path
     * @return a Properties object which has loaded the given resource file.
     */
    public static Properties buildProperties(String propertiesPath) {
        InputStream inputStream = null;
        Properties property = new Properties();
        try {
            inputStream = findResources(propertiesPath);
            property.load(inputStream);
            return property;
        } catch (Exception e) {
            return property;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                }
            }
        }
    }

    /**
     * read resource file
     *
     * @param resource resource file path
     * @return resource file content.
     */
    public static List<String> readResource(String resource) {
        InputStream in;
        try {
            in = ResourceFinder.findResources(resource);
        } catch (IOException e) {
            return Lists.newArrayListWithCapacity(0);
        }
        InputStreamReader inr = new InputStreamReader(in);
        try {
            return Splitter.on("\n").splitToList(CharStreams.toString(inr));
        } catch (IOException e) {
            return Lists.newArrayListWithCapacity(0);
        } finally {
            try {
                in.close();
            } catch (IOException e) {
            }
        }
    }

    /**
     * read resource into a byte array
     *
     * @param resource resource path
     * @return resource content as a byte array
     * @throws IOException if something is wrong when reading the bytes..
     */
    public static byte[] readResourcesAsBytes(String resource) throws IOException {
        InputStream in;
        try {
            in = ResourceFinder.findResources(resource);
        } catch (IOException e) {
            return new byte[0];
        }

        byte[] data;

        try {
            data = ByteStreams.toByteArray(in);
            return data;
        } catch (IOException e) {
            throw new IOException("failed to read " + resource + " into bytes due to " + e);
        } finally {
            if (in != null) try {
                in.close();
            } catch (IOException e) {
            }
        }
    }
}
