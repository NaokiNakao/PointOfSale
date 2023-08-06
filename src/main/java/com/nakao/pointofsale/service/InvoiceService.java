package com.nakao.pointofsale.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.nakao.pointofsale.dao.OrderDAO;
import com.nakao.pointofsale.exception.NotFoundException;
import com.nakao.pointofsale.model.Order;
import com.nakao.pointofsale.repository.OrderRepository;
import com.nakao.pointofsale.util.InvoiceProduct;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class InvoiceService {

    private final OrderRepository orderRepository;
    private final OrderDAO orderDAO;

    private static final Font BOLD_FONT = FontFactory.getFont(FontFactory.TIMES_BOLD, 18);
    private static final Font NORMAL_FONT = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12);

    @Value("${business.name}")
    private String businessName;

    public void generateInvoice(String orderId, HttpServletResponse response) throws IOException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found with ID: " + orderId));

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        addBusinessName(document);
        addTransactionInfo(document, orderId, order.getPurchaseDate());
        addCustomerInfo(document, order);
        addServedByInfo(document, order);
        addItemsTable(document, orderId);
        addTotalsInfo(document, order);
        addFooter(document);

        document.close();
    }

    private void addBusinessName(Document document) throws DocumentException {
        Paragraph title = new Paragraph(businessName, BOLD_FONT);
        title.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(title);
    }

    private void addTransactionInfo(Document document, String orderId, LocalDateTime purchaseDate) throws DocumentException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        Paragraph transactionId = new Paragraph("\nTransaction ID: " + orderId, NORMAL_FONT);
        transactionId.setAlignment(Paragraph.ALIGN_LEFT);

        Paragraph purchaseDateParagraph = new Paragraph("Purchase date: " + purchaseDate.format(formatter), NORMAL_FONT);
        purchaseDateParagraph.setAlignment(Paragraph.ALIGN_LEFT);

        document.add(transactionId);
        document.add(purchaseDateParagraph);
    }

    private void addCustomerInfo(Document document, Order order) throws DocumentException {
        String customerName = orderDAO.getCustomerName(order.getCustomerId());
        Paragraph customer = new Paragraph("Customer: " + customerName, NORMAL_FONT);
        customer.setAlignment(Paragraph.ALIGN_LEFT);
        document.add(customer);
    }

    private void addServedByInfo(Document document, Order order) throws DocumentException {
        String employeeName = orderDAO.getEmployeeName(order.getEmployeeId());
        Paragraph servedBy = new Paragraph("Served by: " + employeeName + "\n\n\n", NORMAL_FONT);
        servedBy.setAlignment(Paragraph.ALIGN_LEFT);
        document.add(servedBy);
    }

    private void addItemsTable(Document document, String orderId) throws DocumentException {
        PdfPTable itemsTableHeaders = createItemsTableHeaders();
        PdfPTable itemsTable = createItemsTable(orderId);

        document.add(itemsTableHeaders);
        document.add(createSeparator());
        document.add(itemsTable);
        document.add(createSeparator());
    }

    private PdfPTable createItemsTableHeaders() {
        PdfPTable itemsTableHeaders = new PdfPTable(3);
        itemsTableHeaders.setWidthPercentage(90);

        addHeaderCell(itemsTableHeaders, "Description", PdfPCell.ALIGN_LEFT);
        addHeaderCell(itemsTableHeaders, "Quantity", PdfPCell.ALIGN_RIGHT);
        addHeaderCell(itemsTableHeaders, "Unit Price", PdfPCell.ALIGN_RIGHT);

        return itemsTableHeaders;
    }

    private PdfPTable createItemsTable(String orderId) {
        List<InvoiceProduct> invoiceProducts = orderDAO.getInvoiceProducts(orderId);

        PdfPTable itemsTable = new PdfPTable(3);
        itemsTable.setWidthPercentage(90);

        for (InvoiceProduct product : invoiceProducts) {
            addItemCell(itemsTable, product.getProductName(), PdfPCell.ALIGN_LEFT);
            addItemCell(itemsTable, product.getQuantity().toString(), PdfPCell.ALIGN_RIGHT);
            addItemCell(itemsTable, product.getUnitPrice().toString(), PdfPCell.ALIGN_RIGHT);
        }

        return itemsTable;
    }

    private void addHeaderCell(PdfPTable table, String content, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(content, BOLD_FONT));
        cell.setHorizontalAlignment(alignment);
        cell.setBorder(PdfPCell.NO_BORDER);
        table.addCell(cell);
    }

    private void addItemCell(PdfPTable table, String content, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(content, NORMAL_FONT));
        cell.setHorizontalAlignment(alignment);
        cell.setBorder(PdfPCell.NO_BORDER);
        table.addCell(cell);
    }

    private Paragraph createSeparator() {
        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12);
        Paragraph separator = new Paragraph("---------------------------------------------------" +
                "-------------------------------------------------------------------------------", font);
        separator.setAlignment(Paragraph.ALIGN_CENTER);
        return separator;
    }

    private void addTotalsInfo(Document document, Order order) throws DocumentException {
        Paragraph subtotal = new Paragraph("Subtotal: " + order.getNet().toString(), NORMAL_FONT);
        subtotal.setAlignment(Paragraph.ALIGN_LEFT);

        Paragraph taxes = new Paragraph("Taxes: " + order.getTax().toString(), NORMAL_FONT);
        taxes.setAlignment(Paragraph.ALIGN_LEFT);

        Paragraph total = new Paragraph("Total: " + order.getTotal().toString(), BOLD_FONT);
        total.setAlignment(Paragraph.ALIGN_LEFT);

        document.add(subtotal);
        document.add(taxes);
        document.add(total);
        document.add(createSeparator());
    }

    private void addFooter(Document document) throws DocumentException {
        Paragraph footer = new Paragraph("\nThank you for your purchase.", NORMAL_FONT);
        footer.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(footer);
    }
}

