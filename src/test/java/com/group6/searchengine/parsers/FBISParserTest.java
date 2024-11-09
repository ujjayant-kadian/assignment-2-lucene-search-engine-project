package com.group6.searchengine.parsers;

import com.group6.searchengine.data.FBISData;
import org.junit.jupiter.api.Test;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class FBISParserTest {

    @Test
    void testParseFBISFile() {
        File fbisDirectory = new File("../assignment-2/fbis/fb496262"); // Specify a test file path here
        FBISParser parser = new FBISParser();
        String outputFilePath = "test-outputs/fbis-parser.txt";

        try {
            TestConsumer consumer = new TestConsumer(outputFilePath);
            parser.parseSingleFile(fbisDirectory, consumer);
            assertNotNull(consumer);
            consumer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class TestConsumer implements DocumentConsumer<FBISData> {
        private BufferedWriter writer;

        public TestConsumer(String outputFilePath) throws IOException {
            File outputFile = new File(outputFilePath);
            outputFile.getParentFile().mkdirs();
            writer = new BufferedWriter(new FileWriter(new File(outputFilePath)));
        }

        @Override
        public void consume(FBISData documentData) {
            try {
                writer.write("Document No: " + documentData.getDocNo());
                writer.newLine();
                writer.write("Author: " + documentData.getAuthor());
                writer.newLine();
                writer.write("Date: " + documentData.getDate());
                writer.newLine();
                writer.write("Title: " + documentData.getTitle());
                writer.newLine();
                writer.write("Abstract: " + documentData.getAbs());
                writer.newLine();
                writer.write("Text: " + documentData.getText());
                writer.newLine();
                writer.write("Region: " + documentData.getRegion());
                writer.newLine();
                writer.write("Location: " + documentData.getLocation());
                writer.newLine();
                writer.write("Language: " + documentData.getLanguage());
                writer.newLine();
                writer.write("--------------------------------------------------");
                writer.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void close() throws IOException {
            writer.close();
        }
    }
}