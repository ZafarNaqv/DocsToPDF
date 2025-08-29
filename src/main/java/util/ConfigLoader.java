    package util;
    
    import com.fasterxml.jackson.annotation.JsonInclude;
    import com.fasterxml.jackson.databind.ObjectMapper;
    import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
    import model.Config;
    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    
    
    import java.io.File;
    import java.io.IOException;
    
    
    public class ConfigLoader {
        private static final ObjectMapper mapper = new ObjectMapper(new YAMLFactory())
                .setSerializationInclusion(JsonInclude.Include.NON_NULL);
        private static final Logger logger = LoggerFactory.getLogger(ConfigLoader.class);
        
        public static Config load(String path) throws IOException {
            Config config = mapper.readValue(new File(path), Config.class);
            if (config.getMetrics() == null) {
                config.setMetrics(new Config.Metrics());
            }
            config.setSourcePath(path);
            return config;
        }
        
        public static void save(Config config) throws IOException {
            config.getMetrics().incrementTotalCount();
            mapper.writeValue(new File(config.getSourcePath()), config);
            logger.info("{} Cover Letters saved in total", config.getMetrics().getTotalCount());
        
        }
    }