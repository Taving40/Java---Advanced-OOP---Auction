package auction;

import java.time.LocalDate;

public class Item {

    private String name, description;
    private int startingPrice, appraisedPrice;
    private LocalDate added, sold;
    private Evaluator e;
    private int highestBid;
    private String assignedAuction;

    public Item(){
        this.added = LocalDate.now();
        this.highestBid = 0;
    }

    public Item(String name, String description){
        this.name = name;
        this.description = description;
        this.added = LocalDate.now();
        this.highestBid = 0;
    }

    public Item(String name, String description, int appraisedPrice, Evaluator e){
        this.name = name;
        this.description = description;
        this.added = LocalDate.now();
        this.e = e;
        this.appraisedPrice = appraisedPrice;
        this.startingPrice = (int)(appraisedPrice - appraisedPrice /10);
        this.highestBid = 0;
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", starting_price=" + startingPrice +
                ", appraised_price=" + appraisedPrice +
                ", added=" + added +
                ", sold=" + sold +
                ", e=" + e +
                ", highest_bid=" + highestBid +
                '}';
    }

    public int getHighestBid() {
        return highestBid;
    }

    public String getEvaluator(){
        return this.e.getUsername();
    }

    public int getAppraisedPrice() {
        return this.appraisedPrice;
    }

    public String getName() {
        return this.name;
    }

    public int getStartingPrice() {
        return this.startingPrice;
    }

    public LocalDate getAdded() {
        return this.added;
    }

    public void setHighestBid(int highest_bid) {
        this.highestBid = highest_bid;
    }

    public void setSold(LocalDate sold) {
        this.sold = sold;
    }

    public void setAppraisedPrice(int appraised_price) {
        this.appraisedPrice = appraised_price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getSold() {
        return this.sold;
    }

    public void setStartingPrice(int starting_price) {
        this.startingPrice = starting_price;
    }

    public String getDescription() {
        return this.description;
    }

    public Evaluator getE() {
        return e;
    }

    public void setE(Evaluator e) {
        this.e = e;
    }

    public void setAuction(String auc){
        this.assignedAuction = auc;
    }

    public String getAuction(){
        return this.assignedAuction;
    }
}
