package com.example.intracer.Fr_groups;

public class GroupList {
    private String groupId;
    private String groupname;
    private String description;
    private String User;

    public GroupList(String groupId, String groupname, String description, String User){
        this.groupId = groupId;
        this.groupname = groupname;
        this.description = description;
        this.User = User;


    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }
}
