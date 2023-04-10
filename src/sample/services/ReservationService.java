/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sample.services;

import sample.entities.Book;
import sample.entities.RentedItem;
import sample.entities.Status;

import java.util.ArrayList;

public class ReservationService extends BaseService {
    private ArrayList<RentedItem> rentedItems = new ArrayList<RentedItem>();
    private String pathname = "src/sample/data/reservations.txt";

    public ReservationService()
    {
        ArrayList<String> lines = getAllLinesFromTextFile(pathname);
        lines.forEach(line -> {
            String[] parts = line.split("\t");
            if(parts.length > 1) {
                RentedItem rentedItem = new RentedItem(parts[0]);
                for (int i = 1; i < parts.length; i++) {
                    rentedItem.addReservation(parts[i]);
                }
                rentedItems.add(rentedItem);
            }
        });
    }

    public Status bookCanBeRented(Book book, String username)
    {
        if(rentedItems.isEmpty()) return Status.CANRENT;

        RentedItem rItem = findRentedItem(book);
        if(rItem == null) return Status.CANRENT;
        return rItem.needToMakeReservation(username);
    }

    public boolean registerReservation(Book book, String username)
    {
        RentedItem rItem = findRentedItem(book);
        if(rItem == null) {
            rItem = new RentedItem(book.getTitle());
            rentedItems.add(rItem);
        }
        return rItem.addReservation(username);
//        if(success) saveAllLinesToTextFile(RentedItem.getDataToSave(rentedItems));
//        return success;
    }

    public boolean returnBook(Book book, String username)
    {
        RentedItem rItem = findRentedItem(book);
        if(rItem == null) return true;
        else return rItem.returnBook(username);
    }

    private RentedItem findRentedItem(Book book)
    {
        RentedItem rItem = null;
        for (int i = 0; i < rentedItems.size(); i++) {
            RentedItem item = rentedItems.get(i);
            if(item.getTitle().equals(book.getTitle()))
            {
                rItem = item;
                break;
            }
        }
        return rItem;
    }

    public boolean saveAllRegistration()
    {
        ArrayList<ArrayList<String>> data = RentedItem.getDataToSave(rentedItems);
        return saveAllLinesToTextFile(data, pathname);
    }

    public ArrayList<String> getLoggedUserBooks(String username)
    {
        ArrayList<String> titles = new ArrayList<String>();
        rentedItems.forEach(r -> {
            if(r.userHasTheBook(username))
                titles.add(r.getTitle());
        });
        return titles;
    }

    public ArrayList<String> getLoggedUserWishList(String username)
    {
        ArrayList<String> titles = new ArrayList<String>();
        rentedItems.forEach(r -> {
            if(r.userIsAwaitingForTheBook(username))
                titles.add(r.getTitle());
        });
        return titles;
    }

}
