package auction;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Buyer extends User{

    private ArrayList<Integer> relatedAuctions = new ArrayList<Integer>();;

    public Buyer(){
        Scanner read = new Scanner(System.in);
        String temp_email;
        boolean valid_email = false;

        while(!valid_email){
            System.out.print("Please enter your email address: ");
            temp_email = read.nextLine().strip();

            if (isValid(temp_email)){
                valid_email = true;
                setEmail(temp_email);
            }
        }

        System.out.print("Please enter your username: ");
        setUsername(read.nextLine().strip());

        System.out.print("Please enter your password: ");
        setPassword(read.nextLine().strip());
    }

    public Buyer(String username, String password, String email){
        super(username, password, email);
        this.relatedAuctions = new ArrayList<Integer>();
    }

    public void addAuction(Integer id){
        if(!isAllowed()){
            return;
        }
        this.relatedAuctions.add(id);
    }

    public void updateRelated(AuctionHistoryLogs logged, Inventory inv){
        for (AuctionHistory log: logged.getLogs()){
            if(log.lookUpUser(this, inv) && !relatedAuctions.contains(logged.getLogs().indexOf(log)))
                addAuction(logged.getLogs().indexOf(log));
        }
    }

    public void participateIn(Auction auction){
        if(!isAllowed()){
            return;
        }
        if(auction.conflictsWith(this)){
            System.out.println("Warning! A conflict of interests will occur if you choose to participate in this auction, as one of the evaluators knows you personally.");
        }
        auction.addInvolved(this);
        try{
            CSVHandler.writeCSVAudit("src\\csv\\audit.csv", "Participate in auction, " + LocalTime.now() + "\n", true);
        } catch (IOException e){
            System.out.println("Audit service failed to find file.");
        }
    }

    public void makeBid(Auction auction){ //to be used without active threads
        if(!isAllowed()){
            return;
        }
        if(!auction.getInvolved().contains(this)){
            System.out.println("Only users registered for this auction (" + auction.getName() + ") may make bids!");
            return;
        }
        Scanner read = new Scanner(System.in);
        System.out.print("Enter bid: ");
        boolean ok = false;
        while(!ok){
            try{
                if(read.hasNextInt()){
                    if(read.nextInt() > auction.getCurrentItem().getHighestBid() &&
                            read.nextInt() > auction.getCurrentItem().getStartingPrice()){
                        Bid bid = new Bid(this.getUsername(), read.nextInt(), auction.getCurrentItem().getName());
                        auction.recordBid(bid);
                        ok = true;
                        System.out.println("Bid has been registered.");
                    }
                    else throw new InputMismatchException();
                }
                else throw new InputMismatchException();
            }
            catch(InputMismatchException e){
                System.out.println("Please enter a bid higher than the highest current bid.");
                read.next();
            }
        }
    }

    public void paramBid(Auction auction, Integer price){
        if(!isAllowed()) {
            return;
        }
        if(!auction.getInvolved().contains(this)){
            System.out.println("Only users previously registered for this auction (" + auction.getName() + ") may make bids!");
            return;
        }
        while (!auction.request_bid()){
            //wait for bidding to be done
            System.out.println(this.getUsername() + " is waiting for bidding to be done so he can make his own bid!\n");
        }

        if(price > auction.getCurrentItem().getHighestBid() && price > auction.getCurrentItem().getStartingPrice()){
            Bid new_bid = new Bid(this.getUsername(), price, auction.getCurrentItem().getName());
            try{
                CSVHandler.writeCSVAudit("src\\csv\\audit.csv", "Bid made, " + LocalTime.now() + "\n", true);
            } catch (IOException e){
                System.out.println("Audit service failed to find file.");
            }
            auction.recordBid(new_bid);
            try{
                CSVHandler.writeCSVAudit("src\\csv\\audit.csv", "Bid recorded, " + LocalTime.now() + "\n", true);
            } catch (IOException e){
                System.out.println("Audit service failed to find file.");
            }
        }
        else {
            System.out.println("Price for bid is too low!\n");
        }
        auction.finish_bid();
    }

    public void showRelatedAuctions(AuctionHistoryLogs logged){
        for(Integer x: relatedAuctions){
            System.out.println("Auction: " + logged.getNames().get(x));
            System.out.println("Date: " + logged.getTimestamps().get(x));
            System.out.println("---------------------------------------");
        }
        try{
            CSVHandler.writeCSVAudit("src\\csv\\audit.csv", "Displayed buyer's related auctions, " + LocalTime.now() + "\n", true);
        } catch (IOException e){
            System.out.println("Audit service failed to find file.");
        }
    }



    @Override
    public String toString() {
        return  "\nBuyer: " + this.getUsername() +
                "\nRelatedAuctions=" + relatedAuctions +
                "\n}\n";
    }
}
