package com.android.whatsappclone;

public class Users {
    String name;
    String userId;
    String imageUrl;

    public Users() {
    }

    public Users(String name, String userId,String imageUrl) {
        this.name = name;
        this.userId = userId;
        this.imageUrl=imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

}
