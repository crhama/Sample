/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sample.services;

import sample.entities.Book;
import sample.entities.User;

import java.util.ArrayList;

public class UserService  extends BaseService {
    private ArrayList<User> users;
    private boolean isLoggedIn = false;
    private User loggedUser;

    private Integer maxCap = 3;
    private Book[] myBooks = new Book[maxCap];

    public UserService()
    {
        users = new ArrayList<User>();
        ArrayList<String> lines = getAllLinesFromTextFile ("src/sample/data/users.txt");

        lines.forEach(line -> {
            User user = new User();
            String[] parts = line.split("\t");
            for (int i = 0; i < parts.length; i++) {

                System.out.println(parts);
                if(i == 0) user.setUsername(parts[i]);
                else if(i == 1) user.setPassword(parts[i]);
            }
            users.add(user);
        });
    }

    public boolean getIsLoggedIn()
    {
        return isLoggedIn;
    }
    public String getLoggedUserName()
    {
        return loggedUser.getUsername();
    }

    public boolean logUser(String username, String pwd)
    {
        if(username != null &&
                username.length() > 0 &&
                pwd != null &&
                pwd.length() > 0) {

            for (User u: users)
            {
                if (u.getUsername().toLowerCase().equals(username.toLowerCase())
                        && u.getPassword().equals(pwd)) {
                    isLoggedIn = true;
                    loggedUser = u;
                    break;
                }
            }
        }
        return isLoggedIn;
    }

    public boolean addItemToMyBooks(Book book)
    {
        boolean success = true;
        ArrayList<Integer> emptySlots = new ArrayList<>();
        for (int i = 0; i < maxCap; i++) {
            Book b = myBooks[i];
            if(b == null) emptySlots.add(i);
            else {
                if(b.getTitle().equals(book.getTitle()))
                {
                    success = false;
                    break;
                }
            }
        }

        if(success)
        {
            if(!emptySlots.isEmpty())
            {
                myBooks[emptySlots.get(0)] = book;
            }
        }
        return success;
    }

//    public boolean removeItemFromMyBooks(Book book)
//    {
//        boolean success = false;
//        for (int i = 0; i < maxCap; i++) {
//            Book b = myBooks[i];
//            if(b != null)
//            {
//                if(b.getTitle().equals(book.getTitle()))
//                {
//                    success = true;
//                    myBooks[i] = null;
//                    break;
//                }
//            }
//        }
//
//        return success;
//    }

    public Book[] getMyBooks()
    {
        return myBooks;
    }
}
