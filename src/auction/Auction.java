package auction;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.time.*;

public class Auction implements Runnable{

    private String name;
    private Set<User> involved = new HashSet<>();
    private Set<String> conflicts = new HashSet<>();
    private Set<Item> items = new HashSet<>();
    private AuctionHistory log = new AuctionHistory();
    private Item currentItem;
    private Boolean bidFlag = false;

    public Auction(){}

    public Auction(String name){
        this.name = name;
    }

    public Auction(String name, Set<Item> items){
        this.name = name;
        this.items = items;
        for(Item i: items){
            for(String conflictName: i.getE().getConflicts()){
                conflicts.add(conflictName);
            }
        }
    }

    public Auction(String name, Set<User> buyers, Set<Item> items){
        this.name = name;
        this.items = items;
        this.involved = involved;
        for(Item i: items){
            for(String conflictName: i.getE().getConflicts()){
                conflicts.add(conflictName);
            }
        }
    }

    public Auction(String name, Set<User> involved, Set<Item> items, AuctionHistory log){
        this.name = name;
        this.items = items;
        this.involved = involved;
        this.log = log;
    }

    public Boolean request_bid(){
        if(bidFlag)
            return false;
        else{
            bidFlag = true;
            return true;
        }
    }

    public void finish_bid(){
        if(bidFlag)
            bidFlag = false;
        else
            System.out.println("Cannot finish bid that was not started!");
    }

    public Boolean get_flag(){
        return bidFlag;
    }

    @Override
    public void run(){
        if(this.isConflict()){
            System.out.println("There is a conflict of interests! Auction is now cancelled.");
            return;
        }
        try{
            CSVHandler.writeCSVAudit("src\\csv\\audit.csv", "Checked for conflicts, " + LocalTime.now() + "\n", true);
        } catch (IOException e){
            System.out.println("Audit service failed to find file.");
        }
        System.out.println("Auction " + this.name + " is now starting!\n");
        try{
            CSVHandler.writeCSVAudit("src\\csv\\audit.csv", "Started auction, " + LocalTime.now() + "\n", true);
        } catch (IOException e){
            System.out.println("Audit service failed to find file.");
        }
        for(Item x: items){
            currentItem = x;
            System.out.println("Start bidding on " + x.getName() + ". Starting bid is " + x.getStartingPrice());
            Instant start = Instant.now();
            long print_flag = 2;
            while(true){
                Instant maybe_finish = Instant.now();
                long elapsed_time = Duration.between(start, maybe_finish).toSeconds();
                if(elapsed_time == print_flag){
                    if(log.isEmpty() || log.getLastBid().getItem() != x.getName())
                        System.out.println("No current bids!\n");
                    else
                        System.out.println("Highest bid is: " + log.getLastBid().getAmount() + "\n");
                    print_flag += 2;
                }
                if (elapsed_time > 10){
                    break;
                }
            }
            if(log.isEmpty() || log.getLastBid().getItem() != x.getName())
                System.out.println("Item " + x.getName() + " wasn't bid on.\n");
            else
                System.out.println("Bidding on " + x.getName() + " has stopped. Winner is " + log.getLastBid() + "\n");
        }

        System.out.println("\nAuction " + this.name + " is now finished.\n");
    }

    private Boolean canBeAuctioned(Item a){
        try{
            CSVHandler.writeCSVAudit("src\\csv\\audit.csv", "Checked if item is auctionable, " + LocalTime.now() + "\n", true);
        } catch (IOException e){
            System.out.println("Audit service failed to find file.");
        }
        if(a.getEvaluator() != null)
            return true;
        return false;
    }

    public void addInvolved(User i){
        this.involved.add(i);
    }

    public void addItem(Item i){
        if (canBeAuctioned(i)){
            this.items.add(i);
            i.setAuction(this.getName());
            for(String conflictName: i.getE().getConflicts()){
                conflicts.add(conflictName);
            }
            try{
                CSVHandler.writeCSVAudit("src\\csv\\audit.csv", "Added item to auction, " + LocalTime.now() + "\n", true);
            } catch (IOException e){
                System.out.println("Audit service failed to find file.");
            }
        }
        else {
            System.out.println("Please provide an item that has been appraised and is ready to be auctioned.");
        }
    }

    public synchronized void recordBid(Bid b){
        log.recordBid(b);
    }

    public Set<User> getInvolved() {
        return this.involved;
    }

    public Boolean conflictsWith(Buyer b){
        if(conflicts.contains(b.getUsername()))
            return true;
        return false;
    }

    public Boolean isConflict(){
        for(User u: involved){
            if(conflicts.contains(u.getUsername())){
                return true;
            }
        }
        return false;
    }

    public Set<Item> getItems() {
        return this.items;
    }

    public void setInvolved(Set<User> involved) {
        this.involved = involved;
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

    public synchronized AuctionHistory getLog() {
        return log;
    }

    public synchronized Item getCurrentItem() {
        return currentItem;
    }

    public synchronized void setCurrentItem(Item currentItem) {
        this.currentItem = currentItem;
    }


    @Override
    public String toString() {
        return "Auction{" +
                "name='" + name + '\'' +
                ", involved=" + involved +
                ", items=" + items +
                ", log=" + log +
                '}';
    }
}
