package com.capgemini.assesment.web.vaadin;

import com.capgemini.assesment.service.AccountService;
import com.capgemini.assesment.service.CustomerService;
import com.capgemini.assesment.service.TransactionService;
import com.capgemini.assesment.service.exception.AccountNotFound;
import com.capgemini.assesment.service.exception.CustomerAlreadyExist;
import com.capgemini.assesment.service.exception.CustomerNotFound;
import com.capgemini.assesment.service.exception.InsufficientBalance;
import com.capgemini.assesment.service.model.input.account.AddCustomerAccountInput;
import com.capgemini.assesment.service.model.input.customer.AddCustomerInput;
import com.capgemini.assesment.service.model.input.transaction.TransactionInput;
import com.capgemini.assesment.service.model.output.account.GetAccountOutput;
import com.capgemini.assesment.service.model.output.account.TransactionOutput;
import com.capgemini.assesment.service.model.output.customer.GetCustomerOutput;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
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

    @Autowired
    AccountService accountService;

    @Autowired
    TransactionService transactionService;

    VerticalLayout accountLayout;

    VerticalLayout customerLayout;

    VerticalLayout transactionLayout;

    Grid<GetAccountOutput> getAccountOutputGrid = null;

    private long selectedCustomerId;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

        customerLayout = buildCustomer();
        this.addComponent(customerLayout);
        this.setComponentAlignment(customerLayout,Alignment.MIDDLE_CENTER);
    }



    private VerticalLayout buildCustomer(){
        VerticalLayout verticalLayout = new VerticalLayout();
        Grid<GetCustomerOutput> customerOutputGrid = new Grid<>();
        customerOutputGrid.setItems(customerService.getAllCustomer());
        customerOutputGrid.addColumn(getCustomerOutput -> getCustomerOutput.getId()).setCaption("Id");
        customerOutputGrid.addColumn(getCustomerOutput -> getCustomerOutput.getName()).setCaption("Name");
        customerOutputGrid.addColumn(getCustomerOutput -> getCustomerOutput.getSurname()).setCaption("Surname");
        customerOutputGrid.addColumn(getCustomerOutput -> getCustomerOutput.getNationalityId()).setCaption("Nationality");
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
                try {
                    customerService.addCustomer(AddCustomerInput.builder().name(name.getValue()).surname(surname.getValue()).nationalityId(nationalityId.getValue()).build());
                    window.close();
                    customerOutputGrid.setItems(customerService.getAllCustomer());
                    customerOutputGrid.markAsDirty();
                } catch (CustomerAlreadyExist customerAlreadyExist) {
                    Notification.show(customerAlreadyExist.getMessage(),Notification.Type.ERROR_MESSAGE);
                }
            });
            content.setMargin(true);
            window.setContent(content);
            UI.getCurrent().addWindow(window);
        });
        customerOutputGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        customerOutputGrid.addSelectionListener(selectionEvent -> {
            selectionEvent.getFirstSelectedItem().ifPresent(getCustomerOutput -> {
                accountLayout = buildAccount(getCustomerOutput);
                TransactionView.this.addComponent(accountLayout);
            });
            if(!selectionEvent.getFirstSelectedItem().isPresent()){
                TransactionView.this.removeComponent(accountLayout);
                if(transactionLayout != null){
                    TransactionView.this.removeComponent(transactionLayout);
                }

            }
        });
        verticalLayout.addComponent(addCustomer);
        return verticalLayout;
    }




    private VerticalLayout buildAccount(GetCustomerOutput getCustomerOutput){
        accountLayout = new VerticalLayout();
        getAccountOutputGrid = new Grid<>();
        getAccountOutputGrid.setItems(accountService.getCustomerAccounts(getCustomerOutput.getId()));
        getAccountOutputGrid.addColumn(getAccountOutput -> getAccountOutput.getId()).setCaption("Id");
        getAccountOutputGrid.addColumn(getAccountOutput -> getAccountOutput.getOwnerId()).setCaption("Customer Id");
        getAccountOutputGrid.addColumn(getAccountOutput -> getAccountOutput.getCurrencyType()).setCaption("Currency Type");
        getAccountOutputGrid.addColumn(getAccountOutput -> getAccountOutput.getBalance()).setCaption("Balance");
        accountLayout.addComponent(getAccountOutputGrid);
        Button addCustomer = new Button("Add Account");
        addCustomer.addClickListener(event -> {
            Window window = new Window("Add Account");
            window.setWidth(300.0f, Unit.PIXELS);
            final FormLayout content = new FormLayout();
            content.addComponent(new Label("Currency Type"));
            TextField currencyType = new TextField();
            content.addComponent(currencyType);

            content.addComponent(new Label("Amount"));
            TextField amount = new TextField();
            content.addComponent(amount);

            Button add = new Button("add");
            content.addComponent(add);
            add.addClickListener(event1 -> {
                try {
                    AddCustomerAccountInput addCustomerAccountInput = AddCustomerAccountInput.builder().amount(new Long(amount.getValue())).currencyType(currencyType.getValue()).ownerId(getCustomerOutput.getId()).build();
                    accountService.addAccount(addCustomerAccountInput);
                    window.close();
                    getAccountOutputGrid.setItems(accountService.getCustomerAccounts(getCustomerOutput.getId()));
                    getAccountOutputGrid.markAsDirty();
                } catch (CustomerNotFound  | AccountNotFound | InsufficientBalance e) {
                    Notification.show(e.getMessage(),Notification.Type.ERROR_MESSAGE);
                }
            });
            content.setMargin(true);
            window.setContent(content);
            UI.getCurrent().addWindow(window);
        });
        accountLayout.addComponent(addCustomer);
        getAccountOutputGrid.addSelectionListener(selectionEvent -> {
            selectionEvent.getFirstSelectedItem().ifPresent(getAccountOutput -> {
                transactionLayout = buildTransaction(getAccountOutput);
                TransactionView.this.addComponent(transactionLayout);
            });
            if(!selectionEvent.getFirstSelectedItem().isPresent()){
                TransactionView.this.removeComponent(transactionLayout);
            }

        });
        return accountLayout;
    }


    private VerticalLayout buildTransaction(GetAccountOutput getAccountOutput){
        VerticalLayout verticalLayout = new VerticalLayout();
        Grid<TransactionOutput> transactionGrid = new Grid<>();
        try {
            transactionGrid.setItems(accountService.getAccountTransactions(getAccountOutput.getId()).getTransactionOutputs());
        } catch (AccountNotFound accountNotFound) {
            accountNotFound.printStackTrace();
            return null;
        }
        transactionGrid.addColumn(transactionOutput ->  transactionOutput.getAmount()).setCaption("Amount");
        transactionGrid.addColumn(transactionOutput ->  transactionOutput.getTransactionDate()).setCaption("Date");
        verticalLayout.addComponent(transactionGrid);
        Button addTransaction = new Button("Add Transaction");
        addTransaction.addClickListener(event -> {
            Window window = new Window("Add Transaction");
            window.setWidth(300.0f, Unit.PIXELS);
            final FormLayout content = new FormLayout();

            content.addComponent(new Label("Amount"));
            TextField amount = new TextField();
            content.addComponent(amount);

            Button add = new Button("add");
            content.addComponent(add);
            add.addClickListener(event1 -> {
                try {
                    TransactionInput transactionInput = TransactionInput.builder().amount(new Long(amount.getValue())).accountId(getAccountOutput.getId()).build();
                    transactionService.doTransaction(transactionInput);
                    transactionGrid.setItems(accountService.getAccountTransactions(getAccountOutput.getId()).getTransactionOutputs());
                    getAccountOutputGrid.setItems(accountService.getCustomerAccounts(getAccountOutput.getOwnerId()));
                    window.close();
                } catch ( AccountNotFound | InsufficientBalance e) {
                    Notification.show(e.getMessage(),Notification.Type.ERROR_MESSAGE);
                }
            });
            content.setMargin(true);
            window.setContent(content);
            UI.getCurrent().addWindow(window);
        });
        verticalLayout.addComponent(addTransaction);
        return verticalLayout;
    }

}
