package ru.job4j.concurrent.io;

import java.io.*;
import java.nio.file.Files;
import java.util.stream.Collectors;

public class ParseFile {
    private File file;

    public synchronized void setFile(File f) {
        file = f;
    }

    public synchronized File getFile() {
        return file;
    }

    public synchronized String getContent() throws IOException {
        try (Reader r = new FileReader(file);
             BufferedReader br = new BufferedReader(r)) {
            return br.lines().collect(Collectors.joining(System.lineSeparator()));
        }
    }

    public synchronized String getContentWithoutUnicode() throws IOException {
        byte[] bytes = Files.readAllBytes(file.toPath());
        StringBuilder output = new StringBuilder();
        for(byte b : bytes) {
            int unsignedByte = b & 0xff;
            if ( unsignedByte < 0x80) {
                output.append((char) unsignedByte);
            }
        }
        return output.toString();    }

    public synchronized void saveContent(String content) throws IOException {
        try(Writer w = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(w)) {
            bw.write(content);
        }
    }
}
