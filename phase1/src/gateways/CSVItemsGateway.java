package gateways;
import entities.Item;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVItemsGateway implements ItemsGateway {
    private final String filepath; //path to the csv file

    /**
     * Constructor for CSVItemsGateway that sets the filepath of the csv file
     * @param filepath the filepath of the csv file
     */
    public CSVItemsGateway(String filepath){
        this.filepath = filepath;
    }

    /**
     * Helper method that returns an Item object with a given string of text in csv
     * Precondition: id is valid
     * @param id ID of desired item.
     * @param data List of strings contained in a line, separated by commas
     * @return Item possessing the given ID.
     */
    private Item createItem(int id, String[] data){
        //create an item
        String name = data[1];
        String description = data[2];
        Boolean isApproved = Boolean.valueOf(data[3]);
        int ownerID = Integer.valueOf(data[4]);
        Item item = new Item(id, name, description, ownerID);

        //owner IDs separated by exclamation marks
        String[] oldWishlist = data[5].split("!");

        //set other attributes from the csv file
        for(String oneId:oldWishlist){
            item.addToAccountsWithItemsInWishlist(Integer.valueOf(oneId));
        }
        if(isApproved){
            item.approve();
        }
        return item;
    }

    public Item findById(int id) throws IOException{
        //pre-processing the file reading system
        File f = new File(filepath);
        BufferedReader br = new BufferedReader(new FileReader(f));
        String line = br.readLine();

        //search the line that matches the id
        while(line!= null){
            String[] data = line.split(",");
            System.out.println(data[0]);

            //first column stores its ID
            if (id == Integer.valueOf(data[0])){
                br.close();
                return createItem(id, data);
            }

            line = br.readLine();
        }

        //item not found
        br.close();
        return null;

    }

    public void updateItem(Item item) throws IOException{
        final String COMMA = ",";

        String newLine = "";
        String wishlistString = "";

        //cast wishlist id to a string, each separated by an exclamation mark
        for(int i=0; i<item.getAccountsWithItemInWishlist().size(); i++){
            wishlistString += item.getAccountsWithItemInWishlist().get(i);
            if (i != item.getAccountsWithItemInWishlist().size() - 1){
                wishlistString += "!";
            }
        }

        //cast the item to a csv-friendly format
        newLine += item.getItemID() + COMMA
                + item.getName() + COMMA
                + item.getDescription() + COMMA
                + item.isApproved() + COMMA
                + item.getOwnerID() + COMMA
                + wishlistString + "\n";

        //append the new line without erasing other rows and close the stream
        FileWriter fw = new FileWriter(filepath, true);
        fw.write(newLine);
        fw.flush();
        fw.close();
    }

    public List<Item> getAllItems() throws IOException{
        //preprocessing a file stream
        BufferedReader br = new BufferedReader(new FileReader(filepath));
        String line = br.readLine();
        List<Item> allItems = new ArrayList<>();

        //traverse all rows
        while (line != null){
            String[] data = line.split(",");
            allItems.add(createItem(Integer.valueOf(data[0]), data));
            line = br.readLine();
        }

        br.close();
        return allItems;
    }

    public int generateValidId() throws IOException{
        //...A unique ID is defined by its row number...//

        //preprocessing a file stream
        BufferedReader br = new BufferedReader(new FileReader(filepath));
        int counter = 0;
        String line = br.readLine();

        //traverse to the end of csv
        while(line != null){
            counter++; //increment until the last row
            line = br.readLine();
        }

        br.close();
        return counter;
    }
}
