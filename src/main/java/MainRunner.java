import model.Config;
import model.PdfTemplateGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;
import util.ConfigLoader;
import util.FileUtil;

import java.io.IOException;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "docsToPdf", mixinStandardHelpOptions = true, version = "1.0",
        description = "Customised PDF from Docs within few clicks")
public class MainRunner implements Callable<Integer> {
    private static final Logger logger = LoggerFactory.getLogger(MainRunner.class);
    
    @CommandLine.Parameters(index = "0", description = "Company name to be replaced")
    String companyName;
    
    @CommandLine.Parameters(index = "1", description = "Job Position")
    String jobPosition;
    
    @CommandLine.Option(names = {"-c", "--config"}, description = "Path to YAML config file", defaultValue = "config/config.yaml")
    private String configPath;
    
    @Override
    public Integer call(){
        logger.debug("CompanyName={}, JobPosition={}, ConfigPath={}", companyName, jobPosition, configPath);
        try {
            Config cfg = ConfigLoader.load(configPath);
            logger.info("Config file read successfully.");
            PdfTemplateGenerator pdfGenerator = new PdfTemplateGenerator(cfg,companyName,jobPosition);
            FileUtil.generatePdf(pdfGenerator);
            logger.info("Successfully generated PDF.");
        } catch (IOException e) {
            logger.error("Failed to find config file", e);
            throw new IllegalArgumentException("Failed to find config file", e);
        } catch (Exception e) {
            logger.error("Unexpected error while generating PDF", e);
            throw new RuntimeException("PDF generation failed", e);
        }
        return 0;
    }
    
    public static void main(String[] args) {
        int exitCode = new CommandLine(new MainRunner()).execute(args);
        System.exit(exitCode);
    }
}