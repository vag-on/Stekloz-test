package net.simplifiedlearning.myheroapp;

/**
 * Created by Belal on 9/9/2017.
 */

public class Api {

    private static final String ROOT_URL = "http://vagon-parts.ru/android/Api.php?apicall=";

    public static final String URL_CREATE_HERO = ROOT_URL + "createhero";
    public static final String URL_READ_HEROES = ROOT_URL + "getheroes";
    public static final String URL_UPDATE_HERO = ROOT_URL + "updatehero";
    public static final String URL_DELETE_HERO = ROOT_URL + "deletehero&id=";
    public static final String URL_LOGIN = ROOT_URL + "login";

}
