package auction;

import java.io.IOException;
import java.time.LocalTime;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Evaluator extends User{

    private List<String> conflicts; //list of usernames

    public Evaluator(String username, String password, String email){
        super(username, password, email);
    }

    public void addConflict(Buyer b){
        conflicts.add(b.getUsername());
    }

    public void setConflicts(List<String> conflicts) {
        this.conflicts = conflicts;
    }

    public List<String> getConflicts(){
        return this.conflicts;
    }

    public Boolean lookUpConflict(String target){
        return conflicts.contains(target);
    }

    public void appraiseItem(Item i){

        Scanner read = new Scanner(System.in);
        System.out.println("Current item properties: " + i.toString());

        boolean ok = false;
        int temp = -1;
        while(!ok){
            try{
                System.out.println("Please insert the price you appraise this item as: ");
                if(read.hasNextInt()){
                    temp = read.nextInt();
                }
                else{
                    throw new InputMismatchException();
                }
                if (temp <= 0){
                    throw new InputMismatchException();
                }
                i.setAppraisedPrice(temp);
                ok = true;
            }
            catch(InputMismatchException e){
                System.out.println("Please enter a positive integer.");
                read.next();
            }
        }

        i.setStartingPrice(i.getAppraisedPrice()-(i.getAppraisedPrice()/10));
        i.setE(this);
        try{
            CSVHandler.writeCSVAudit("src\\csv\\audit.csv", "Appraised item, " + LocalTime.now() + "\n", true);
        } catch (IOException e){
            System.out.println("Audit service failed to find file.");
        }
    }

    @Override
    public String toString() {
        return "Evaluator: " + this.getUsername() +
                "\nconflicts =" + conflicts +
                '}';
    }
}
