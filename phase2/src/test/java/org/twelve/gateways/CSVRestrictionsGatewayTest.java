package org.twelve.gateways;

import entities.Restrictions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CSVRestrictionsGatewayTest {
    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void getRestrictionTest() throws IOException {
        File csv = temporaryFolder.newFile("test.csv");

        FileWriter fw = new FileWriter(csv.getAbsolutePath(), false);
        String first_row = "lend_limit,incomplete_limit,weekly_limit\n";
        String second_row = "1,2,3";
        fw.write(first_row + second_row);
        fw.flush();
        fw.close();

        RestrictionsGateway gateway = new CSVRestrictionsGateway(csv.getAbsolutePath());

        Restrictions restrictions = gateway.getRestrictions();

        assert(restrictions.getLendMoreThanBorrow()==1);
        assert(restrictions.getMaxIncompleteTrade()==2);
        assert(restrictions.getMaxWeeklyTrade()==3);
    }

    @Test
    public void updateRestircionsTest() throws IOException{
        File csv = temporaryFolder.newFile("test.csv");

        FileWriter fw = new FileWriter(csv.getAbsolutePath(), false);
        String first_row = "lend_limit,incomplete_limit,weekly_limit\n";
        String second_row = "1,2,3";
        fw.write(first_row + second_row);
        fw.flush();
        fw.close();

        RestrictionsGateway gateway = new CSVRestrictionsGateway(csv.getAbsolutePath());

        Restrictions newRestrictions = new Restrictions(5,6,7);
        boolean updated = gateway.updateRestrictions(newRestrictions);

        assert(updated==true);
    }
}
