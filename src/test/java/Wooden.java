import com.fasterxml.jackson.annotation.JsonProperty;

public class Wooden {


    @JsonProperty("WooodenInner")
    private WoodenInner oak;
    private String tree;
    private String activity;

    @JsonProperty("oak")
    public WoodenInner getWoodenInner() {
        return this.oak;
    }

    public void setWoodenInner(WoodenInner oak) {
        this.oak = oak;
    }

    public String getTree() {
        return this.tree;
    }

    public void setTree(String tree) {
        this.tree = tree;
    }

    public String getActivity() {
        return this.activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

}

