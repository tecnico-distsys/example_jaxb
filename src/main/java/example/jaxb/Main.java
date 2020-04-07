package example.jaxb;

import java.io.*;
import java.util.List;

import javax.xml.bind.*;

import mydomain.message.*;

import static javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI;
import javax.xml.namespace.QName;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Schema;

import org.xml.sax.SAXException;


public class Main {

    public static void main( String[] args ) throws Exception {

        // Check arguments
        if (args.length < 1) {
            System.err.println("Argument(s) missing!");
            System.err.printf("Usage: java %s marshal|unmarshal ...%n", Main.class.getName());
            return;
        }

        if(args[0].equalsIgnoreCase("marshal")) {
            System.out.print("marshal");
            String outputFile = null;
            if(args.length < 2) {
                System.out.println(" to System.out");
            } else {
                outputFile = args[1];
                System.out.println(" to file " + outputFile);
            }
            marshal(outputFile);

        } else if(args[0].equalsIgnoreCase("unmarshal")) {
            System.out.print("unmarshal");
            String inputFile = null, schemaFile = null;
            if(args.length < 2) {
                System.out.println(", missing input file");
            } else {
                inputFile = args[1];
                System.out.print(" from input file " + inputFile);
                if(args.length < 3) {
                    System.out.println(" with no schema validation");
                } else {
                    schemaFile = args[2];
                    System.out.println(" validating with schema file " + schemaFile);
                }
            }
            unmarshal(inputFile, schemaFile);
        } else {
            System.out.println("Action parameter " + args[0] + " is unknown!");
        }
    }

    @SuppressWarnings("unchecked")
    private static void marshal(String outputFile) throws JAXBException, FileNotFoundException {

        // create a JAXBContext
        JAXBContext jc = JAXBContext.newInstance( "mydomain.message" );

        // create Java XML-bound objects
        MessageType message = new MessageType();
        message.setId(49532);
        message.setFrom("Alice");
        message.setTo("Bob");
        message.setSubject("Lunch");
        message.setBody("How about lunch on friday?");

        // create XML element (a complex type cannot be instantiated by itself)
        JAXBElement jaxbElementMarshal = new JAXBElement(
            new QName("urn:mydomain:message", "message", "e"),
            mydomain.message.MessageType.class,
            message);

        // create a Marshaller and marshal
        Marshaller m = jc.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        if(outputFile == null) {
            m.marshal(jaxbElementMarshal, System.out);

        } else {
            OutputStream os = new FileOutputStream(outputFile);
            m.marshal(jaxbElementMarshal, os);
        }

    }

    private static void unmarshal(String inputFile, String schemaFile) throws JAXBException, SAXException {

        // input file must not be null
        if(inputFile == null)
            throw new IllegalArgumentException("inputFile");


        // create a JAXBContext
        JAXBContext jc = JAXBContext.newInstance( "mydomain.message" );

        // create an Unmarshaller
        Unmarshaller u = jc.createUnmarshaller();

        // set schema
        if(schemaFile != null) {
            SchemaFactory sf = SchemaFactory.newInstance(W3C_XML_SCHEMA_NS_URI);
            Schema schema = sf.newSchema(new File(schemaFile));
            u.setSchema(schema);
        }

        // unmarshal, get element and cast to expected type
        Object obj = u.unmarshal(new File(inputFile));
        JAXBElement jaxbElementUnmarshal = (JAXBElement) obj;
        MessageType message = (MessageType) jaxbElementUnmarshal.getValue();

        // print part of the read information
        System.out.println("message");
        System.out.println("id= " + message.getId());
        System.out.println("from= " + message.getFrom());
        System.out.println("to= " + message.getTo());
        System.out.println("subject= " + message.getSubject());
        System.out.println("body= " + message.getBody());

    }

}

