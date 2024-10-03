package com.example.tp4_pa2_grupo12.conexion;

public class DataDB {

    //Información de la BD
    public static String host="sql10.freesqldatabase.com";
    public static String port="3306";
    public static String nameBD="sql10734909";
    public static String user="sql10734909";
    public static String pass="DzqXKZmLv1";

    //Información para la conexion
    public static String urlMySQL = "jdbc:mysql://" + host + ":" + port + "/"+nameBD;
    public static String driver = "com.mysql.jdbc.Driver";

}

