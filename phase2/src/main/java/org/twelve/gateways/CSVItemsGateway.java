package org.twelve.gateways;

import org.twelve.entities.Item;

import java.io.*;
import java.util.*;

/**
 * An item gateway that uses csv files as persistent storage.
 *
 * @author Tairi
 */
public class CSVItemsGateway implements ItemsGateway {

    /**
     * The filepath to the csv file persisting items.
     */
    private final String filepath; //path to the csv file

    /**
     * The HashMap that maps the item ID against the item object.
     */
    private final Map<Integer, Item> itemMap;

    /**
     * Constructor for CSVItemsGateway that sets the filepath of the csv file.
     *
     * @param filepath the filepath of the csv file
     * @throws IOException If the given csv file cannot be accessed
     */
    public CSVItemsGateway(String filepath) throws IOException {
        //setting attributes
        this.filepath = filepath;
        this.itemMap = new HashMap<>();


        File f = new File(filepath);

        //Set the header row if the csv is empty
        if (f.length() == 0) {

            if (!save()) throw new IOException();

        } else {
            //pre-processing the file reading system
            BufferedReader br = new BufferedReader(new FileReader(f));
            br.readLine();
            String line;

            //skip the first line
            line = br.readLine();

            //read the csv file, and create items corresponding the lines
            while (line != null) {
                if (!line.equals("")) {
                    String[] data = line.split(",");
                    int id = Integer.parseInt(data[0]);
                    Item item = createItem(data);
                    itemMap.put(id, item);
                }

                line = br.readLine();
            }
            br.close();
        }

    }

    /**
     * Helper method that writes the csv file according to the itemMap.
     */
    private boolean save() {
        try {
            //erase the entire file content
            new FileWriter(filepath, false).close();

            //rewrite the header
            FileWriter fw = new FileWriter(filepath, true);
            fw.write("item_id,name,description,is_approved,owner_id\n");

            //rewrite the entire items
            for (Item item : itemMap.values()) {
                fw.write(convertItemToString(item));

            }

            fw.flush();
            fw.close();
            return true;

        } catch (IOException e) {
            System.out.println("Wrong filepath");
            return false;
        }
    }

    /**
     * Return a String representation of the item that is CSV friendly.
     *
     * @param item Item object to be converted
     * @return the csv-friendly representation of the item
     */
    private String convertItemToString(Item item) {
        final String COMMA = ",";

        String newLine = "";


        //cast the item to a csv-friendly format
        newLine += item.getItemID() + COMMA
                + item.getName() + COMMA
                + item.getDescription() + COMMA
                + item.isApproved() + COMMA
                + item.getOwnerID() + "\n";
        return newLine;

    }

    /**
     * Helper method that returns an Item object with a given string of text in csv.
     * Precondition: id is valid
     *
     * @param data List of strings contained in a line, separated by commas
     * @return Item possessing the given ID
     */
    private Item createItem(String[] data) {
        //create an item
        int id = Integer.parseInt(data[0]);
        String name = data[1];
        String description = data[2];
        boolean isApproved = Boolean.parseBoolean(data[3]);
        int ownerID = Integer.parseInt(data[4]);
        Item item = new Item(id, name, description, ownerID);


        if (isApproved) {
            item.approve();
        }
        return item;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Item findById(int id) {
        //check if the ID exists
        if (itemMap.containsKey(id)) {
            return itemMap.get(id);
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateItem(Item item) {
        itemMap.put(item.getItemID(), item);
        return save();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Item> getAllItems() {
        return new ArrayList<>(itemMap.values());
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public int generateValidId() {
        //A unique ID is defined as the current size of the books + 1
        if (itemMap.size() == 0) return 1;
        return Collections.max(itemMap.keySet()) + 1;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteItem(Item item) {
        if (itemMap.containsKey(item.getItemID())) {
            itemMap.remove(item.getItemID());
            return save();
        }
        return false;
    }
}
