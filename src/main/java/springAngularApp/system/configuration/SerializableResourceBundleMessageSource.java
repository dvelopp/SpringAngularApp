package springAngularApp.system.configuration;

import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import static java.util.Collections.emptyList;
import static org.apache.commons.io.FileUtils.listFiles;
import static org.apache.commons.lang3.StringUtils.substringBeforeLast;

/**
 * Retrieves all properties for selected locale from all properties files
 * which are in the message source directory.
 */
@Component
public class SerializableResourceBundleMessageSource extends ReloadableResourceBundleMessageSource {

    private static final String MESSAGES_FILE_SUFFIX = ".properties";

    public Properties getAllProperties(Locale locale) {
        clearCacheIncludingAncestors();
        String[] propertyFilesNames = getMessagesFiles().stream()
                .map(this::getFileSystemResourcesFile)
                .distinct()
                .toArray(String[]::new);
        setBasenames(propertyFilesNames);
        return getMergedProperties(locale).getProperties();
    }

    private List<File> getMessagesFiles() {
        URL messageSource = getClass().getClassLoader().getResource(getMessageSource());
        if(messageSource == null){
            return emptyList();
        }
        File messageSourceFolder = new File(messageSource.getFile());
        return (List<File>) listFiles(messageSourceFolder,
                new SuffixFileFilter(MESSAGES_FILE_SUFFIX),
                TrueFileFilter.INSTANCE);
    }

    /**
     * Prepares file path to be used as spring resource
     * @param file gives the path to the file to process
     * @return file path without suffix wrapped with "file:" at the start.
     * e.g. messages_en.properties -> messages
     * e.g. messages.properties -> messages
     */
    private String getFileSystemResourcesFile(File file) {
        String targetFileName = file.getName();
        targetFileName = substringBeforeLast(targetFileName, "_");
        targetFileName = substringBeforeLast(targetFileName, ".");
        String filePathWithoutSuffix = file.getPath().replace(file.getName(), targetFileName);
        return "file:".concat(filePathWithoutSuffix);
    }

    protected String getMessageSource() {
        return "messages";
    }

}