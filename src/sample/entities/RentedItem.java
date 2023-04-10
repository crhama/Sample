package sample.entities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class RentedItem {
    private String title;
    private Queue<String> usernameQueue = new LinkedList<>();

    public RentedItem(String title)
    {
        this.title = title;
    }

    // title
    public String getTitle() {
        return title;
    }

    //users
    public boolean addReservation(String username)
    {
        if(usernameQueue.isEmpty() || !usernameQueue.contains(username)) {
            usernameQueue.add(username);
            return true;
        }
        return false;
    }

    public boolean userHasTheBook(String username)
    {
        if(!usernameQueue.isEmpty()) {
            String name = usernameQueue.peek();
            return name.equals(username);
        }
        return false;
    }

    public boolean userIsAwaitingForTheBook(String username)
    {
        if(!usernameQueue.isEmpty()) {
            String name = usernameQueue.peek();
            if(name.equals(username)) return false;
            else {
               return usernameQueue.contains(username);
            }
        }
        return false;
    }

    public Status needToMakeReservation(String username)
    {
        if(usernameQueue.isEmpty()) return Status.CANRENT;
        else
        {
            return usernameQueue.contains(username) ? Status.USERHAVEBOOK : Status.NEEDRESERVATION;
        }
    }

    public boolean returnBook(String username)
    {
        String name = usernameQueue.remove();
        return name != null;
    }

    public static ArrayList<ArrayList<String>> getDataToSave(ArrayList<RentedItem> rentedItems)
    {
        ArrayList<ArrayList<String>> fileContent = new ArrayList<ArrayList<String>>();
        for (RentedItem rItem : rentedItems)
        {
            ArrayList<String> arr = new ArrayList<String>();
            arr.add(rItem.getTitle());
            Iterator<String> iterator = rItem.usernameQueue.iterator();
            while (iterator.hasNext())
            {
                arr.add(iterator.next());
            }
            fileContent.add(arr);
        }
        return fileContent;
    }
}

