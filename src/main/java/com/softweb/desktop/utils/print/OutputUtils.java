package com.softweb.desktop.utils.print;

import com.softweb.desktop.auth.Authorization;
import javafx.embed.swing.SwingFXUtils;
import javafx.print.*;
import javafx.scene.chart.BarChart;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1CFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class OutputUtils {
    public static void printChart(final BarChart<String, Integer> chart) {
        Printer printer = Printer.getDefaultPrinter();
        PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, Printer.MarginType.DEFAULT);

        WritableImage image = chart.snapshot(null, null);
        PrinterJob job = PrinterJob.createPrinterJob();
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(pageLayout.getPrintableWidth());
        imageView.setFitHeight(image.getHeight() * pageLayout.getPrintableWidth() / image.getWidth());
        if (job != null) {
            boolean success = job.printPage(imageView);
            if (success) {
                job.endJob();
            }
        }
    }

    public static String saveChartAsPDF(final BarChart<String, Integer> chart) throws IOException{

        File temp = File.createTempFile("SoftWeb-", ".png");
        temp.deleteOnExit();
        PDDocument doc = new PDDocument();
        PDPage page = new PDPage();

        WritableImage chartImage = chart.snapshot(null, null);
        ImageView imageView = new ImageView(chartImage);
        imageView.setFitWidth(page.getMediaBox().getWidth());
        imageView.setFitHeight(chartImage.getHeight() * page.getMediaBox().getWidth() / chartImage.getWidth());

        WritableImage pdfImage = imageView.snapshot(null, null);
        ImageIO.write(SwingFXUtils.fromFXImage(pdfImage, null), "png", temp);

        PDImageXObject pdimage;
        PDPageContentStream content;
        pdimage = PDImageXObject.createFromFile(temp.getPath(),doc);
        pdimage.setWidth((int) page.getMediaBox().getWidth());
        pdimage.setHeight((int) (pdimage.getWidth() * chartImage.getHeight() / chartImage.getWidth()));
        content = new PDPageContentStream(doc, page);
        String title = "Statistics of applications of user \"" + Authorization.getCurrentUser().getFullName() + "\"";
        PDFont font = PDType1Font.HELVETICA_BOLD;
        int fontSize = 16;
        int marginTop = 30;
        float titleWidth = font.getStringWidth(title) / 1000 * fontSize;
        float titleHeight = font.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * fontSize;
        content.beginText();
        content.setFont(font, fontSize);
        content.newLineAtOffset((page.getMediaBox().getWidth() - titleWidth) / 2, page.getMediaBox().getHeight() - marginTop - titleHeight);
        content.drawString(title);
        content.endText();
        content.drawImage(pdimage, 0, 400);
        content.close();
        doc.addPage(page);
        String path = System.getProperty("user.home") + "\\Downloads\\" + "SoftWeb-" + java.util.UUID.randomUUID().toString() + "-Chart.pdf";
        doc.save(path);
        doc.close();
        return path;
    }
}
