package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Scanner;


public class DesmosDecimator {

    static StringBuffer stringBufferOfData = new StringBuffer();
    static String filename = "src/com/company/DesmosDecimator/functions.txt";
    // static Scanner sc = new Scanner(System.in);
    static CaptureParser brder = new CaptureParser();

    static ArrayList<Integer> xCoords = new ArrayList<>(10);
    static ArrayList<Integer> yCoords = new ArrayList<>(10);


    public static void main(String[] args) throws IOException {

        FileChannel.open(Paths.get(filename), StandardOpenOption.WRITE).truncate(0).close();
        boolean fileRead = readFile();//call the method to read the file with the files name

        File f = new File("src/com/company/DesmosDecimator/Capture.JPG");
        BufferedImage img = ImageIO.read(f);

        if (fileRead) {//if the read file was successfully
            brder.imageReader();
            Coords(img);
            generateEquations(xCoords, yCoords);//call method to get text to replace, replacement text and output replaced String buffer
            writeToFile();
        }

        System.exit(0);//exit once app is done
    }

    private static boolean readFile() {

        Scanner fileToRead = null;
        try {
            fileToRead = new Scanner(new File(filename)); //point the scanner method to a file

            for (String line; fileToRead.hasNextLine() && (line = fileToRead.nextLine()) != null; ) {
                System.out.println(line);//print each line as its read

                stringBufferOfData.append(line).append("\r\n");
            }
            fileToRead.close();
            return true;
        } catch (FileNotFoundException ex) {//if the file cannot be found an exception will be thrown
            System.out.println("The file " + filename + " could not be found! " + ex.getMessage());
            return false;

        } finally {//if an error occurs now we close the file to exit gracefully
            fileToRead.close();
            return true;
        }
    }

    private static void writeToFile() {
        try {
            BufferedWriter bufwriter = new BufferedWriter(new FileWriter(filename));
            bufwriter.write(stringBufferOfData.toString());//writes the edited string buffer to the new file
            bufwriter.close(); //closes the file

        } catch (Exception e) {//if an exception occurs
            System.out.println("Error occured while attempting to write to file: " + e.getMessage());
        }
    }

    private static void generateEquations(ArrayList<Integer> xCoords, ArrayList<Integer> yCoords) throws IOException {

        brder.imageReader();

        int xIntercept = 0;
        for (int i = 0; i < CaptureParser.width; i++) {

            if (xIntercept == xCoords.get(i)) {
                stringBufferOfData.append("x = " + xIntercept + " \\left\\{0 < y< " + yCoords.get(i) + "\\right\\}");
                stringBufferOfData.append("\n");
            } else {
                stringBufferOfData.append("x = " + xIntercept + " \\left\\{0 < y< " + brder.height / 10 + "\\right\\}");
                stringBufferOfData.append("\n");
                xIntercept += 0.05;
                //returnTesting(xCoords,yCoords);
            }

        }

    }

    public static void Coords(BufferedImage img) {
        int count = 0;

        for (int i = 0; i < CaptureParser.height; i++) {
            for (int j = 0; j < CaptureParser.width; j++) {

                count++;
                Color c = new Color(img.getRGB(i, j));
                if ((c.getRed() > 0 && c.getRed() < 40) && (c.getGreen() > 0 && c.getGreen() < 40) && (c.getBlue() > 0 && c.getBlue() < 40)) {
                    xCoords.add(j);
                    yCoords.add(i);
                    System.out.println("S.No: " + count + " Red: " + c.getRed() + "  Green: " + c.getGreen() + " Blue: " + c.getBlue());

                }
                System.out.println(xCoords+","+yCoords);

            }
        }

    }
    /*
    public static int[] returnTesting(ArrayList xCoords, ArrayList yCoords){
        for(int i = 0; i < xCoords.size(); i += 10){
            for(int j = 0; j < xCoords.size(); j += 10) {
                System.out.println(xCoords.get(i) + "," + yCoords.get(j));
            }
        }

    }
    */
}
