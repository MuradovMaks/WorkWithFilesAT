package JsonModels;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class WoodenInner {
    @JsonSerialize()
    private Integer BarkDensity;

    private Integer Size;
    private String Color;

    private String View;

    private String Leafes;

    private String grow;

    public Integer getBarkDensity() {
        return this.BarkDensity;
    }

    public Integer getSize() {
        return this.Size;
    }

    public String getColor() {
        return this.Color;
    }

    public String getView() {
        return this.View;
    }

    public String getLeafes() {
        return this.Leafes;
    }

    public String getGrow() {
        return this.grow;
    }

    public void setBarkDensity(Integer tried) {
        this.BarkDensity = tried;
    }

    public void setSize(Integer Size) {
        this.Size = Size;
    }

    public void setColor(String Color) {
        this.Color = Color;
    }

    public void setView(String View) {
        this.View = View;
    }

    public void setLeafes(String Leafes) {
        this.Leafes = Leafes;
    }

    public void setGrow(String grow) {
        this.grow = grow;
    }

}
