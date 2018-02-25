package com.kosi0917.textandfacerecognitionapp.Model.facebook;

/**
 * Created by sivko on 25.02.2018.
 */

public class GroupEntity {
   private String name;
   private String privacy;
   private String id;
   private String icon;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
