/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sample.utils;

import sample.entities.Book;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class QuickSort {
    public static ArrayList<Book> sort(ArrayList<Book> books)
    {
        Book[] arr = books.toArray(new Book[books.size()]);
        quickSort(arr, 0, arr.length - 1);
        ArrayList<Book> list = new ArrayList<>();
        Collections.addAll(list, arr);
        return list;
    }

    private static void quickSort(Book[] books, int low, int high)
    {
        if(low < high + 1){
            int p = partition(books, low, high);
            quickSort(books, low, p - 1);
            quickSort(books, p + 1, high);
        }
    }

    private static void swap(Book[] books, int index1, int index2)
    {
        Book temp = books[index1];
        books[index1] = books[index2];
        books[index2] = temp;
    }

    private static int getPivot(int low, int high)
    {
        Random r = new Random();
        return r.nextInt((high - low) + 1) + low;
    }

    private static int partition(Book[] books, int low, int high)
    {
        swap(books, low, getPivot(low, high));
        int border = low + 1;
        for (int i = border; i <= high; i++) {
            if(books[i].getTitle().compareTo(books[low].getTitle()) < 0)
            {
                swap(books, i, border++);
            }
        }
        swap(books, low, border - 1);
        return border - 1;
    }
}
