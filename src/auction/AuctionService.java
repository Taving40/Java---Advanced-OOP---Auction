package auction;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class AuctionService {

    private ArrayList<Evaluator> evaluators = new ArrayList<>();
    private ArrayList<Buyer> buyers = new ArrayList<>();
    private Inventory inv = Inventory.getInstance();
    private Scheduler sch = new Scheduler();
    private AuctionHistoryLogs logged = AuctionHistoryLogs.getInstance();

    public AuctionService() throws IOException {
        CSVHandler handler = CSVHandler.getInstance();
        handler.writeCSVAudit("src\\csv\\audit.csv", "Nume actiune, timestamp\n", false);
        evaluators = handler.parseCSVEvaluators(handler.readCSVFile("src\\csv\\evaluators.csv"));
        //System.out.println(evaluators);
        buyers = handler.parseCSVBuyers(handler.readCSVFile("src\\csv\\buyers.csv"));
        //System.out.println(buyers);
        inv = handler.parseCSVInventory(handler.readCSVFile("src\\csv\\inventory.csv"), inv, evaluators);
        //System.out.println(inv);
        sch = handler.parseCSVSchedule(handler.readCSVFile("src\\csv\\scheduled.csv"), inv);
    }

    public void runAuction(){
        Auction backyard_sale = sch.lookUpAuctionByName("Backyard sale");
        Buyer rachel = buyers.get(0), steve = buyers.get(1);
        steve.logIn();
        rachel.logIn();

        steve.participateIn(backyard_sale);

        Thread backyard = new Thread(backyard_sale);
        backyard.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("Thread is interrupted");
        }
        steve.paramBid(backyard_sale, 100);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("Thread is interrupted");
        }
        rachel.paramBid(backyard_sale, 20);
        try {
            backyard.join();
        } catch(InterruptedException e){
            System.out.println("Thread is interrupted");
        }
        logged.recordAuction(backyard_sale);
        steve.updateRelated(logged, inv);
        rachel.updateRelated(logged, inv);
        steve.logOut();
        rachel.logOut();
    }

    public void displayScheduledAuctions(){
        for(int i=0; i<sch.getPlanner().size(); i++){
            System.out.println("Auction " + sch.getPlanner().get(i).getName() + " is planned for " + sch.getTimes().get(i) + ".\n");
        }
        try{
            CSVHandler.writeCSVAudit("src\\csv\\audit.csv", "Displayed scheduled auctions, " + LocalTime.now() + "\n", true);
        } catch (IOException e){
            System.out.println("Audit service failed to find file.");
        }
    }

    public void displayLoggedAuctions(){
        Buyer steve = buyers.get(1);
        steve.logIn();
        steve.showRelatedAuctions(logged);
        steve.logOut();
    }

    public void makeNewAuction(){
        Item new_item = new Item("Couch", "a couch");
        Evaluator anna = evaluators.get(0);
        anna.logIn();
        anna.appraiseItem(new_item);
        anna.logOut();
        inv.storeItem(new_item);
        Auction new_auction = new Auction("Book sale");
        new_auction.addItem(new_item);
        sch.planAuction(new_auction, LocalDate.of(2021, 10, 10));
    }







}
