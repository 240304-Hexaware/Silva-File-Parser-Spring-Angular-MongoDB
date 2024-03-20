package com.marksilva.fileparser.backendspringboot.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public class HelperForService {
    /**
     * Reads a MultipartFile and converts the content into a String
     * @param file The file to be converted into a String
     * @return A string representation of the content of the file
     * @throws IOException
     */
    public static String parseFileToString(MultipartFile file) throws IOException {
        InputStreamReader reader = new InputStreamReader(file.getInputStream());
        StringBuilder builder = new StringBuilder();
        while(reader.ready()) {
            builder.append((char) reader.read());
        }
        return builder.toString();
    }

    public static void uploadFileLocally(String fileContent, String filePath, String fileName) throws FileNotFoundException {
        File theDir = new File(filePath);
        if(!theDir.exists()){
            theDir.mkdir();
        }
        try (PrintWriter out = new PrintWriter(filePath + "\\" +fileName)){
            out.println(fileContent);
        }
    }
}
