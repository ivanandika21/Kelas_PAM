package com.example.pertemuan8.model.google;

import com.google.gson.annotations.SerializedName;

public class item {

    @SerializedName("id")
    private Id id;

    public item(Id id) {
        this.id = id;
    }

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public class Id {

        @SerializedName("kindId")
        String kindId;

        @SerializedName("videoId")
        String videoId;

        public Id(String kindId, String videoId) {
            this.kindId = kindId;
            this.videoId = videoId;
        }

        public String getKindId() {
            return kindId;
        }

        public void setKindId(String kindId) {
            this.kindId = kindId;
        }

        public String getVideoId() {
            return videoId;
        }

        public void setVideoId(String videoId) {
            this.videoId = videoId;
        }
    }
}
