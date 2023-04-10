/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.*;
import javafx.scene.layout.*;

import sample.entities.Book;
import sample.entities.Status;
import sample.services.BookService;
import sample.services.ReservationService;
import sample.services.UserService;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller extends BaseController {
    //Login Pane
    @FXML private Pane loginPane;
    @FXML private TextField usernameTbox;
    @FXML private PasswordField pwdTbox;
    @FXML private Button loginButton;

    //Main Pane
    @FXML private Pane mainPane;
    @FXML private FlowPane contentFlowPane;
    @FXML private ListView<String> categoryListview;
    @FXML private TitledPane mainTitledPane;
    @FXML private Text username;
    @FXML private Button logoutButton;
    @FXML private TitledPane myBooksTitledPane;
    @FXML private TitledPane myWishListTitledPane;

    //My Book Pane
    @FXML private FlowPane myBooksFlowPane;

    //My wish list pane
    @FXML private FlowPane myWishListFlowPane;

    //services
    BookService bookService = new BookService();
    UserService userService;
    ReservationService reservationService;
    //data
    ArrayList<Book> allBooks;
    ArrayList<Book> booksToDisplay;
    ArrayList<String> allCategories;
//    ArrayList<Book> myBooks;

    //control variables
    private boolean appInitialized = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginButton.setOnMouseClicked(e -> {
            userService = new UserService();
            Boolean isValid = validateCreds(usernameTbox.getText(), pwdTbox.getText());
            if(isValid) {
                mainPane.setVisible(true);
                loginPane.setVisible(false);
                reservationService = new ReservationService();
                if(!appInitialized) loadGeneralData();
                loadMainPaneContent();

                if(!appInitialized) appInitialized = true;
            }
        });

        logoutButton.setOnMouseClicked(e -> {
            if(reservationService.saveAllRegistration())
            {
                nullifyState();
                mainPane.setVisible(false);
                loginPane.setVisible(true);
                usernameTbox.setText("");
                pwdTbox.setText("");
            }
        });
    }

    private boolean validateCreds(String userId, String pwd){
        return userService.logUser(userId, pwd);
    }

    private void loadMainPaneContent()
    {
        username.setText(userService.getLoggedUserName());
        if(!appInitialized) loadItemsToCategoryPane();
        loadBooksToMainPane(booksToDisplay);
        displayMyBookPane();
        displayWishListPane();
    }

    private void loadGeneralData()
    {
        allBooks = bookService.getAllBooks();
        allCategories = bookService.getAllCategories(allBooks);
        booksToDisplay = CopyContent(allBooks, "All");
    }

    private void loadItemsToCategoryPane()
    {
        categoryListview.getItems().clear();
        ObservableList<String> cateoryList = FXCollections.observableArrayList(allCategories);
        categoryListview.setItems(cateoryList);

        categoryListview.getSelectionModel().select(0);

        categoryListview.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldval, newval) -> {
                    mainTitledPane.setText(newval);
                    booksToDisplay = CopyContent(allBooks, newval);
                    loadBooksToMainPane(booksToDisplay);
                });
    }
    private void loadBooksToMainPane(ArrayList<Book> books){
        contentFlowPane.getChildren().clear();
        books.forEach(b -> AddBookItemToPane(contentFlowPane, b));
    }
    
    private ArrayList<Book> CopyContent(ArrayList<Book> a, String category){

        ArrayList<Book> b = new ArrayList<>();
        for (Book book: a) {
            if("All".equals(category))
                b.add(book);
            else
            {
                String cat1 = book.getCategory().trim().toLowerCase();
                String cat2 = category.trim().toLowerCase();

                if(cat1.equals(cat2))
                    b.add(book);
            }
        }
        return b;
    }

    protected void AddBookToMyBookPane(Book book)
    {
        Status canBeRanted = reservationService.bookCanBeRented(book, userService.getLoggedUserName());
        if(canBeRanted == Status.CANRENT) {
            Boolean success = reservationService.registerReservation(book, userService.getLoggedUserName());
            Boolean b = userService.addItemToMyBooks(book);
            if (success && b) {
                displayMyBookPane();
            }
        }
        else {
            displayAlert(canBeRanted, book, userService.getLoggedUserName());
        }
    }

    protected void makeBookReservation(Book book, String username)
    {
        reservationService.registerReservation(book, username);
        displayWishListPane();
    }

    protected void removeBookFromMyBookPane(Book book)
    {
        boolean success = reservationService
                .returnBook(book, userService.getLoggedUserName());
        displayMyBookPane();
    }

    private void displayMyBookPane()
    {
        myBooksFlowPane.getChildren().clear();
        ArrayList<String> myTitles = reservationService
                .getLoggedUserBooks(userService.getLoggedUserName());
        ArrayList<Book> myBooks = bookService.geBooksByTitles(allBooks, myTitles);

        for (Book myBook : myBooks) {
            if(myBook != null)
                AddBookItemToMyBookPane(myBooksFlowPane, myBook);
        }
        String title = "My Books (" + myBooks.size() + ")";
        myBooksTitledPane.setText(title);
    }

    private void displayWishListPane()
    {
        myWishListFlowPane.getChildren().clear();
        ArrayList<String> myTitles = reservationService
                .getLoggedUserWishList(userService.getLoggedUserName());
        ArrayList<Book> myBooks = bookService.geBooksByTitles(allBooks, myTitles);

        for (Book myBook : myBooks) {
            if(myBook != null)
                AddBookItemToMyWishPane(myWishListFlowPane, myBook);
        }
        String title = "My Wish List (" + myBooks.size() + ")";
        myWishListTitledPane.setText(title);
    }

    private void initializeState()
    {
        //services
        ReservationService reservationService = null;
        //data
        Book[] myBooks = new Book[3];
    }

    private void nullifyState()
    {
        //services
        ReservationService reservationService = null;
        //data
        Book[] myBooks = null;
    }
}
