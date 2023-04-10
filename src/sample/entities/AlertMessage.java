/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sample.entities;

import javafx.scene.control.Alert;

/**
 *
 * @author crham
 */
public class AlertMessage {
    private Alert.AlertType alertType;
    private String title;
    private String message;

    public AlertMessage(Status canBeRanted, Book book)
    {
        if(canBeRanted == Status.NEEDRESERVATION)
        {
            alertType = Alert.AlertType.CONFIRMATION;
            title = "Need to Reserve '"+ book.getTitle() + "'";
            message = "this book has been rented. \n Would you like to make a reservation?";
        }
        if(canBeRanted == Status.USERHAVEBOOK)
        {
            alertType = Alert.AlertType.ERROR;
            title = "Cannot Rent the Book";
            message = "Book '" + book.getTitle() + "' is already in your custody";
        }
    }

    public Alert.AlertType getAlertType()
    {
        return alertType;
    }

    public String getTitle()
    {
        return title;
    }

    public String getMessage()
    {
        return message;
    }
}
