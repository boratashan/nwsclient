package sfccorderexp.app;

import java.util.Objects;

public class ApplicationParams {

    private String tenant;
    private String environment;
    private String username;
    private String password;
    private String sourceFolder;
    private String invLoad;
    private String fullFillmentLocation;
    private String stockLocation;
    private int chunkSize;
    private String nameOfInvLoad;
    private int idle;


    public int getIdle() {
        return idle;
    }

    public ApplicationParams setIdle(int idle) {
        this.idle = idle;
        return this;
    }

    public String getNameOfInvLoad() {
        return nameOfInvLoad;
    }

    public ApplicationParams setNameOfInvLoad(String nameOfInvLoad) {
        this.nameOfInvLoad = nameOfInvLoad;
        return this;
    }

    public int getChunkSize() {
        return chunkSize;
    }

    public ApplicationParams setChunkSize(int chunkSize) {
        this.chunkSize = chunkSize;
        return this;
    }

    public String getInvLoad() {
        return invLoad;
    }

    public ApplicationParams setInvLoad(String invLoad) {
        this.invLoad = invLoad;
        return this;
    }

    public String getFullFillmentLocation() {
        return fullFillmentLocation;
    }

    public ApplicationParams setFullFillmentLocation(String fullFillmentLocation) {
        this.fullFillmentLocation = fullFillmentLocation;
        return this;
    }

    public String getStockLocation() {
        return stockLocation;
    }

    public ApplicationParams setStockLocation(String stockLocation) {
        this.stockLocation = stockLocation;
        return this;
    }





    public String getTenant() {
        return tenant;
    }

    public ApplicationParams setTenant(String tenant) {
        this.tenant = tenant;
        return this;
    }

    public String getEnvironment() {
        return environment;
    }

    public ApplicationParams setEnvironment(String environment) {
        this.environment = environment;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public ApplicationParams setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public ApplicationParams setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getSourceFolder() {
        return sourceFolder;
    }

    public ApplicationParams setSourceFolder(String sourceFolder) {
        this.sourceFolder = sourceFolder;
        return this;
    }


    public void checkIfValid() {
        Objects.requireNonNull(tenant);
        Objects.requireNonNull(environment);
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);
        Objects.requireNonNull(invLoad);
        Objects.requireNonNull(fullFillmentLocation);
        Objects.requireNonNull(stockLocation);
        Objects.requireNonNull(chunkSize);
        Objects.requireNonNull(nameOfInvLoad);
    }


    @Override
    public String toString() {
        return "ApplicationParams{" +
                "tenant='" + tenant + '\'' +
                ", environment='" + environment + '\'' +
                ", username='" + username + '\'' +
                ", password='" + "**************************" + '\'' +
                ", sourceFolder='" + sourceFolder + '\'' +
                ", invLoad='" + invLoad + '\'' +
                ", fullFillmentLocation='" + fullFillmentLocation + '\'' +
                ", stockLocation='" + stockLocation + '\'' +
                ", chunkSize=" + chunkSize +
                ", nameOfInvLoad='" + nameOfInvLoad + '\'' +
                ", idle=" + idle +
                '}';
    }
}
