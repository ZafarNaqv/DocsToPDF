package util;

import org.docx4j.Docx4J;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class PlaceHolderUtil {
    private static final HashMap<String,String> placeHolderMap = new HashMap<>();
    public static final String COMPANY_NAME_KEY = "{{companyName}}";
    
    public PlaceHolderUtil(String companyName) {
        placeHolderMap.put(COMPANY_NAME_KEY,companyName);
    }
    
    public static String buildCompanySuffix(String name) {
        String cleaned = name.replaceAll("\\W+", "");
        if (cleaned.length() < 8    ) {
            cleaned = String.format("%-8s", cleaned).replace(' ', 'x');
        }
        return cleaned.substring(0, Math.min(8, cleaned.length()));
    }
    
    public static void replace(File inputDocx,String outputPdfPath) throws Docx4JException, IOException {
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(inputDocx);
        MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();
        
        String xml = documentPart.getXML();
        for (Map.Entry<String, String> entry : placeHolderMap.entrySet()) {
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