package model;

public class PdfTemplateGenerator extends AbstractTemplateGenerator {
    private Config yamlConfig;
    
    public PdfTemplateGenerator(Config yamlConfig, String fullCompanyName, String jobPosition) {
        super(fullCompanyName,jobPosition);
        this.yamlConfig = yamlConfig;
    }
    
    public Config getYamlConfig() {
        return yamlConfig;
    }
    
    
}