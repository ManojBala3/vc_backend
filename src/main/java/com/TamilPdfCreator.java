package com;


import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class TamilPdfCreator {



    public static void main(String[] args) throws FontFormatException {
        try {
            // Define paths
            String imagePath = "D:/text_image.png";
            String pdfPath = "D:/text_image_pdf.pdf";
            
            // Create an image from text
            createImageFromText("மருந்துகள் - Text as Image", imagePath);
            
            // Create a PDF with the image
            createPdfWithImage(imagePath, pdfPath);
            
            System.out.println("PDF created successfully with text as image!");
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
    }
    
    private static void createImageFromText(String text, String imagePath) throws IOException, FontFormatException {
        // Set up image dimensions and font
        int width = 400;
        int height = 100;
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = bufferedImage.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, width, height);
        
        // Set font and color
        
      
        Font font = Font.createFont(Font.TRUETYPE_FONT, new File("D:/Latha-Bold.ttf"));
        graphics.setFont(font);
        graphics.setColor(Color.BLACK);
        
        // Draw the text
        graphics.drawString(text, 10, 40);
        graphics.dispose();
        
        // Save the image to a file
        ImageIO.write(bufferedImage, "png", new File(imagePath));
    }
    
    private static void createPdfWithImage(String imagePath, String pdfPath) throws IOException, DocumentException {
        // Create a Document instance
        Document document = new Document();
        
        // Create a PdfWriter instance
        PdfWriter.getInstance(document, new FileOutputStream(pdfPath));
        
        // Open the document
        document.open();
        
        // Load the image
        Image image = Image.getInstance(imagePath);
        
        // Add the image to the document
        document.add(image);
        
        // Close the document
        document.close();
    }

}
