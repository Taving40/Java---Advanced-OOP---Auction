package auction;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;

public class AuctionHistory {

    private ArrayList<Bid> history = new ArrayList<>();

    public AuctionHistory(){}

    public AuctionHistory(ArrayList<Bid> history){
        this.history = history;
    }

    public void setHistory(ArrayList<Bid> history) {
        this.history = history;
    }

    public ArrayList<Bid> getHistory() {
        return this.history;
    }

    public void recordBid(Bid b){
        this.history.add(b);
    }

    public ArrayList<Bid> lookUpItem(Item i){
        ArrayList<Bid> rez = new ArrayList<Bid>();
        for(Bid bid: history){
            if(bid.getItem().equals(i.getName())){
                rez.add(bid);
                try{
                    CSVHandler.writeCSVAudit("src\\csv\\audit.csv", "Recorded bid, " + LocalTime.now() + "\n", true);
                } catch (IOException e){
                    System.out.println("Audit service failed to find file.");
                }
            }
        }
        return rez;
    }
    public Boolean isEmpty(){
        if(history.size() == 0)
            return true;
        return false;
    }

    public boolean lookUpUser(User u, Inventory i){
        for(Bid bid: history){
            if(bid.getUsername().equals(u.getUsername()) || i.lookUpItem(bid.getItem()).getEvaluator().equals(u.getUsername()))
                return true;
        }
        return false;
    }

    public Bid getLastBid(){
        if(history.size() == 0){
            return null;
        }
        return history.get(history.size() - 1);
    }

    @Override
    public String toString() {
        return "AuctionHistory{" +
                "history=" + history +
                '}';
    }
}
