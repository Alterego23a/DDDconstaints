package parser;

import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import entity.XMI;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class XMLParserUtil {


    public static XMI parserXML(String filePath) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(filePath);
        JacksonXmlModule jacksonXmlModule = new JacksonXmlModule();
        XmlMapper xmlMapper = new XmlMapper(jacksonXmlModule);
        XMI xmi = xmlMapper.readValue(fileInputStream, XMI.class);
    return xmi;
    }
}
