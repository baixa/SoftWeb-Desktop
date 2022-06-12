package com.softweb.desktop.utils.print;

import javafx.print.*;
import javafx.scene.chart.BarChart;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Transform;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class PrintUtil {
    public static void print(final BarChart<String, Integer> chart) {
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
}
