package auction;

import java.util.Set;

public class Auction {
    //TODO:
    // Start method (iterate over items updating current_item, wait for Buyers to make bids and print out each new bid while updating item's highest_bid, close after internal timer and notify users)

    private String name;
    private Set<Buyer> buyers;
    private Set<Item> items;
    private AuctionHistory log;
    private Item current_item;

    public Auction(String name, Set<Item> items){
        this.name = name;
        this.items = items;
    }

    public Auction(String name, Set<Buyer> buyers, Set<Item> items){
        this.name = name;
        this.items = items;
        this.buyers = buyers;
    }

    public Auction(String name, Set<Buyer> buyers, Set<Item> items, AuctionHistory log){
        this.name = name;
        this.items = items;
        this.buyers = buyers;
        this.log = log;
    }

    public void start(){
        //TODO
        return;
    }

    private Boolean canBeAuctioned(Item a){
        if(a.getEvaluator() != null)
            return true;
        return false;
    }

    public void addBuyer(Buyer b){
        this.buyers.add(b);
    }

    public void addItem(Item i){
        if (canBeAuctioned(i)){
            this.items.add(i);
        }
        else {
            System.out.println("Please provide an item that has been appraised and is ready to be auctioned.");
        }
    }

    public void recordBid(Bid b){
        log.recordBid(b);
    }

    public Set<Buyer> getBuyers() {
        return this.buyers;
    }

    public Set<Item> getItems() {
        return this.items;
    }

    public void setBuyers(Set<Buyer> buyers) {
        this.buyers = buyers;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public AuctionHistory getLog() {
        return log;
    }

    public Item getCurrentItem(){
        return current_item;
    }

    @Override
    public String toString() {
        return "Auction{" +
                "name='" + name + '\'' +
                ", buyers=" + buyers +
                ", items=" + items +
                ", log=" + log +
                ", current_item=" + current_item +
                '}';
    }
}
