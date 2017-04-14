package ru.in.ssf.resp.conf;

public class Settings {
	////////// network settings  /////////////
	public static volatile String UDP_SERVER_HOST = "127.0.0.1";
	public static volatile int CAMEL_SERVER_PORT_1 = 8877;
	public static volatile int MAP_SERVER_PORT = 9999;
	
	////////// internal settings ///////////
	public static volatile int UDP_MESSAGE_MAX_SIZE = 1412;
	public static volatile int UDP_MAX_QUEUE_SIZE = 5000;


}
