package erkewesa.org.findnlearn;

import java.util.Date;

public class Meetings {

    private String Studiengang,Modul,studg_modul,Datum;;
    private Long Semester;
    public Meetings(){

    }

    public Meetings(String studiengang, Long semester,String modul, String datum){
        this.Studiengang =studiengang;
        this.Semester =semester;
        this.Modul =modul;
        this.Datum =datum;
    }

    public void setModul(String modul) {
        this.Modul = modul;
    }

    public String getModul() {
        return Modul;
    }

    public void setStudiengang(String studiengang) {
        this.Studiengang = studiengang;
    }

    public String getStudiengang() {
        return Studiengang;
    }

    public void setSemester(Long semester) {
        this.Semester = semester;
    }

    public Long getSemester() {
        return Semester;
    }

    public void setDatum(String datum) {
        this.Datum = datum;
    }

    public String getDatum() {
        return Datum;
    }

    public void setStudg_modul(String studg_modul) {
        this.studg_modul = studg_modul;
    }

    public String getStudg_modul() {
        return studg_modul;
    }

    public String toString(){

        return " Studiengang: "+ Studiengang +"\n Semester: "+ Semester +"\n Modul: "+ Modul +"\n Datum: "+ Datum;
    }
}
