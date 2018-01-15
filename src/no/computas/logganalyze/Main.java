package no.computas.logganalyze;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
overodnet: skal telle rows affected per tabel for å finne frem til linja
skal finne linjer som ikke er
START innlasting av tomerik1

(row(s) affected)
SLUTT innlasting av tomerik1

logg disse med tabellen som har feilen....
 */
public class Main {

    public static void main(String[] args) throws Exception {
        // write your code here
        String filename = args[0];

        Main m = new Main();
        m.go(filename);
        System.out.println(filename);
    }

    void go(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String         line = null;
        StringBuilder  stringBuilder = new StringBuilder();
        String         ls = System.getProperty("line.separator");

        List l = new ArrayList();

        String currenttable ="";

        int rowsaffectedpertable = 0 ;
        int errorcounterpertable = 0;
        int tablecounter = 0;

        try {
            while((line = reader.readLine()) != null) {

                if ( line.contains("START innlasting av")) {
//                    internallinecounter = 0;

                    currenttable = line.substring(line.indexOf(" av ") + 4,line.length());
                    tablecounter ++;
                    rowsaffectedpertable = 0;
                    errorcounterpertable = 0;
                } else if ("".equals(line.trim())){
                    //do nothing
                } else if (line.contains("SLUTT innlasting")){
                    // do nothing
                }
                else if ( line.contains(" row(s) affected")){
                    // do nothing
                    rowsaffectedpertable ++;
                } else {

                    // problem
                    // System.out.println("currenttable" + currenttable + " got errorlog: " + rowsaffectedpertable*2) ;

                    // en feil tar 3 linjer og en vanlig rad tar 2 liner
                    System.out.println(currenttable + " got error at line in csv file : " + ((rowsaffectedpertable*2)+1 + errorcounterpertable*2)) ;
                    String followUp =  reader.readLine();
                    System.out.println(followUp);

                            if (followUp.startsWith("The INSERT statement conflicted")) {
                                String followup2 = reader.readLine();
                                System.out.println(  followup2);
                            }
                    System.out.println("");
                    errorcounterpertable ++ ;
                }




            }

            } finally {
            reader.close();
        }
    }
}
