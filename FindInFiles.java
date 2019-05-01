import java.util.Arrays;
import java.io.*;

public class FindInFiles {
    private String keyword;
    private String[] types;
    
    public FindInFiles(String[] args) {
        File directory = new File(args[0]);
        if (!directory.isDirectory()) {
            System.out.print("Error: Bad directory. \n");
            return;
        }
        keyword = args[1];
        types = Arrays.copyOfRange(args, 2, args.length);
        readFolder(directory);
    }

    public static void main(String[] args) {
        if (args.length >= 3) {
            new FindInFiles(args);
        } else {
            System.out.print("Error: Requires directory, keyword, and type(s). \n\nTry: 'java FindInfiles \"directory\" \"keyword\" file-types' \n\nExample: 'java FindInfiles \"C:/Users/Owner/Documents\" \"Hello World\" txt rtf json xml' \n");
        }
    }

    private void readFolder(File folder) {
        for (File file : folder.listFiles()) {
            if (file.isDirectory()) {
                readFolder(file);
            } else {
                String myType = file.getName();
                myType = myType.substring(myType.lastIndexOf(".") + 1);
                for (String type : types) {
                    if (myType.equals(type)) {
                        readFile(file);
                    }
                }   
            }
        }
    }

    private void readFile(File file) {
        try {
            FileReader reader = new FileReader(file);
            StringBuffer data = new StringBuffer();
            int i; 
            while ((i=reader.read()) != -1) {
                data.append((char) i);
            }
            int count = 0;
            i=-1;
            while ((i=data.indexOf(keyword, i+1)) != -1) {
                count += 1;
            }
            if (count > 0) {
                System.out.print(count + " hits: " + file.getPath() + "\n");
            }
        }
        catch (FileNotFoundException err) {
            return;
        }
        catch (IOException err) {
            return;
        }
    }
}