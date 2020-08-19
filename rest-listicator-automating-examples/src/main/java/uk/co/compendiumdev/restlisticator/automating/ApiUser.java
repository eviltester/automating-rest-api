package uk.co.compendiumdev.restlisticator.automating;


public class ApiUser {
    private final String username;
    private final String password;


    public ApiUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static ApiUser getDefaultAdminUser(){
        return new ApiUser("admin", "password");
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
