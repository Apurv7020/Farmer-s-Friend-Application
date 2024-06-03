    package com.teamup.Farm360.AllReqs;


import androidx.annotation.Keep;


    @Keep
    public class CropsReq {

        int id;
         String name, marathiName, imgUrl;


        public CropsReq() {
        }

        public CropsReq(int id, String name, String marathiName, String imgUrl) {
            this.id = id;
            this.name = name;
            this.marathiName = marathiName;
            this.imgUrl = imgUrl;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMarathiName() {
            return marathiName;
        }

        public void setMarathiName(String marathiName) {
            this.marathiName = marathiName;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }
    }