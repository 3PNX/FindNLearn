package erkewesa.org.findnlearn;

public class User {

    private String name, studiengang;


    public User(String name, String studiengang){
        this.studiengang =studiengang;
        this.name = name;
    }

    public String getName() {return name;}
    public String getStudiengang() {return studiengang;}
}