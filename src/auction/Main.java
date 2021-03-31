package auction;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        //TODO:

        Item a = new Item();

        Evaluator e = new Evaluator("asd", "asd", "asd@asd.com");

        e.appraiseItem(a);

        System.out.println(a.getStartingPrice());


    }
}
