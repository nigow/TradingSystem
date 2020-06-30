package gateways;
import entities.Item;
import java.io.*;
import java.util.*;

/**
 * An item gateway that uses csv files as persistent storage.
 */
public class CSVItemsGateway implements ItemsGateway {

    /**
     * The filepath to the csv file persisting items
     */
    private final String filepath; //path to the csv file

    /**
     * The HashMap that maps the item ID against the item object
     */
    private Map<Integer, Item> itemMap;

    /**
     * Constructor for CSVItemsGateway that sets the filepath of the csv file
     * @param filepath the filepath of the csv file
     */
    public CSVItemsGateway(String filepath){
        //setting attributes
        this.filepath = filepath;
        this.itemMap = new HashMap<>();

        try {
            File f = new File(filepath);

            //Set the header row if the csv is empty
            if(f.length() == 0){
                save();

            }else{
                //pre-processing the file reading system
                BufferedReader br = new BufferedReader(new FileReader(f));
                br.readLine();
                String line;

                //skip the first line
                line = br.readLine();

                //read the csv file, and create items corresponding the lines
                while(line != null){
                    if (line != ""){
                        String[] data = line.split(",");
                        int id = Integer.valueOf(data[0]);
                        Item item = createItem(data);
                        itemMap.put(id, item);
                    }

                    line = br.readLine();
                }
                br.close();
            }

        }catch(IOException e){
            System.out.println("A wrong file path given");
        }

    }

    /**
     * Helper method that writes the csv file according to the itemMap.
     */
    private void save(){
        try{
            //erase the entire file content
            new FileWriter(filepath, false).close();

            //rewrite the header
            FileWriter fw = new FileWriter(filepath, true);
            fw.write("item_id,name,description,is_approved,owner_id,wishlist_account_id\n");

            //rewrite the entire items
            for(Item item: itemMap.values()){
                fw.write(convertItemToString(item));

            }

            fw.flush();
            fw.close();

        }catch(IOException e){
            System.out.println("Wrong filepath");
        }
    }

    /**
     * return a String representation of the item that is CSV friendly.
     * @param item Item object to be converted
     * @return the csv-friendly representation of the item
     */
    private String convertItemToString(Item item){
        final String COMMA = ",";

        String newLine = "";
        String wishlistString = "";

        //cast wishlist id to a string, each separated by an exclamation mark
        for(int i=0; i<item.getAccountsWithItemInWishlist().size(); i++){
            wishlistString += item.getAccountsWithItemInWishlist().get(i);

            if (i != item.getAccountsWithItemInWishlist().size() - 1){
                wishlistString += " ";
            }
        }

        //Avoid index out of range when there is nothing in the wishlist
        if (wishlistString.equals("")) wishlistString = " ";

        //cast the item to a csv-friendly format
        newLine += item.getItemID() + COMMA
                + item.getName() + COMMA
                + item.getDescription() + COMMA
                + item.isApproved() + COMMA
                + item.getOwnerID() + COMMA
                + wishlistString + "\n";
        return newLine;

    }

    /**
     * Helper method that returns an Item object with a given string of text in csv
     * Precondition: id is valid
     * @param data List of strings contained in a line, separated by commas
     * @return Item possessing the given ID.
     */
    private Item createItem(String[] data){
        //create an item
        int id = Integer.valueOf(data[0]);
        String name = data[1];
        String description = data[2];
        Boolean isApproved = Boolean.valueOf(data[3]);
        int ownerID = Integer.valueOf(data[4]);
        Item item = new Item(id, name, description, ownerID);

        //owner IDs separated by exclamation marks
        String[] oldWishlist = data[5].split(" ");

        //set other attributes from the csv file
        for(String oneId:oldWishlist){
            item.addToAccountsWithItemsInWishlist(Integer.valueOf(oneId));
        }
        if(isApproved){
            item.approve();
        }
        return item;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Item findById(int id){
        //check if the ID exists
        if(itemMap.containsKey(id)){
            return itemMap.get(id);
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateItem(Item item){
        itemMap.put(item.getItemID(), item);
        save();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Item> getAllItems(){
        List<Item> allItems = new ArrayList<>();
        for(Item item: itemMap.values()){
            allItems.add(item);

        }
        return allItems;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public int generateValidId(){
        //A unique ID is defined as the current size of the books + 1
        if (itemMap.size() == 0) return 0;
        return Collections.max(itemMap.keySet()) + 1;

    }
}
