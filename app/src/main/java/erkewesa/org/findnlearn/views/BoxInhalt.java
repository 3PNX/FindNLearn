package erkewesa.org.findnlearn.views;

public class BoxInhalt {
    private String beschreibung;
    private String datum;
    private String zeitVon;

    public String getZeitVon() {
        return zeitVon;
    }

    public void setZeitVon(String zeitVon) {
        this.zeitVon = zeitVon;
    }

    public String getZeitBis() {
        return zeitBis;
    }

    public void setZeitBis(String zeitBis) {
        this.zeitBis = zeitBis;
    }

    private String zeitBis;
    public BoxInhalt(){}
    public BoxInhalt(String beschreibung, String datum,String zeitVon,String zeitBis){
        this.beschreibung = beschreibung;
        this.datum=datum;
        this.zeitVon=zeitVon;
        this.zeitBis=zeitBis;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }
}
