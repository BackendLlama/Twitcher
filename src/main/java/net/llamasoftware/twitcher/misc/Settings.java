package net.llamasoftware.twitcher.misc;

public class Settings {

    private String token;
    private boolean modControls;

    public Settings(String token, boolean modControls) {
        this.token       = token;
        this.modControls = modControls;
    }

    public String serialize(){
        return token + "/" + modControls;
    }

    public static Settings deserialize(String data){
        if(data.isEmpty())
            return new Settings("", false);

        String[] arr = data.split("/");
        return new Settings(arr[0], Boolean.parseBoolean(arr[1]));
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isModControls() {
        return modControls;
    }

    public void setModControls(boolean modControls) {
        this.modControls = modControls;
    }

}