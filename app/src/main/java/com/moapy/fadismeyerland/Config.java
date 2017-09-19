package com.moapy.fadismeyerland;

/**
 * Created by eyeniaras on 11/22/15.
 */
public class Config {
    public static final String ITEMS_BY_CATEGORY_URL="http://www.moapy.com/fadis/productbycat.php";
    public static final String CATEGORY_ALL_URL="http://www.moapy.com/fadis/categoryall.php";
    public static final String IMAGE_PATH_URL="@drawable/";

    //Keys for username, email and password as defined in our $_POST['key'] in login.php
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_USERNAME = "name";
    public static final String KEY_REGTOKEN = "regtoken";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_ANDVERSION = "andversion";
    public static final String KEY_FBIMAGELINK = "fbimagelink";


    //If server response is equal to this that means login is successful
    public static final String LOGIN_ERROR = "Error";

    //Keys for Sharedpreferences
    //This would be the name of our shared preferences
    public static final String SHARED_PREF_NAME = "userData";

    //This would be used to store the email of current logged in user
    public static final String EMAIL_SHARED_PREF = "email";

    //This would be used to store the username of current logged in user
    public static final String USERNAME_SHARED_PREF = "username";

    //This would be used to store the token(database id) of current logged in user
    public static final String TOKEN_SHARED_PREF = "token";

    public  static  final  String FB_IMAGE_SHARED_PREF = "fbimagelink";

    //This would be used to store the registration token (app instance id) of current logged in user
    public static final String REGISTERED_APP = "registeredapp";
    public static final String REGTOKEN_SHARED_PREF = "regtoken";
    public static final String LONGITUDE_SHARED_PREF = "longitude";
    public static final String LATITUDE_SHARED_PREF = "latitude";
    public static final String CITY_SHARED_PREF = "city";
    public static final String LOCATION_KNOWN_SHARED_PREF = "locationknown";
    public static final String PHONE_NUMBER_SHARED_PREF = "phonenumber";
    public static final String ANDROID_VERSION_SHARED_PREF = "androidversion";

    //This would be used to store the image path of current logged in user
    public static final String USERIMAGEPATH_SHARED_PREF = "userimagepath";

    //We will use this to store the boolean in sharedpreference to track user is loggedin or not
    public static final String LOGGEDIN_SHARED_PREF = "loggedin";

    //We will use this to store the boolean in sharedpreference to track user is loggedin with facebook or not
    public static final String FB_LOGGEDIN_SHARED_PREF = "fbloggedin";

    //We will use this to store the boolean in sharedpreference to track user is loggedin or not
    public static final String FILTER_SEARCH_SHARED_PREF = "filtersearch";
    public static final String FILTER_CITY_SHARED_PREF = "filtercity";
    public static final String FILTER_CATEGORY_SHARED_PREF = "filtercategory";
    public static final String FILTER_SORT_SHARED_PREF = "filtersort";

    public static final String CHAT_OPEN_SHARED_PREF = "chatopen";
    public static final String PRODUCT_ID_SHARED_PREF = "productid";
    public static final String OTHER_ID_SHARED_PREF = "otherid";

    //public static final String EDIT_PRODUCT_SHARED_PREF = "editproduct";
}