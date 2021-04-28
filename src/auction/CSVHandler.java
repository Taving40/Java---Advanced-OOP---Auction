package auction;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CSVHandler {
    private static CSVHandler single_instance = null;

    public static CSVHandler getInstance(){
        if (single_instance == null)
            single_instance = new CSVHandler();
        return single_instance;
    }

    public ArrayList<Buyer> parseCSVBuyers(ArrayList<String> str) throws IOException{
        ArrayList<Buyer> buyers = new ArrayList<>();
        if(!str.get(0).equals("username, password, email")){
            throw new IOException("Error on parsing buyers.csv");
        }
        str.remove(0);
        for(String line: str){
            String[] temp_line = line.split(",");
            buyers.add(new Buyer(temp_line[0].strip(), temp_line[1].strip(), temp_line[2].strip()));
            }
        return buyers;
    }

    public ArrayList<Evaluator> parseCSVEvaluators(ArrayList<String> str) throws IOException {
        ArrayList<Evaluator> evaluators = new ArrayList<>();
        if(!str.get(0).equals("username, password, email, list of conflicts")){
            throw new IOException("Error on parsing evaluators.csv");
        }
        str.remove(0);
        for(String line: str){
            String[] temp_line = line.split(",");
            Evaluator temp_ev = new Evaluator(temp_line[0].strip(), temp_line[1].strip(), temp_line[2].strip());
            if(temp_line[3].equals(""))
                evaluators.add(temp_ev);
            else{
                temp_ev.setConflicts(Arrays.asList(temp_line[3].strip().split(" ")));
                evaluators.add(temp_ev);
            }
        }
        return evaluators;
    }

    public Inventory parseCSVInventory(ArrayList<String> str, Inventory inv, ArrayList<Evaluator> evaluators) throws IOException {

        if(!str.get(0).equals("name, description, appraised price, name of evaluator")){
            throw new IOException("Error on parsing inventory.csv");
        }
        str.remove(0);
        for(String line: str){
            String[] temp_line = line.split(",");
            for(Evaluator e: evaluators){
                if(e.getUsername().equals(temp_line[3].strip())){
                    inv.storeItem(new Item(temp_line[0].strip(), temp_line[1].strip(), Integer.parseInt(temp_line[2].strip()), e));
                    break;
                }
            }
        }
        return inv;
    }

    public Scheduler parseCSVSchedule(ArrayList<String> str, Inventory inv) throws IOException {
        if(!str.get(0).equals("name, items, timestamp")){
            throw new IOException("Error on parsing schedule.csv");
        }
        str.remove(0);
        ArrayList<Auction> auctions = new ArrayList<>();
        ArrayList<LocalDate> times = new ArrayList<>();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for(String line: str){
            String[] temp_line = line.split(",");
            String[] temp_item_names = temp_line[1].strip().split(" ");
            LocalDate temp_time = LocalDate.parse((temp_line[2].strip()), dateTimeFormatter);
            times.add(temp_time);
            Auction temp_auc = new Auction();
            temp_auc.setName(temp_line[0].strip());
            for(String name: temp_item_names) {
                for (Item it : inv.getItems()) {
                    if (name.equals(it.getName())) {
                        temp_auc.addItem(it);
                        temp_auc.addInvolved(it.getE());
                    }
                }
            }
            auctions.add(temp_auc);
        }
        return new Scheduler(auctions, times);
    }

    public ArrayList<String> readCSVFile(String path) throws IOException {
        File file = new File(path);
        ArrayList<String> res = new ArrayList<>();
        if(file.exists() && file.canRead()){
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine())
            {
                res.add(sc.nextLine());
                //System.out.print(sc.nextLine());
            }
            sc.close();
        }
        return res;
    }

    public static void writeCSVAudit(String path, String line, Boolean append) throws IOException {
        File file = new File(path);
        FileWriter file_writer = new FileWriter(file, append);
        file_writer.write(line);
        file_writer.close();
    }

}
