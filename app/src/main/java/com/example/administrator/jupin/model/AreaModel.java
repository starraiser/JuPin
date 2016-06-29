package com.example.administrator.jupin.model;

import java.util.List;

/**
 * Created by hasee on 2016/6/29.
 */
public class AreaModel {
    private int statecode;
    private List<Data> data;

    public int getStatecode() {
        return statecode;
    }

    public void setStatecode(int statecode) {
        this.statecode = statecode;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public class Data{
        private String NAME;
        private String LONGITUDE;
        private String LATITUDE;
        private String AREAID;
        private List<City> citys;

        public List<City> getCitys() {
            return citys;
        }

        public void setCitys(List<City> citys) {
            this.citys = citys;
        }

        public String getNAME() {
            return NAME;
        }

        public void setNAME(String NAME) {
            this.NAME = NAME;
        }

        public String getLONGITUDE() {
            return LONGITUDE;
        }

        public void setLONGITUDE(String LONGITUDE) {
            this.LONGITUDE = LONGITUDE;
        }

        public String getLATITUDE() {
            return LATITUDE;
        }

        public void setLATITUDE(String LATITUDE) {
            this.LATITUDE = LATITUDE;
        }

        public String getAREAID() {
            return AREAID;
        }

        public void setAREAID(String AREAID) {
            this.AREAID = AREAID;
        }

        public class City{
            private String NAME;
            private String LONGITUDE;
            private String AREAID;
            private String LATITUDE;

            public String getNAME() {
                return NAME;
            }

            public void setNAME(String NAME) {
                this.NAME = NAME;
            }

            public String getLONGITUDE() {
                return LONGITUDE;
            }

            public void setLONGITUDE(String LONGITUDE) {
                this.LONGITUDE = LONGITUDE;
            }

            public String getAREAID() {
                return AREAID;
            }

            public void setAREAID(String AREAID) {
                this.AREAID = AREAID;
            }

            public String getLATITUDE() {
                return LATITUDE;
            }

            public void setLATITUDE(String LATITUDE) {
                this.LATITUDE = LATITUDE;
            }
        }
    }
}
