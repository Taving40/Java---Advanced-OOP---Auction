package auction;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Buyer extends User{

    private ArrayList<Integer> RelatedAuctions;

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

        this.RelatedAuctions = new ArrayList<Integer>();

    }

    public Buyer(String username, String password, String email){
        super(username, password, email);
        this.RelatedAuctions = new ArrayList<Integer>();
    }

    public void addAuction(Integer id){
        if(!isAllowed()){
            return;
        }
        this.RelatedAuctions.add(id);
    }

    public void participateIn(Auction auction){
        if(!isAllowed()){
            return;
        }
        auction.addBuyer(this);
    }

    public void makeBid(Auction auction){
        if(!isAllowed()){
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

    public void showRelatedAuctions(AuctionHistoryLogs logs){
        for(Integer x: RelatedAuctions){
            System.out.println(logs.at(x).toString());
        }
    }



    @Override
    public String toString() {
        return super.toString() +
                "Buyer{" +
                "RelatedAuctions=" + RelatedAuctions +
                '}';
    }
}
