package util;
import model.PdfTemplateGenerator;
import org.docx4j.openpackaging.exceptions.Docx4JException;

import javax.xml.bind.JAXBException;
import java.io.File;

import java.io.IOException;

public class FileUtil {
    
    public static void generatePdf(PdfTemplateGenerator generator) {
        
        File inputDocx = new File(generator.getYamlConfig().getDocs().getLocation());
        if (!inputDocx.exists()) {
            throw new IllegalArgumentException("Input DOCX not found: " + inputDocx.getAbsolutePath());
        }
        
        String outputPathPdf = inputDocx.getParent() + File.separator + removeExtension(inputDocx.getName()) +
                "_" + generator.getSafeCompanyName() + ".pdf";
        try {
            PlaceHolderUtil.replace(inputDocx, outputPathPdf,generator);
        } catch (JAXBException | Docx4JException | IOException | jakarta.xml.bind.JAXBException e) {
            throw new RuntimeException("Failed to Generate PDF", e);
        }
    }
    
    public static String removeExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        return dotIndex >0?fileName.substring(0,dotIndex):fileName;
    }
}