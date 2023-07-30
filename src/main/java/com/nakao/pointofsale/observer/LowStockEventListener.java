package com.nakao.pos.observer;

import com.nakao.pos.enumeration.EmployeeRole;
import com.nakao.pos.model.Employee;
import com.nakao.pos.model.Product;
import com.nakao.pos.service.EmailSenderService;
import com.nakao.pos.service.EmployeeService;
import com.nakao.pos.util.EmailMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Naoki Nakao on 7/28/2023
 * @project POS
 */

@Component
@RequiredArgsConstructor
public class LowStockEventListener {

    private final EmployeeService employeeService;
    private final EmailSenderService emailSenderService;

    @EventListener
    public void onLowStockEvent(LowStockEvent lowStockEvent) {
        List<Employee> managers = employeeService.getEmployeesByRole(EmployeeRole.MANAGER.getValue());
        Product product = lowStockEvent.getProduct();

        for (Employee manager : managers) {
            EmailMessage notification = buildEmailMessage(manager, product);
            emailSenderService.sendMail(notification);
        }
    }

    private EmailMessage buildEmailMessage(Employee manager, Product product) {
        String subject = "Alert: " + product.getName() + " (" + product.getSku() + ") has reached minimum stock level";
        String text = "Dear " + manager.getFirstName() + " " + manager.getLastName() + ",\n\n"
                + "We would like to inform you that the product " + product.getName() + " (SKU: " + product.getSku() + ")"
                + " has reached the minimum stock level. Currently, we have " + product.getStock() + " units available in our inventory.\n\n"
                + "It's essential to consider that this stock level may impact order fulfillment and business operations. We recommend taking immediate action to replenish the stock to appropriate levels.\n\n"
                + "Best regards,\n" + "Administration";

        return EmailMessage.builder()
                .to(manager.getEmail())
                .subject(subject)
                .text(text)
                .build();
    }

}
