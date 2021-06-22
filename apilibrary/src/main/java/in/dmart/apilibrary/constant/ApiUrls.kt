package `in`.dmart.apilibrary.constant

import `in`.dmart.apilibrary.util.Logger
import android.annotation.SuppressLint
import android.content.Context

@SuppressLint("StaticFieldLeak")
object ApiUrls {
    var baseUrl: String? = null
    //var headerNodeID = ""
    fun initialize(baseURL: String) {
        Logger.log("API", "URL----$baseURL")
        baseUrl = baseURL
    }

    var headerTokenn = ""

    // public static final String API_POST_LOGIN_USERNAME="/api/trips/v1/user/login";
    const val API_POST_LOGIN_USERNAME = "/api/auth/v1/login"
    const val API_POST_LOGOUT = "/api/auth/v1/logout"
    const val API_GET_TRIPS = "/api/trips/v1/printlabel"

    //dad
    // public static final String API_GET_CRATE_DATA="/dad/v1/crates/itemdetails";
    const val API_GET_CRATE_DATA = "/api/trips/v1/crates/itemdetails"

    //public static final String API_GET_LOADED_CRATES="/dad/v1/trip/crates";
    const val API_GET_LOADED_CRATES = "/api/trips/v1/"
    const val API_GET_START_TRIPS = "/api/dad/v1/trips/tostart"
    const val API_POST_CLOSE_TRIP = "/api/trips/v1/close"

    //public static final String API_GET_LOADED_TRIPS="/dad/v1/trips/listing";
    const val API_GET_LOADED_TRIPS = "/api/trips/v1/listing"

    //public static final String API_POST_CRATES_LOADED="dad/v1/crates/load";
    const val API_POST_CRATES_LOADED = "/api/trips/v1/crates/load"

    //public static final String API_RECONCILE_SHORT_GET_LIST="/dad/v1/crates/getshortmarked";
    const val API_RECONCILE_SHORT_GET_LIST = "/api/trips/v1/crates/getshortmarked"
    const val API_START_TRIP = "/api/dad/v1/trip/start" //"/api/trips/v1/updatestatus";

    //public static final String API_GET_ORDER_LOADING="/dad/v1/trip/v1/crates/details";
    const val API_GET_ORDER_LOADING = "/api/trips/v1/"
    //const val API_POST_LOGIN = "/auth/v1" //auth,login
    //const val API_POST_ORDER_CRATES_LOADED = "dad/v1/crates/capacity/load"
    const val API_SEARCH_LIST = "/api/trips/v1/search/"

    //public static final String API_GET_READYFORSTART_TRIP="/dad/v1/trips";
    // public static final String API_START_TRIP="/dad/v1/trips/start";
    const val API_GET_MAP_RESOURCES = "/api/trips/v1/trip/resource"
    const val API_GET_CLOSE_TRIPS = "/api/trips/v1/fetch/closetrip"
    const val API_POST_MAP_RESOURCES = "/api/trips/v1/update/resource"
    const val API_GET_DRIVER_LIST = "/api/dad/v1/driver"
    const val API_GET_VEHICLE_LIST = "/api/dad/v1/vehicle"
    const val API_GET_DA_LIST = "/api/dad/v1/search"
    const val API_POST_CHANGE_PWD = "/api/auth/v1/user/changePassword"
    const val API_GET_EC_RECEIVING = "/api/dad/v1/emptycrates/tobereceivecrates"
    const val API_POST_EC_RECEIVING = "/api/dad/v1/emptycrates/receiving"
    const val API_GET_EC_DISCREPANCIES = "/api/dad/v1/emptycrates/receivecrates"
    const val API_POST_ADD_REMARK_EC_DISCREPANCIES = "/api/dad/v1/emptycrates/cashier/receiving"
    const val API_GET_VEHICLE_CHECKIN_DETAILS = "/api/dad/v1/vehicle/checkin/vehicleid/"
    const val API_POST_VEHICLE_CHECKIN_UPDATE = "/api/dad/v1/vehicle/checkin/"
}