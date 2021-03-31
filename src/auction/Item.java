package auction;

import java.time.LocalDate;

public class Item {

    private String name, description;
    private int starting_price, appraised_price;
    private LocalDate added, sold;
    private Evaluator e;
    private int highest_bid;

    public Item(){
        this.added = LocalDate.now();
        this.highest_bid = 0;
    }

    public Item(String name, String description, int appraised_price, Evaluator e){
        this.name = name;
        this.description = description;
        this.added = LocalDate.now();
        this.e = e;
        this.appraised_price = appraised_price;
        this.starting_price = (int)(appraised_price-appraised_price/10);
        this.highest_bid = 0;
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", starting_price=" + starting_price +
                ", appraised_price=" + appraised_price +
                ", added=" + added +
                ", sold=" + sold +
                ", e=" + e +
                ", highest_bid=" + highest_bid +
                '}';
    }

    public int getHighestBid() {
        return highest_bid;
    }

    public String getEvaluator(){
        return this.e.getUsername();
    }

    public int getAppraisedPrice() {
        return this.appraised_price;
    }

    public String getName() {
        return this.name;
    }

    public int getStartingPrice() {
        return this.starting_price;
    }

    public LocalDate getAdded() {
        return this.added;
    }

    public void setHighestBid(int highest_bid) {
        this.highest_bid = highest_bid;
    }

    public void setSold(LocalDate sold) {
        this.sold = sold;
    }

    public void setAppraisedPrice(int appraised_price) {
        this.appraised_price = appraised_price;
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
        this.starting_price = starting_price;
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
}
