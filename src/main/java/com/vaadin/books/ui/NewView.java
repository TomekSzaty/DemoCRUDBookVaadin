package com.vaadin.books.ui;

import com.vaadin.books.backend.Book;
import com.vaadin.books.backend.BookService;
import com.vaadin.collaborationengine.CollaborationBinder;
import com.vaadin.collaborationengine.CollaborationMessageInput;
import com.vaadin.collaborationengine.CollaborationMessageList;
import com.vaadin.collaborationengine.UserInfo;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.annotation.security.RolesAllowed;

@Route("new")
@RolesAllowed("ADMIN")
public class NewView extends VerticalLayout {

    private TextField title = new TextField("TItle");
    private DatePicker published = new DatePicker("Published");
    private IntegerField rating = new IntegerField("Rating");

    /*public NewView(BookService service) {
        var binder = new Binder<>(Book.class);
        binder.bindInstanceFields(this);
        add(
                new H1("New book"),
                new FormLayout(title, published, rating),
                new Button("Save", event -> {
                    var book = new Book();
                    binder.writeBeanIfValid(book);
                    service.add(book);
                    Notification.show("Book saved.");
                    binder.readBean(new Book());
                })
        );
    }*/
////////collaboration engine działa w wersji trial, po zalogowniu na vaadin.com do produkcji trzeba licencje////////////
    public NewView(BookService service) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        var userInfo = new UserInfo(username, username);
        var binder = new CollaborationBinder<>(Book.class, userInfo);
        binder.bindInstanceFields(this);
        binder.setTopic("new-book", () -> new Book());
        var collaborationMessageList = new CollaborationMessageList(userInfo, "new-book");
        add(
                new H1(("New book")),

                new HorizontalLayout(
                        new VerticalLayout(
                                new FormLayout(title, published, rating),
                                new Button("Save", buttonClickEvent -> {
                                    var book = new Book();
                                    binder.writeBeanIfValid(book);
                                    service.add(book);
                                    Notification.show("Book saved");
                                    binder.reset(new Book());
                                })
                        ),
                        new VerticalLayout(
                                collaborationMessageList,
                                new CollaborationMessageInput(collaborationMessageList)
                        )
                )
        );
    }
}
