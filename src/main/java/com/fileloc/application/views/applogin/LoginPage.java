package com.fileloc.application.views.applogin;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.router.internal.BeforeEnterHandler;

@Route("log-in")
@PageTitle("Log-in to FileLoc")
public class LoginPage extends VerticalLayout implements BeforeEnterObserver {

    private final LoginForm loginPage = new LoginForm();
    private final Button registration = new Button("Sign-up");

    public LoginPage() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        loginPage.setAction("log-in");
        registration.addClickListener((click)-> UI.getCurrent().getPage().setLocation("sign-up"));
        add(new H1("FileLoc Local Content Management App"), loginPage,registration);
    }


    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {

        if(beforeEnterEvent.getLocation().getQueryParameters().getParameters().containsKey("error")) {
            loginPage.setError(true);
        }
    }
}
