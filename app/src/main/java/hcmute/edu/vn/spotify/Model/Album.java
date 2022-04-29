package hcmute.edu.vn.spotify.Model;
public class Album {
    private String name;
    private int  resourceId;
    private String description;

    public Album(String name, int resourceId, String description) {
        this.name = name;
        this.resourceId = resourceId;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

