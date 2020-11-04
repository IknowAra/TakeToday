package kr.hs.emirim.cho.taketoday2;

public class User {
    private String name;
    private String tele;
    private boolean isEmalVerified;

    public User(){

    }

    public User(String name, String tele, boolean isEmalVerified) {
        this.name = name;
        this.tele = tele;
        this.isEmalVerified=isEmalVerified;
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

    public boolean isEmalVerified() {
        return isEmalVerified;
    }

    public void setEmalVerified(boolean emalVerified) {
        isEmalVerified = emalVerified;
    }
}
