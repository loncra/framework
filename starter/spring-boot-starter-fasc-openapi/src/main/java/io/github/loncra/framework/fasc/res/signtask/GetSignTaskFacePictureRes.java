package io.github.loncra.framework.fasc.res.signtask;

public class GetSignTaskFacePictureRes {

    private String picture;
    private String faceVideo;
    private String similarity;
    private String liveRate;

    public String getFaceVideo() {
        return faceVideo;
    }

    public void setFaceVideo(String faceVideo) {
        this.faceVideo = faceVideo;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getSimilarity() {
        return similarity;
    }

    public void setSimilarity(String similarity) {
        this.similarity = similarity;
    }

    public String getLiveRate() {
        return liveRate;
    }

    public void setLiveRate(String liveRate) {
        this.liveRate = liveRate;
    }
}