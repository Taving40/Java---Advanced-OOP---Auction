package auction;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class AuctionHistoryLogs {
    //TODO:
    // Log all AuctionHistory objects in here with some sort of ids, and datestamps
    // Look-up method (using date or id)


    private ArrayList<AuctionHistory> logs;
    private ArrayList<LocalDate> timestamps; //date added
    private static AuctionHistoryLogs single_instance = null;

    public AuctionHistoryLogs(){}

    public static AuctionHistoryLogs getInstance()
    {
        if (single_instance == null)
            single_instance = new AuctionHistoryLogs();

        return single_instance;
    }

    public void recordAuction(Auction auction){
        logs.add(auction.getLog());
        timestamps.add(LocalDate.now());
    }

    public boolean isUserInvolved(User u, Integer index, Inventory inv){
        return logs.get(index).lookUpUser(u, inv);
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

}
