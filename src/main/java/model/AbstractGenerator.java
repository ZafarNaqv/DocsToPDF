package model;

import util.PlaceHolderUtil;

import java.util.Map;

public abstract class AbstractGenerator {
    private final String safeCompanyName;
    private final Map<String,String> placeHolderMap;
    
    public AbstractGenerator(String fullCompanyName, String jobPosition) {
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