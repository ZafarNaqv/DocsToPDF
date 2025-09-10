package util;

import model.PdfTemplateGenerator;
import org.docx4j.Docx4J;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBException;
import java.io.*;
import java.util.Map;

public class PlaceHolderUtil {
    private static final Logger logger = LoggerFactory.getLogger(PlaceHolderUtil.class);
    public static final String COMPANY_NAME_KEY = "{{companyName}}";
    public static final String JOB_POSITION_KEY = "{{jobPosition}}";
    
    private PlaceHolderUtil() {} // prevent instantiation
    
    public static Map<String,String> createPlaceHolderMap(String companyName, String jobPosition) {
        return Map.of(
                COMPANY_NAME_KEY, escapeXml(companyName),
                JOB_POSITION_KEY, escapeXml(jobPosition)
        );
    }
    
    public static String escapeXml(String input) {
        if (input == null) return null;
        return input
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&apos;");
    }
    
    public static String buildCompanySuffix(String name) {
        String cleaned = name.replaceAll("\\W+", "");
        if (cleaned.length() < 8) {
            cleaned = String.format("%-8s", cleaned).replace(' ', 'x');
        }
        return cleaned.substring(0, Math.min(8, cleaned.length()));
    }
    
    public static void replace(File inputDocx, String outputPdfPath, PdfTemplateGenerator generator) throws Docx4JException, IOException, JAXBException, jakarta.xml.bind.JAXBException {
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(inputDocx);
        MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();
        int placeHoldersCount = 0;
        String xml = documentPart.getXML();
        for (Map.Entry<String, String> entry : generator.getPlaceHolderMap().entrySet()) {
            placeHoldersCount = StringUtils.countMatches(xml, entry.getKey());
            xml = xml.replace(entry.getKey(), entry.getValue());
        }
        if(placeHoldersCount > 0) {
            logger.info("Replaced {} placeholders", placeHoldersCount);
        }else{
            throw new IllegalArgumentException("No placeholders found. Kindly update docx with {{companyName}} and {{jobPosition}}");
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