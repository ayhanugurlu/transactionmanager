package com.capgemini.assesment.web.vaadin;

import com.vaadin.navigator.Navigator;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Ayhan.Ugurlu on 29/05/2018
 */
@SpringUI(path = "assesment")
public class TransactionUI extends UI {

    @Autowired
    SpringViewProvider springViewProvider;

    @Override
    protected void init(VaadinRequest request) {
        Navigator navigator = new Navigator(this, this);

        springViewProvider.setAccessDeniedViewClass(TransactionView.class);
        navigator.addProvider(springViewProvider);

        navigator.setErrorView(TransactionView.class);

        navigator.addView(TransactionView.NAME, TransactionView.class);


        Page.getCurrent().addPopStateListener(event -> {
            router(event.getUri());
        });
        router(TransactionView.NAME);
    }

    public void router(String route) {

        getNavigator().navigateTo(route);


    }

}
