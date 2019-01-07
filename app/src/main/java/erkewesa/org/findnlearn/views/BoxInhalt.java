package erkewesa.org.findnlearn.views;

public class BoxInhalt {
    private String title,datum;
    public BoxInhalt(){}
    public BoxInhalt(String title,String datum){
        this.title=title;
        this.datum=datum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }
}
