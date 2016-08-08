package by.dmitry.exchange;

/**
 * Created by Dmitry on 08.08.16.
 */
public class Bank {
    String name,adress;
    Double kurs_0,kurs1;

    public Bank(String name, String adress, Double kurs_0, Double kurs1) {
        this.name = name;
        this.adress = adress;
        this.kurs_0 = kurs_0;
        this.kurs1 = kurs1;
    }

    public Bank (String name){
        this.name = name;
        this.adress = "";
        this.kurs_0 = 0.0;
        this.kurs1 = 0.0;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public void setKurs0(Double kurs_0) {
        this.kurs_0 = kurs_0;
    }

    public void setKurs1(Double kurs1) {
        this.kurs1 = kurs1;
    }

    public String getName() {
        return name;
    }

    public String getAdress() {
        return adress;
    }

    public Double getKurs_0() {
        return kurs_0;
    }

    public Double getKurs1() {
        return kurs1;
    }
}
