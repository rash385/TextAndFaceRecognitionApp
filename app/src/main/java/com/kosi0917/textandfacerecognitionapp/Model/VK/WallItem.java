
package com.kosi0917.textandfacerecognitionapp.Model.VK;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class WallItem extends RealmObject {

    public String senderName;
    public String senderPhoto;

    public String attachmentsString;

    @PrimaryKey
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("from_id")
    @Expose
    private Integer fromId;
    @SerializedName("owner_id")
    @Expose
    private Integer ownerId;
    @SerializedName("date")
    @Expose
    private Integer date;
    @SerializedName("marked_as_ads")
    @Expose
    private Integer markedAsAds;
    @SerializedName("post_type")
    @Expose
    private String postType;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("can_pin")
    @Expose
    private Integer canPin;
    @SerializedName("attachments")
    @Expose
    private RealmList<ApiAttachment> apiAttachments = new RealmList<>();
    @SerializedName("post_source")
    @Expose
    private PostSource postSource;
    @SerializedName("comments")
    @Expose
    private Comments comments;
    @SerializedName("likes")
    @Expose
    private Likes likes;
    @SerializedName("reposts")
    @Expose
    private Reposts reposts;
    @SerializedName("views")
    @Expose
    private Views views;

    @SerializedName("copy_history")
    @Expose
    private RealmList<WallItem> copyHistory = new RealmList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFromId() {
        return fromId;
    }

    public void setFromId(Integer fromId) {
        this.fromId = fromId;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public Integer getMarkedAsAds() {
        return markedAsAds;
    }

    public void setMarkedAsAds(Integer markedAsAds) {
        this.markedAsAds = markedAsAds;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getCanPin() {
        return canPin;
    }

    public void setCanPin(Integer canPin) {
        this.canPin = canPin;
    }

    public RealmList<ApiAttachment> getApiAttachments() {
        return apiAttachments;
    }

    public void setApiAttachments(RealmList<ApiAttachment> apiAttachments) {
        this.apiAttachments = apiAttachments;
    }

    public PostSource getPostSource() {
        return postSource;
    }

    public void setPostSource(PostSource postSource) {
        this.postSource = postSource;
    }

    public Comments getComments() {
        return comments;
    }

    public void setComments(Comments comments) {
        this.comments = comments;
    }

    public Likes getLikes() {
        return likes;
    }

    public void setLikes(Likes likes) {
        this.likes = likes;
    }

    public Reposts getReposts() {
        return reposts;
    }

    public void setReposts(Reposts reposts) {
        this.reposts = reposts;
    }

    public Views getViews() {
        return views;
    }

    public void setViews(Views views) {
        this.views = views;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderPhoto() {
        return senderPhoto;
    }

    public void setSenderPhoto(String senderPhoto) {
        this.senderPhoto = senderPhoto;
    }

    public String getAttachmentsString() {
        return attachmentsString;
    }

    public void setAttachmentsString(String attachmentsString) {
        this.attachmentsString = attachmentsString;
    }

    public boolean haveSharedRepost() {
        return copyHistory.size() > 0;
    }

    public WallItem getSharedRepost() {
        if (haveSharedRepost()) {
            return copyHistory.get(0);
        }
        return null;
    }
}
