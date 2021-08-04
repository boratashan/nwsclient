package nwscore;

public enum NwsEnvironment {
    SANDBOX("x"),
    STAGING("s"),
    PRODUCTION("p");

    private final String env;

    NwsEnvironment(String env) {
        this.env = env;
    }

    public String getEnv() {
        return this.env;
    }
}
