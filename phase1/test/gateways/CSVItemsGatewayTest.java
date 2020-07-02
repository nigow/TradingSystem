package gateways;

import entities.Item;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CSVItemsGatewayTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();


    @Test
    public void findByIdNullTest() throws IOException{
        File csv = temporaryFolder.newFile("test.csv");
        ItemsGateway gateway = new CSVItemsGateway(csv.getAbsolutePath());
        assert(gateway.findById(1) == null);
    }

    @Test
    public void findByIdNotNullTest() throws IOException {
        File csv = temporaryFolder.newFile("test.csv");
        ItemsGateway gateway = new CSVItemsGateway(csv.getAbsolutePath());

        Item item = new Item(1,"Test","Hoge",114514);

        gateway.updateItem(item);
        assert(gateway.findById(1) == item);
    }

    @Test
    public void updateItemNew() throws IOException{
        File csv = temporaryFolder.newFile("test.csv");
        ItemsGateway gateway = new CSVItemsGateway(csv.getAbsolutePath());

        Item item = new Item(1,"Test","Hoge",114514);

        boolean bool = gateway.updateItem(item);
        assert(bool==true);

    }

    @Test
    public void updateItemExisting() throws IOException{
        File csv = temporaryFolder.newFile("test.csv");
        ItemsGateway gateway = new CSVItemsGateway(csv.getAbsolutePath());

        Item old = new Item(1,"Foo","Bar",810);
        gateway.updateItem(old);
        Item item = new Item(1,"Test","Hoge",114514);
        gateway.updateItem(item);

        Map<Integer, Item> items = new HashMap<>();
        items.put(item.getItemID(), item);

        ItemsGateway inMemory = new InMemoryItemGateway(new HashMap<Integer, Item>(){{
            put(item.getItemID(), item);
        }});

        assert(gateway.findById(item.getItemID()) == inMemory.findById(item.getItemID()));
    }

    @Test
    public void getAllItemsNoItem() throws IOException{
        File csv = temporaryFolder.newFile("test.csv");
        ItemsGateway gateway = new CSVItemsGateway(csv.getAbsolutePath());

        assert(gateway.getAllItems().isEmpty()==true);
    }

    @Test
    public void getAllItemsOneItem() throws IOException{
        File csv = temporaryFolder.newFile("test.csv");

        Item item1 = new Item(1,"Test","Hoge",114514);
        Item item2 = new Item(2,"Foo","Bar",810);

        FileWriter fw = new FileWriter(csv, true);
        fw.write("item_id,name,description,is_approved,owner_id,wishlist_account_id\n");
        fw.write("1,Test,Hoge,false,114514, \n");
        fw.write("2,Foo,Bar,false,810, ");

        fw.flush();
        fw.close();

        ItemsGateway gateway = new CSVItemsGateway(csv.getAbsolutePath());

        assert(gateway.getAllItems().get(0).getOwnerID()==item1.getOwnerID());
        assert(gateway.getAllItems().get(1).getOwnerID()==item2.getOwnerID());
    }

    @Test
    public void generateValidId0() throws IOException{
        File csv = temporaryFolder.newFile("test.csv");
        ItemsGateway gateway = new CSVItemsGateway(csv.getAbsolutePath());

        int id = gateway.generateValidId();
        assert(id==1);
    }

    @Test
    public void generateValidId1() throws IOException{
        File csv = temporaryFolder.newFile("test.csv");

        FileWriter fw = new FileWriter(csv, true);
        fw.write("item_id,name,description,is_approved,owner_id,wishlist_account_id\n");
        fw.write("1,Test,Hoge,false,114514, \n");
        fw.write("2,Foo,Bar,false,810, ");

        fw.flush();
        fw.close();

        ItemsGateway gateway = new CSVItemsGateway(csv.getAbsolutePath());

        int id = gateway.generateValidId();
        assert(id==3);
    }

    @Test
    public void deleteItemOne() throws IOException{
        File csv = temporaryFolder.newFile("test.csv");

        Item item = new Item(2,"Foo","Bar",810);

        FileWriter fw = new FileWriter(csv, true);
        fw.write("item_id,name,description,is_approved,owner_id,wishlist_account_id\n");
        fw.write("1,Test,Hoge,false,114514, \n");
        fw.write("2,Foo,Bar,false,810, ");

        fw.flush();
        fw.close();

        ItemsGateway gateway = new CSVItemsGateway(csv.getAbsolutePath());

        boolean removed = gateway.deleteItem(item);

        assert(removed==true);
    }

    @Test
    public void deleteItemNone() throws IOException{
        File csv = temporaryFolder.newFile("test.csv");
        ItemsGateway gateway = new CSVItemsGateway(csv.getAbsolutePath());

        Item item = new Item(1,"Test","Hoge",114514);

        boolean removed = gateway.deleteItem(item);

        assert(removed==false);
    }
}
