package sfccorderexp.app.uploader;

public enum ImportTaskStatus {
    PENDING("pending"),
    INPROGRESS("running"),
    COMPLETED("finished"),
    FAILED("failed"),
    OTHER("");

    private final String status;

    ImportTaskStatus(String status) {
        this.status = status;
    }

    public static ImportTaskStatus fromString(String text) {
        for (ImportTaskStatus status : ImportTaskStatus.values()) {
            if (text.equalsIgnoreCase(status.status)) {
                return status;
            }
        }
        return ImportTaskStatus.OTHER;
    }
}
