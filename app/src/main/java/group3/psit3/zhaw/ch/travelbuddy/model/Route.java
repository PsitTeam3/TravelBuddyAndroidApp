package group3.psit3.zhaw.ch.travelbuddy.model;

public class Route {
    private String name, description, detailDescription, thumbnailUrl;

    public Route() {
    }

    public Route(String title) {
        this.name = title;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String title) {
        this.name = title;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDetailDescription() {
        return this.detailDescription;
    }

    public void setDetailDescription(String detailDescription) {
        this.detailDescription = detailDescription;
    }
}
