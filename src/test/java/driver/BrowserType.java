package driver;

public enum BrowserType {
    chrome("Chrome"),
    firefox("Firefox"),
    safari("Safari"),
    edge("Edge");
    private final String name;

    BrowserType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
