package com.m2comm.ultrasound.module.menu;

public class SubSideMenuClass
    {
        public String info;
        public String url;
        public Class activity;
        public SubSideMenuClass(String info, String url, Class activity)
        {
            this.info = info;
            this.url = url;
            this.activity = activity;
        }
    }