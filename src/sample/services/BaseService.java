/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sample.services;

import java.io.*;
import java.util.ArrayList;

public abstract class BaseService {
    public ArrayList<String> getAllLinesFromTextFile(String filename)
    {
        ArrayList<String> allLines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(
                new FileReader(filename))
        )
        {
            // Read lines from file.
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                allLines.add(line);
            }
            reader.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allLines;
    }

    public boolean saveAllLinesToTextFile(ArrayList<ArrayList<String>> fileContent, String pathname)
    {
        boolean success = true;
        try {
            File file = new File(pathname);
            FileWriter fw = new FileWriter(file);
            PrintWriter pw = new PrintWriter(fw);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < fileContent.size() ; i++) {
                ArrayList<String> arr = fileContent.get(i);
                for (int j = 0; j < arr.size() ; j++) {
                    sb.append(arr.get(j));
                    if(j < arr.size() - 1)
                        sb.append("\t");
                }
                if(i < fileContent.size() - 1)
                    sb.append("\n");
            }
            pw.println(sb.toString());
            pw.close();
        }
        catch (IOException e)
        {
            success = false;
        }
        return success;
    }
}
