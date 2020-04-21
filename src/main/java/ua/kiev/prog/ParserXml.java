package ua.kiev.prog;


import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

@Component
public class ParserXml {
    private static final String XML_File = "xmlFile.xml";

    public void parserClassToXml(Xml xml){
        File outputFile = new File(XML_File);
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Xml.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(xml, outputFile);
        }catch (JAXBException e){
            e.printStackTrace();
        }
    }
    public Xml xmlToClass(){
        File inputFile = new File(XML_File);
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Xml.class);
            Unmarshaller unmarshaller  = jaxbContext.createUnmarshaller();
            return (Xml) unmarshaller.unmarshal(inputFile);

        }catch (JAXBException e){
            e.printStackTrace();
        }return null;
    }
    public boolean fileExists(){
        return new File(XML_File).exists();
    }
}
