package erkewesa.org.findnlearn;


public class Meetings {

    private String Studiengang,Modul,studg_modul;
    private Long Semester,Teilnehmer;


    public Meetings(){

    }

    public Meetings(String studiengang, Long semester,String modul,Long teilnehmer){
        this.Studiengang =studiengang;
        this.Semester =semester;
        this.Modul =modul;
        this.Teilnehmer=teilnehmer;
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



    public void setStudg_modul(String studg_modul) {
        this.studg_modul = studg_modul;
    }

    public String getStudg_modul() {
        return studg_modul;
    }

    public void setTeilnehmer(Long teilnehmer) {
        Teilnehmer = teilnehmer;
    }

    public Long getTeilnehmer() {
        return Teilnehmer;
    }



    public String toString(){

        return " Studiengang: "+ Studiengang +"\n Semester: "+ Semester +"\n Modul: "+ Modul;
    }
}
