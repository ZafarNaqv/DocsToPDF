import model.Config;
import model.PdfGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;
import util.ConfigLoader;
import util.FileUtil;
import util.PlaceHolderUtil;

import java.io.IOException;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "docsToPdf", mixinStandardHelpOptions = true, version = "1.0",
        description = "Customised PDF from Docs within few clicks")
public class MainRunner implements Callable<Integer> {
    private static final Logger logger = LoggerFactory.getLogger(MainRunner.class);
    
    
    @CommandLine.Option(names = {"-n", "--newPdf"}, description = "Generates new pdf")
    boolean generateNewPDF = true;
    
    @CommandLine.Parameters(index = "0", description = "Company name to be replaced")
    String companyName;
    
    @CommandLine.Option(names = {"-c", "--config"}, description = "Path to YAML config file", defaultValue = "config.yaml")
    private String configPath;
    
    @Override
    public Integer call(){
        try {
            Config cfg = ConfigLoader.load(configPath);
            logger.info("Config file read successfully.");
            PdfGenerator pdfGenerator = new PdfGenerator(cfg,companyName);
            FileUtil.generatePdf(pdfGenerator);
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to find config file", e);
        }
        logger.info("Successfully generated PDF.");
        return 0;
    }
    
    public static void main(String[] args) {
        int exitCode = new CommandLine(new MainRunner()).execute(args);
        System.exit(exitCode);
    }
}