package auction;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

    AuctionService service = new AuctionService();

    service.runAuction();
    service.displayLoggedAuctions();
    service.makeNewAuction();
    service.displayScheduledAuctions();



    }
}
