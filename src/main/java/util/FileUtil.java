package util;
import model.PdfGenerator;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import java.io.File;

import java.io.IOException;

public class FileUtil {
    
    public static void generatePdf(PdfGenerator generator) {
        
        File inputDocx = new File(generator.getYamlConfig().docs.location);
        if (!inputDocx.exists()) {
            throw new IllegalArgumentException("Input DOCX not found: " + inputDocx.getAbsolutePath());
        }
        
        String outputPathPdf = inputDocx.getParent() + File.separator +
                "_" + generator.getSafeCompanyName() + ".pdf";
        try {
            PlaceHolderUtil.replace(inputDocx, outputPathPdf);
        } catch (Docx4JException  | IOException e) {
            throw new RuntimeException("Failed to Generate PDF", e);
        }
        
    }
}