package com.fileloc.application.views.layout;

import com.fileloc.application.appservices.securityservices.SecurityContextAppService;
import com.fileloc.application.views.mainpage.MainWebPage;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightCondition;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;

public class AppMainLayout extends AppLayout {
     private  H1 appLogo;
     private Button logoutButton = new Button("Logout");
     private final SecurityContextAppService securityContextAppService;

    public AppMainLayout(SecurityContextAppService securityContextAppService) {
      this.securityContextAppService = securityContextAppService;

        createAppDrawer();
        createDrawerToggleMenu();
        addButtonListeners();
    }

    private void addButtonListeners() {
        logoutButton.addClickListener(clickEvent -> securityContextAppService.logout());
    }

    private void createDrawerToggleMenu() {
        RouterLink appRouter = new RouterLink("See Files",MainWebPage.class);
        appRouter.setHighlightCondition(HighlightConditions.sameLocation());
        addToDrawer(new VerticalLayout(appRouter));
    }

    private void createAppDrawer() {
        appLogo = new H1("FileLoc");
        appLogo.addClassNames("text-l", "m-m");
        appLogo.setWidth("100");
        HorizontalLayout layout = new HorizontalLayout(new DrawerToggle(),appLogo,logoutButton);
        layout.setSizeFull();
        layout.expand(appLogo);
        layout.addClassNames("py-0", "px-m");
        addToNavbar(layout);
    }


}
