package model;

public class PdfGenerator extends AbstractGenerator {
    private Config yamlConfig;
    
    public PdfGenerator(Config yamlConfig, String fullCompanyName, String jobPosition) {
        super(fullCompanyName,jobPosition);
        this.yamlConfig = yamlConfig;
    }
    
    public Config getYamlConfig() {
        return yamlConfig;
    }
    
    
}