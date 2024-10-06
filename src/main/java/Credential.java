import io.qameta.allure.Step;

public class Credential {
    private final String email;
    private final String password;
    private final String name;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public Credential(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    @Step("Получение данных для входа c неверным email")
    public static Credential getDataForLoginWithWrongEmail(User user) {
        return new Credential("12" + user.getEmail(), user.getPassword(),  null);
    }

    @Step("Получение данных для входа c неверным password")
    public static Credential getDataForLoginWithWrongPassword(User user) {
        return new Credential(user.getEmail(), user.getPassword() + "sd", null);
    }
    @Step("Получение данных для изменения email")
    public static Credential getDataForPathEmail(User user) {
        return new Credential("Kek" + user.getEmail(),null,null);
    }
    @Step("Получение данных для изменения password")
    public static Credential getDataForPathPassword(User user) {
        return new Credential(null,user.getPassword() +"94",null);
    }
    @Step("Получение данных для изменения name")
    public static Credential getDataForPathName(User user) {
        return new Credential(null,null, user.getName()+"ич");
    }


}
