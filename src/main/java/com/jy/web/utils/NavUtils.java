package com.jy.web.utils;

import org.springframework.ui.Model;

public class NavUtils {
	private final static String Menu="m";
	private final static String Order="o";
	private final static String Special="s";
	private final static String Home="h";
	private final static String Manager_menu="mm";
	private final static String Manager_table="mt";
	private final static String Manager_adhoc="ma";
	private final static String Manager_lobby="ml";
	private final static String Manager_togo="tg";
	
	
	public static void setNav(Model uiModel,String currentNav){
		uiModel.addAttribute("currentNav", currentNav);
	}
	
	public static void setMenuNav(Model uiModel){
		setNav(uiModel, Menu);
	}
	public static void setOrderNav(Model uiModel){
		setNav(uiModel, Order);
	}
	public static void setSpecialNav(Model uiModel){
		setNav(uiModel, Special);
	}
	public static void setHomeNav(Model uiModel){
		setNav(uiModel, Home);
	}
	public static void setManagerMenuNav(Model uiModel){
		setNav(uiModel, Manager_menu);
	}
	public static void setManagerTableNav(Model uiModel){
		setNav(uiModel, Manager_table);
	}
	public static void setManagerAdhocNav(Model uiModel){
		setNav(uiModel, Manager_adhoc);
	}
	public static void setManagerLobbyNav(Model uiModel){
		setNav(uiModel, Manager_lobby);
	}
	public static void setManagerTogoNav(Model uiModel){
		setNav(uiModel, Manager_togo);
	}
	
}
