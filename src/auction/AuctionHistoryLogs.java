package auction;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class AuctionHistoryLogs {
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<AuctionHistory> logs = new ArrayList<>();
    private ArrayList<LocalDate> timestamps = new ArrayList<>();
    private static AuctionHistoryLogs single_instance = null;

    public AuctionHistoryLogs(){}

    public static AuctionHistoryLogs getInstance()
    {
        if (single_instance == null)
            single_instance = new AuctionHistoryLogs();
        return single_instance;
    }

    public void recordAuction(Auction auction){
        try{
            CSVHandler.writeCSVAudit("src\\csv\\audit.csv", "Logged auction, " + LocalTime.now() + "\n", true);
        } catch (IOException e){
            System.out.println("Audit service failed to find file.");
        }
        logs.add(auction.getLog());
        timestamps.add(LocalDate.now());
        names.add(auction.getName());
    }

    public AuctionHistory at(Integer index){
        return logs.get(index);
    }

    public ArrayList<AuctionHistory> getLogs(){
        return logs;
    }

    public AuctionHistory lookUpLogByDate(String date){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.parse(date, dateTimeFormatter);

        int auction_index = timestamps.indexOf(localDate);
        if(auction_index == -1)
            return null;
        return logs.get(auction_index);

    }

    public AuctionHistory lookUpLogByName(String name){
        int auction_index = names.indexOf(name);
        if(auction_index == -1)
            return null;
        return logs.get(auction_index);
    }

    public ArrayList<String> getNames(){
        return names;
    }

    public ArrayList<LocalDate> getTimestamps() {
        return timestamps;
    }
}
