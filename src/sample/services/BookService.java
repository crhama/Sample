/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sample.services;

import sample.entities.Book;
import sample.utils.QuickSort;

import java.util.ArrayList;

public class BookService extends BaseService {
    public ArrayList<Book> getAllBooks()
    {
        ArrayList<Book> books = new ArrayList<>();
        ArrayList<String> lines = getAllLinesFromTextFile ("src/sample/data/bookList.txt");
        lines.forEach(line -> {
            Book book = new Book();
            String[] parts = line.split("\t");
            for (int i = 0; i < parts.length; i++) {

                System.out.println(parts);
                if(i == 0) book.setImageFileName(parts[i]);
                else if(i == 1) book.setTitle(parts[i]);
                else if(i == 2) book.setAuthorName(parts[i]);
                else if(i == 3) book.setCategory(parts[i]);

            }
            books.add(book);
        });

        ArrayList<Book> sortedBooks = QuickSort.sort(books);

        return sortedBooks;
    }

    public ArrayList<String> getAllCategories(ArrayList<Book> allBooks) {
        ArrayList<String> categories = new ArrayList<>();
        categories.add("All");

        allBooks.forEach(b -> {
            if(!categories.contains(b.getCategory()))
                categories.add(b.getCategory());
        });
        return  categories;
    }

    public ArrayList<Book> geBooksByTitles(ArrayList<Book> allBooks, ArrayList<String> myTitles)
    {
        ArrayList<Book> myBooks = new ArrayList<>();
        allBooks.forEach(b -> {
            String title  = b.getTitle();
            if(myTitles.contains(title))
                myBooks.add(b);
        });
        return myBooks;
    }
}
