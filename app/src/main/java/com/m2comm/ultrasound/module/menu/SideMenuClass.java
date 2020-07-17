package com.m2comm.ultrasound.module.menu;

import java.util.ArrayList;

public class SideMenuClass
{
    public ArrayList<SubSideMenuClass> SubSideMenuList = new ArrayList<SubSideMenuClass>();
    public String url;
    public String info;

    public Class activity;

    int icon;
    public SideMenuClass(String info, int icon, Class activity, String url, ArrayList<SubSideMenuClass> SubSideMenuList)
    {
        this.info = info;
        this.activity = activity;
        this.url = url;
        this.icon = icon;
        this.SubSideMenuList = SubSideMenuList;
    }
}