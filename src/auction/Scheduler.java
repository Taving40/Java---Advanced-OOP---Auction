package auction;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Scheduler {

    private ArrayList<Auction> planner;
    private ArrayList<LocalDate> times;

    public Scheduler(){}

    public Scheduler(ArrayList<Auction> planner, ArrayList<LocalDate> times){
        this.planner = planner;
        this.times = times;
    }

    public void planAuction(Auction auction, LocalDate timeToBeScheduled){
        planner.add(auction);
        times.add(timeToBeScheduled);
    }

    public Auction lookUpAuctionByDate(String date){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.parse(date, dateTimeFormatter);

        int auction_index = times.indexOf(localDate);
        if(auction_index == -1){
            return null;
        }

        return planner.get(auction_index);
    }

    public Auction lookUpAuctionByName(String name){
        for(Auction a: planner){
            if (a.getName().equals(name)){
                return a;
            }
        }
        return null;
    }

    public void cancelAuction(String date){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.parse(date, dateTimeFormatter);

        int auction_index = times.indexOf(localDate);
        if(auction_index == -1){
            System.out.println("No auction found with given date.");
            return;
        }

        Auction temp = planner.get(auction_index);
        planner.remove(temp);
        times.remove(localDate);
    }

    public void setPlanner(ArrayList<Auction> planner) {
        this.planner = planner;
    }

    public ArrayList<Auction> getPlanner() {
        return planner;
    }

    public ArrayList<LocalDate> getTimes() {
        return times;
    }

    public void setTimes(ArrayList<LocalDate> times) {
        this.times = times;
    }
}
