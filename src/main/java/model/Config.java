package model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Config {
    private  Docs docs;
    private Metrics metrics;
    
    @JsonIgnore
    private String sourcePath;
    
    public void setSourcePath(String path) { this.sourcePath = path; }
    public String getSourcePath() { return sourcePath; }
    
    public Docs getDocs() {
        return docs;
    }
    public Metrics getMetrics() { return metrics; }
    public void setMetrics(Metrics metrics) { this.metrics = metrics; }
    
    public static class Docs {
        private String location;
        public String getLocation() { return location; }
    }
    
    public static class Metrics {
        private int totalCount;
        public int getTotalCount() { return totalCount; }
        public void incrementTotalCount() { this.totalCount++;}
    }
    
}