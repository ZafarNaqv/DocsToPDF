package model;

import util.PlaceHolderUtil;

import java.util.Map;
import java.util.Objects;

public abstract class AbstractTemplateGenerator {
    private final String safeCompanyName;
    private final Map<String,String> placeHolderMap;
    
    public AbstractTemplateGenerator(String fullCompanyName, String jobPosition) {
        Objects.requireNonNull(fullCompanyName);
        Objects.requireNonNull(jobPosition);
        this.safeCompanyName = PlaceHolderUtil.buildCompanySuffix(fullCompanyName);
        this.placeHolderMap = PlaceHolderUtil.createPlaceHolderMap(fullCompanyName,jobPosition);
    }
    
    public String getSafeCompanyName() {
        return safeCompanyName;
    }
    
    public Map<String, String> getPlaceHolderMap() {
        return placeHolderMap;
    }
}