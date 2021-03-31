package auction;

import java.util.ArrayList;

public class AuctionHistory {

    private ArrayList<Bid> history;
    private ArrayList<String> items;

    public AuctionHistory(){}

    public AuctionHistory(ArrayList<Bid> history){
        this.history = history;
        for(Bid bid: history){
            items.add(bid.getItem());
        }
    }

    public AuctionHistory(ArrayList<Bid> history, ArrayList<String> items){
        this.history = history;
        this.items = items;
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
            }
        }

        return rez;
    }

    public boolean lookUpUser(User u, Inventory i){
        for(Bid bid: history){
            if(bid.getUsername().equals(u.getUsername()) || i.lookUpItem(bid.getItem()).getEvaluator().equals(u.getUsername()))
                return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "AuctionHistory{" +
                "history=" + history +
                ", items=" + items +
                '}';
    }
}
