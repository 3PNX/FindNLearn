package erkewesa.org.findnlearn;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Termine implements Comparable<Termine>{
    private String Datum, Beschreibung, Zeit_von, Zeit_bis, Meet;
    private Date date;

    public Termine(){
    }

    public Termine(String datum, String beschreibung, String zeit_von, String zeit_bis,String meet) {
        this.Datum = datum;
        this.Beschreibung = beschreibung;
        this.Zeit_von = zeit_von;
        this.Zeit_bis = zeit_bis;
        this.Meet =meet;
        setDate();
    }

    public String getMeet() {
        return Meet;
    }

    public void setMeet(String meet) {
        this.Meet = meet;
    }

    public String getDatum() {
        return Datum;
    }

    public void setDatum(String datum) {
        this.Datum = datum;
        setDate();
    }

    public String getBeschreibung() {
        return Beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.Beschreibung = beschreibung;
    }

    public String getZeit_von() {
        return Zeit_von;
    }

    public void setZeit_von(String zeit_von) {
        this.Zeit_von = zeit_von;
    }

    public String getZeit_bis() {
        return Zeit_bis;
    }

    public void setZeit_bis(String zeit_bis) {
        this.Zeit_bis = zeit_bis;
    }

    public Date getDate() {
        return date;
    }

    public void setDate() {
        final SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
       try{
           date=sdf.parse(Datum);
       }catch(ParseException e){

       }

    }

    @Override
    public int compareTo(Termine o) {
        if(getDate().before(o.getDate())) {
            return -1;
        }
        if(getDate().after(o.getDate())){
            return 1;
        } else {
            return 0;
        }
    }
}
