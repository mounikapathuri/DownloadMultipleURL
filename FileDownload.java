/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

/**
 *
 * @author DATAWISE
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class FileDownload {

    public static void copyURLToFile(URL url, File file) {

        try {
            InputStream input = url.openStream();
            if (file.exists()) {
                if (file.isDirectory()) {
                    throw new IOException("File '" + file + "' is a directory");
                }

                if (!file.canWrite()) {
                    throw new IOException("File '" + file + "' cannot be written");
                }
            } else {
                File parent = file.getParentFile();
                if ((parent != null) && (!parent.exists()) && (!parent.mkdirs())) {
                    throw new IOException("File '" + file + "' could not be created");
                }
            }

            FileOutputStream output = new FileOutputStream(file);

            byte[] buffer = new byte[4096];
            int n = 0;
            while (-1 != (n = input.read(buffer))) {
                output.write(buffer, 0, n);
            }

            input.close();
            output.close();

            System.out.println("File '" + file + "' downloaded successfully!");
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {

        Properties prop = new Properties();
        String fileName = "app.config";
        InputStream is = new FileInputStream(fileName);
        prop.load(is);

        System.out.println(prop.getProperty("app.name"));
        String toFile=prop.getProperty("ToFolder");
        String urlFile=prop.getProperty("urlFile");
        
        BufferedReader reader;
        
        File uFile = new File(urlFile);
        
        try {
            reader = new BufferedReader(new FileReader(
                    uFile));
            String line = reader.readLine();
            while (line != null) {
                System.out.println(line);
                String[] parts = line.split("=");
                String part1 = parts[0]; // 004
                String part2 = parts[1]; // 034556
                URL url = new URL(part2);
                File file = new File(toFile+"/"+part1);
                FileDownload.copyURLToFile(url, file);
                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        

//        //URL pointing to the file
//        String sUrl = "http://av-info.faa.gov/data/AID/fix/a1975_79.txt";
//
//        URL url = new URL(sUrl);
//
//        //File where to be downloaded
//        File file = new File("C:/Temp/me1.txt");
//
//        FileDownload.copyURLToFile(url, file);
    }

}
