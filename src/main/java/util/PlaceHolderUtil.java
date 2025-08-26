package util;

import model.PdfGenerator;
import org.docx4j.Docx4J;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;

import javax.xml.bind.JAXBException;
import java.io.*;
import java.util.Map;

public class PlaceHolderUtil {
    public static final String COMPANY_NAME_KEY = "{{companyName}}";
    public static final String JOB_POSITION_KEY = "{{jobPosition}}";
    
    private PlaceHolderUtil() {} // prevent instantiation
    
    public static Map<String,String> createPlaceHolderMap(String companyName, String jobPosition) {
        return Map.of(
                COMPANY_NAME_KEY, companyName,
                JOB_POSITION_KEY, jobPosition
        );
    }
    
    public static String buildCompanySuffix(String name) {
        String cleaned = name.replaceAll("\\W+", "");
        if (cleaned.length() < 8) {
            cleaned = String.format("%-8s", cleaned).replace(' ', 'x');
        }
        return cleaned.substring(0, Math.min(8, cleaned.length()));
    }
    
    public static void replace(File inputDocx, String outputPdfPath, PdfGenerator generator) throws Docx4JException, IOException, JAXBException, jakarta.xml.bind.JAXBException {
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(inputDocx);
        MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();
        
        String xml = documentPart.getXML();
        for (Map.Entry<String, String> entry : generator.getPlaceHolderMap().entrySet()) {
            xml = xml.replace(entry.getKey(), entry.getValue());
        }
        documentPart.getContent().clear();
        documentPart.setJaxbElement(
                (org.docx4j.wml.Document) org.docx4j.XmlUtils.unmarshalString(xml)
        );
        
        try (OutputStream os = new FileOutputStream(outputPdfPath)) {
            Docx4J.toPDF(wordMLPackage, os);
        }
    
    }
}