package model;

import util.PlaceHolderUtil;

import java.util.HashMap;

public class PdfGenerator {
    private Config yamlConfig;
    private String safeCompanyName;
    private String fullCompanyName;
    HashMap<String,String> placeHolderMap = new HashMap<>();
    
    public PdfGenerator(Config yamlConfig,String fullCompanyName) {
        this.yamlConfig = yamlConfig;
        this.safeCompanyName =  PlaceHolderUtil.buildCompanySuffix(fullCompanyName);
        this.fullCompanyName = fullCompanyName;
    }
    
    public Config getYamlConfig() {
        return yamlConfig;
    }
    
    public String getSafeCompanyName() {
        return safeCompanyName;
    }
    
    public String getFullCompanyName() {
        return fullCompanyName;
    }
}