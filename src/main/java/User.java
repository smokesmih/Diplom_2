import java.util.Optional;

public class User {
    private  final  String email;
    private  final String password;
    private  final  String name;

    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }
    public User( String password, String name) {
        this.email = null;
        this.password = password;
        this.name = name;
    }
    public User(String email, Optional <String> password, String name) {
        this.email = email;
        this.password = null;
        this.name = name;
    }
    public User(String email, String password,Optional <String> name) {
        this.email = email;
        this.password = password;
        this.name = null;
    }


}
