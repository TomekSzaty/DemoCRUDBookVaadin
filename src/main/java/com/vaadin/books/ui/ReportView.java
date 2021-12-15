package com.vaadin.books.ui;

import com.vaadin.books.backend.Book;
import com.vaadin.books.backend.BookService;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import org.vaadin.reports.PrintPreviewReport;

import javax.annotation.security.RolesAllowed;

@Route("report")
@RolesAllowed("ADMIN")
public class ReportView extends VerticalLayout {

    public ReportView(BookService bookService) {

        var report = new PrintPreviewReport<>(Book.class, "title", "published", "rating");
        report.setItems(bookService.findAll());
        report.getReportBuilder().setTitle("BOOKS");

        StreamResource pdf = report.getStreamResource("books.pdf", bookService::findAll, PrintPreviewReport.Format.PDF);
        StreamResource csv = report.getStreamResource("books.csv", bookService::findAll, PrintPreviewReport.Format.CSV);

        add(
                new HorizontalLayout(
                        new Anchor(pdf, "PDF"),
                        new Anchor(csv, "CSV")
                ),
                report
        );

    }
}
