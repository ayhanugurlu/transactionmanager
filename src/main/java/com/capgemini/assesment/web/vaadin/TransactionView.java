package com.capgemini.assesment.web.vaadin;

import com.capgemini.assesment.service.CustomerService;
import com.capgemini.assesment.service.exception.CustomerAlreadyExist;
import com.capgemini.assesment.service.model.input.customer.AddCustomerInput;
import com.capgemini.assesment.service.model.output.customer.GetCustomerOutput;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Ayhan.Ugurlu on 29/05/2018
 */
@SpringView(name = TransactionView.NAME)
public class TransactionView  extends VerticalLayout implements View {
    public static final String NAME = "TransactionView";

    @Autowired
    CustomerService customerService;

    public TransactionView(){

        this.addComponent(buildCustomer());

    }

    private Component buildCustomer(){
        VerticalLayout verticalLayout = new VerticalLayout();
        Grid<GetCustomerOutput> customerOutputGrid = new Grid<>();
        customerOutputGrid.setData(customerService.getAllCustomer());
        verticalLayout.addComponent(customerOutputGrid);
        Button addCustomer = new Button("Add Customer");
        addCustomer.addClickListener(event -> {
            Window window = new Window("Add Customer");
            window.setWidth(300.0f, Unit.PIXELS);
            final FormLayout content = new FormLayout();
            content.addComponent(new Label("Name"));
            TextField name = new TextField();
            content.addComponent(name);
            content.addComponent(new Label("Surname"));
            TextField surname = new TextField();
            content.addComponent(surname);
            content.addComponent(new Label("Nationality Id"));
            TextField nationalityId = new TextField();
            content.addComponent(nationalityId);
            Button add = new Button("add");
            content.addComponent(add);
            add.addClickListener(event1 -> {
                ;
                try {
                    customerService.addCustomer(AddCustomerInput.builder().name(name.getValue()).surname(surname.getValue()).nationalityId(nationalityId.getValue()).build());
                } catch (CustomerAlreadyExist customerAlreadyExist) {
                    Notification.show(customerAlreadyExist.getMessage(),Notification.Type.ERROR_MESSAGE);
                }
            });
            content.setMargin(true);
            window.setContent(content);
        });
        return verticalLayout;
    }
}
