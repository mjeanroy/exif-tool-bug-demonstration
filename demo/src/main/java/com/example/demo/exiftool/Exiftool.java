package com.example.demo.exiftool;

import com.thebuzzmedia.exiftool.ExifTool;
import com.thebuzzmedia.exiftool.ExifToolBuilder;
import com.thebuzzmedia.exiftool.core.StandardTag;
import com.thebuzzmedia.exiftool.exceptions.UnsupportedFeatureException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

@Service
public class Exiftool {
    private ExifTool exifTool;

    @PostConstruct
    public void init() {
        Optional<String> pathOptional = getExifToolPath();
        if (!pathOptional.isPresent()) {
            return;
        }
        String path = pathOptional.get();
        try {
            exifTool = new ExifToolBuilder().withPath(path).enableStayOpen().build();
        } catch (UnsupportedFeatureException ex) {
            exifTool = new ExifToolBuilder().withPath(path).build();
        }
    }

    private Optional<String> getExifToolPath() {
        String[] paths = System.getenv("PATH").split(File.pathSeparator);
        for (String path : paths) {
            File dir = new File(path);
            if (!dir.isDirectory())
                continue;
            Optional<String> fullPath = Arrays.stream(dir.list((dir1, name) -> name.startsWith("exiftool"))).findFirst();
            if (fullPath.isPresent()) {
                return fullPath;
            }
        }
        return Optional.empty();
    }

    public Double getLat(File image) {
        try {
            String latString = exifTool.getImageMeta(image, Collections.singletonList(StandardTag.GPS_LATITUDE)).get(StandardTag.GPS_LATITUDE);
            if (latString == null) {
                return null;
            }
            return Double.valueOf(latString);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Double getLong(File image) {
        try {
            String latString = exifTool.getImageMeta(image, Collections.singletonList(StandardTag.GPS_LONGITUDE)).get(StandardTag.GPS_LONGITUDE);
            if (latString == null) {
                return null;
            }
            return Double.valueOf(latString);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
