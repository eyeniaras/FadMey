package com.moapy.fadismeyerland;

class FoodItem {
    // Category members
    private String cid;
    private String cname;
    private String ctype;

    // Food item members
    private String fid;
    private String fname;
    private String fprice;
    private String fcategory;
    private String fallergen;

    // Common members
    private String url;

    FoodItem(String c_id, String c_name, String c_type, String c_image) {
        this.cid = c_id;
        this.cname = c_name;
        this.ctype = c_type;
        this.url = c_image;
    }

    FoodItem(String f_id, String f_name, String f_price, String f_category, String f_image) {
        this.fid = f_id;
        this.fname = f_name;
        this.fprice = f_price;
        this.fcategory = f_category;
        this.url = f_image;
    }

    public String getName() {
        return fname!=null ? fname : cname;
    }

    public String getPrice() {
        return fprice;
    }

    public String getId() {
        return fid;
    }

    public String getUrl() {
        return url;
    }
}
