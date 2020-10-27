package kr.hs.emirim.cho.taketoday2;

public class User {
    private String name;
    private String tele;

    public User(){

    }

    public User(String name, String tele) {
        this.name = name;
        this.tele = tele;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTele() {
        return tele;
    }

    public void setTele(String tele) {
        this.tele = tele;
    }
}
