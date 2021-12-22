package testdata.purchasing;

public enum ComputerType {
    CHEAP_COMPUTER("Build your own cheap computer"),
    STANDARD_COMPUTER("Build your own computer");
    private final String type;

    ComputerType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
