package com.example.administrator.jupin.model;

import java.util.List;

/**
 * Created by hasee on 2016/6/28.
 */
public class ActivityIndexModel {
    public String statecode;
    public List<Act> data;

    public List<Act> getData() {
        return data;
    }

    public void setData(List<Act> data) {
        this.data = data;
    }

    public String getStatecode() {
        return statecode;
    }

    public void setStatecode(String statecode) {
        this.statecode = statecode;
    }

    public class Act{
        private String id;
        private String imgUrl;
        private String sign;
        private String isClose;
        private String distance;
        private String area;
        private int join_nums;
        private int actNums;
        private String isCost;
        private String name;
        private String beginTime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getIsClose() {
            return isClose;
        }

        public void setIsClose(String isClose) {
            this.isClose = isClose;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public int getJoin_nums() {
            return join_nums;
        }

        public void setJoin_nums(int join_nums) {
            this.join_nums = join_nums;
        }

        public int getActNums() {
            return actNums;
        }

        public void setActNums(int actNums) {
            this.actNums = actNums;
        }

        public String getIsCost() {
            return isCost;
        }

        public void setIsCost(String isCost) {
            this.isCost = isCost;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(String beginTime) {
            this.beginTime = beginTime;
        }
    }
}
