import java.util.Optional;

public class User {
    private String email;
    private String password;
    private String name;

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
        public void setName(String name) {
            this.name = name;
        }
        public void setEmail(String email) {
            this.email= email;
        }

        public void setPassword(String password) {
            this.password = password;
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
