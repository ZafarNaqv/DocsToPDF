package model;

public class Config {
    private  Docs docs;
    public Docs getDocs() {
        return docs;
    }
    
    public static class Docs {
        private String location;
        public String getLocation() { return location; }
    }
}