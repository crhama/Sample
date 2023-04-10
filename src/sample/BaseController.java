/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sample;

import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import sample.entities.AlertMessage;
import sample.entities.Book;
import sample.entities.Status;

import java.util.Optional;

public abstract class BaseController implements Initializable {
    protected void log(String text){
        System.out.println(text);
    }

    protected void AddBookItemToPane(FlowPane parentPane, Book book){
        //pane 1
//        BackgroundFill background_fill1 = new BackgroundFill(Color.PINK,
//                CornerRadii.EMPTY, Insets.EMPTY);
//        Background background1 = new Background(background_fill1);
        Pane pane = new Pane();
//        pane.setBackground(background1);

        pane.setPrefWidth(240);
        pane.setPrefHeight(270);
        pane.setBorder(new Border(new BorderStroke(Color.SILVER,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        String imagePath = "/sample/images/" + book.getImageFileName();
        Image image = new
                Image(getClass().getResourceAsStream(imagePath));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(150);
        imageView.setFitHeight(150);
        imageView.setLayoutX(14);
        imageView.setLayoutY(15);

        Label titleLabel = new Label(book.getTitle());
        titleLabel.setLayoutX(14);
        titleLabel.setLayoutY(165);
        titleLabel.setFont(new Font(18));
        titleLabel.setTextFill(Color.rgb(26, 12, 223));
        titleLabel.setMaxWidth(150);
        titleLabel.setWrapText(true);

        String authorStr = "By " + book.getAuthorName();
        Label authorLabel = new Label(authorStr);
        authorLabel.setLayoutX(14);
        authorLabel.setLayoutY(208);
        authorLabel.setMaxWidth(150);
        authorLabel.setWrapText(true);

        Button addToCartButton = new Button("Add To Cart");
        addToCartButton.setLayoutX(14);
        addToCartButton.setLayoutY(235);
        addToCartButton.setPrefWidth(150);
        addToCartButton.setOnMouseClicked(e -> {
            AddBookToMyBookPane(book);
        });

        pane.getChildren().addAll(imageView, titleLabel, authorLabel, addToCartButton);

        parentPane.getChildren().addAll(pane);
    }

    protected abstract void AddBookToMyBookPane(Book book);

    protected void AddBookItemToMyBookPane(FlowPane parentPane, Book book)
    {
        Pane pane = new Pane();
        pane.setPrefWidth(180);
        pane.setPrefHeight(200);
        pane.setBorder(new Border(new BorderStroke(Color.SILVER,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        String imagePath = "/sample/images/" + book.getImageFileName();
        Image image = new
                Image(getClass().getResourceAsStream(imagePath));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(150);
        imageView.setFitHeight(150);
        imageView.setLayoutX(14);
        imageView.setLayoutY(15);

        Button returnBookButton = new Button("Return Book");
        returnBookButton.setLayoutX(14);
        returnBookButton.setLayoutY(170);
        returnBookButton.setPrefWidth(150);
        returnBookButton.setOnMouseClicked(e -> {
            System.out.println("Book to Return: " + book.getTitle());
            removeBookFromMyBookPane(book);
        });

        pane.getChildren().addAll(imageView, returnBookButton);
        parentPane.getChildren().addAll(pane);
    }

    protected void AddBookItemToMyWishPane(FlowPane parentPane, Book book)
    {
        Pane pane = new Pane();
        pane.setPrefWidth(180);
        pane.setPrefHeight(200);
        pane.setBorder(new Border(new BorderStroke(Color.SILVER,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        String imagePath = "/sample/images/" + book.getImageFileName();
        Image image = new
                Image(getClass().getResourceAsStream(imagePath));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(150);
        imageView.setFitHeight(150);
        imageView.setLayoutX(14);
        imageView.setLayoutY(15);

//        Button returnBookButton = new Button("Return Book");
//        returnBookButton.setLayoutX(14);
//        returnBookButton.setLayoutY(170);
//        returnBookButton.setPrefWidth(150);
//        returnBookButton.setOnMouseClicked(e -> {
//            System.out.println("Book to Return: " + book.getTitle());
//            removeBookFromMyBookPane(book);
//        });

        pane.getChildren().addAll(imageView);
        parentPane.getChildren().addAll(pane);
    }

    protected abstract void removeBookFromMyBookPane(Book book);

    protected void displayAlert(Status canBeRanted, Book book, String username)
    {
        AlertMessage amsg = new AlertMessage(canBeRanted, book);
        Alert a = new Alert(amsg.getAlertType());
        a.setTitle(amsg.getTitle());
        a.setContentText(amsg.getMessage());
        if(amsg.getAlertType() == Alert.AlertType.CONFIRMATION) {
            Optional<ButtonType> btnType = a.showAndWait();
            if(btnType.get() == ButtonType.OK)
            {
                makeBookReservation(book, username);
            }
        }
        else {
            a.showAndWait();
        }
    }

    protected abstract void makeBookReservation(Book book, String username);
}
