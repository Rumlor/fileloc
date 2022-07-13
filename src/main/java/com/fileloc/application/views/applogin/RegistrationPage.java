package com.fileloc.application.views.applogin;

import com.fileloc.application.appexceptionblueprint.BaseAppException;
import com.fileloc.application.applicationconstants.AppUserRoles.AppUserRoleConstant;
import com.fileloc.application.domain.appobjects.UserRegistrationObject;
import com.fileloc.application.domain.appuser.AppUser;
import com.fileloc.application.domain.appuser.AppUserRole;
import com.fileloc.application.views.UIEventHandler;
import com.github.rjeschke.txtmark.Run;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.apache.catalina.User;


@Route("sign-up")
@AnonymousAllowed
public class RegistrationPage extends VerticalLayout  {

    private final H1 header = new H1("FileLoc Local Content Management App");
    private final H2 subHeader = new H2("Register User");

    private Span error = new Span();
    private final Button registerButton = new Button("Sign-up");
    private TextField userName;
    private PasswordField password;
    private PasswordField passwordConfirm;
    private ComboBox<AppUserRoleConstant> userRole;

    private  RegistrationPageFormBinder formBinder;

    private final UIEventHandler uiEventHandler;

    public RegistrationPage(UIEventHandler eventHandler) {
        this.uiEventHandler = eventHandler;
        configureFormFields();
        configureFormButtons();
        configureFormBinder();
        var pageLayout =  configureFormLayout();
        add(pageLayout);
    }

    private void configureFormBinder() {
        formBinder = new RegistrationPageFormBinder();
        formBinder.bindFieldsOfForm();
    }

    private VerticalLayout configureFormLayout() {

        setSizeFull();
        VerticalLayout layout = new VerticalLayout();
        layout.add(header,subHeader,userName,password,passwordConfirm,userRole,registerButton);
        layout.setAlignItems(Alignment.CENTER);
        layout.setJustifyContentMode(JustifyContentMode.CENTER);
        return layout;
    }

    private void configureFormButtons() {
        registerButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        registerButton.addClickListener((clickEvent)-> {
            try {
                uiEventHandler.registrationEvent(formBinder.writeBeanFromFields());

            }catch (RuntimeException e){
                Notification notification  = new Notification();
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                notification.setText(e.getMessage());
                notification.setDuration(5000);
                notification.setPosition(Notification.Position.BOTTOM_CENTER);
                notification.open();
            }
        });
    }

    private void configureFormFields() {
        error.setVisible(false);
        userName = new TextField("Username");
        password = new PasswordField("Password");
        passwordConfirm = new PasswordField("Confirm Password");
        userRole = new ComboBox<>();
        userRole.setItems(AppUserRoleConstant.getAllConstants());

    }

    private class RegistrationPageFormBinder {
        private final Binder<UserRegistrationObject> userRegistrationBinder = new Binder<>(UserRegistrationObject.class);

        private void bindFieldsOfForm(){
            userRegistrationBinder.bind(RegistrationPage.this.userName,userRegister->userRegister.getUserName(),(userRegister,arg)->userRegister.setUserName(arg));
            userRegistrationBinder.bind(RegistrationPage.this.password,userRegister->userRegister.getPassword(),(userRegister,arg)->userRegister.setPassword(arg));
            userRegistrationBinder.bind(RegistrationPage.this.passwordConfirm,userRegister->userRegister.getConfirmPassword(),(userRegister,arg)->userRegister.setConfirmPassword(arg));
            userRegistrationBinder.bind(RegistrationPage.this.userRole,userRegister->userRegister.getUserRole(),(userRegister,arg)->userRegister.setUserRole(arg));
        }

        private UserRegistrationObject writeBeanFromFields()  {
            UserRegistrationObject object = new UserRegistrationObject();
            try {
                userRegistrationBinder.writeBean(object);
            }catch (ValidationException e){
                Notification.show("Validation Exception",5000, Notification.Position.BOTTOM_CENTER);
            }

            return object;
        }



    }


}
