package auction;

import java.time.LocalTime;

public class Bid {

    private String username;
    private int amount;
    private LocalTime timestamp;
    private String item; //item's name

    public Bid(String username, int amount, String item){
        this.username = username;
        this.amount = amount;
        this.item = item;
        timestamp = LocalTime.now();
    }

    public Bid(String username, int amount, String item, LocalTime timestamp){
        this.username = username;
        this.amount = amount;
        this.item = item;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Bid{" +
                "username='" + username + '\'' +
                ", amount=" + amount +
                ", timestamp=" + timestamp +
                ", item=" + item +
                '}';
    }

    public int getAmount() {
        return amount;
    }

    public String getItem() {
        return item;
    }

    public String getUsername() {
        return username;
    }

    public LocalTime getTimestamp() {
        return timestamp;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public void setTimestamp(LocalTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setUsername(String username) {
        this.username = username;
    }


}
