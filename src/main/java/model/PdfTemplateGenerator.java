package model;

public class PdfTemplateGenerator extends AbstractTemplateGenerator {
    private final Config yamlConfig;
    private final String configPath;
    
    public PdfTemplateGenerator(Config yamlConfig, String fullCompanyName, String jobPosition,String configPath) {
        super(fullCompanyName,jobPosition);
        this.yamlConfig = yamlConfig;
        this.configPath = configPath;
    }
    
    public Config getYamlConfig() {
        return yamlConfig;
    }
    
    public String getConfigPath() { return configPath; }
    
    
    
}