package auction;

import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class User {

    private String username;
    private String password;
    private String email;
    private boolean isLoggedIn, isLocked;

    public User(){}

    public User(String username, String password, String email){
        this.username = username;
        this.password = password;
        this.email = email;
        this.isLoggedIn = false;
        this.isLocked = false;
    }

    protected static boolean isValid(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    public void logIn(){
        Scanner read = new Scanner(System.in);
        int i;
        for(i = 1; i <= 3; i++){
            System.out.print("Enter your password: ");
            String temp = read.nextLine();
            if (temp.equals(this.password)){
                this.isLoggedIn = true;
                break;
            }
            else{
                System.out.println("Login failed, try again.");
            }
        }

        if (i==4){
            System.out.println("Failed 3 attempts to login in a row, account is now locked.");
            this.isLocked = true;
        }

    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public String getUsername(){
        return this.username;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public boolean isAllowed(){
        if(this.isLocked() || !this.isLoggedIn()){
            System.out.println("Account is either locked or you haven't logged in.");
            return false;
        }
        return true;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getPassword(){
        return this.password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getEmail(){
        return this.email;
    }

    public void setEmail(String email){
        this.email = email;
    }

}
