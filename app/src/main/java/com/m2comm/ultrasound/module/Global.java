package com.m2comm.ultrasound.module;

import com.m2comm.ultrasound.DTO.MenuDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Global {

    public static String main_url = "https://www.ultrasound.or.kr/";

    public static String MENU1 = "0"; // 학회소개
    public static String MENU2 = "1"; //학회지
    public static String MENU3 = "2"; // 행사일정.
    public static String MENU4 = "3"; // 회원공간
    public static String MENU5 = "4"; //교육자료

    private String[] exts = {"pptx", "docx", "doc", "hwp", "xlsx"};
    private String[] imgExts = {"jpeg", "gif", "jpeg", "jpg", "png"};



    public static HashMap<String, String> getUrls = new HashMap<String, String>() {
        {
            put("getSchedule", "/app/php/get_schedule.php?code=directors");
            put("getBannerAndNotice", "/app/php/get_main.php");

        }
    };

    public static String[] top_title = {
            "학회소개",
            "",
            "학술행사",
            "회원공간",
            "교육자료",
    };

    public HashMap<String, ArrayList<MenuDTO>> getMenu = new HashMap<String, ArrayList<MenuDTO>>() {
        {
            put(MENU1,new ArrayList<MenuDTO>(Arrays.asList(
                    new MenuDTO("인사말","https://www.ultrasound.or.kr/app/php/about/index.php"),
                    new MenuDTO("연혁",""),
                    new MenuDTO("회칙","https://www.ultrasound.or.kr/app/php/about/laws.php"),
                    new MenuDTO("임원진 및 위원회","https://www.ultrasound.or.kr/app/php/about/organ.php"),
                    new MenuDTO("역대임원진","https://www.ultrasound.or.kr/app/php/about/board_prev.php"),
                    new MenuDTO("명예회원","https://www.ultrasound.or.kr/app/php/about/honor.php"),
                    new MenuDTO("사무국 안내","https://www.ultrasound.or.kr/app/php/about/office.php")

            )));

            put(MENU2,new ArrayList<MenuDTO>(Arrays.asList(
                    new MenuDTO("","")
            )));

            put(MENU3,new ArrayList<MenuDTO>(Arrays.asList(
                    new MenuDTO("정기학술대회","https://www.ultrasound.or.kr/app/php/bbs/workshop.php"),
                    new MenuDTO("정기연수교육","https://www.ultrasound.or.kr/app/php/schedule/index.php"),
                    new MenuDTO("정기 Hands-on 교육","https://www.ultrasound.or.kr/app/php/schedule/index.php?gubun=2"),
                    new MenuDTO("이사회 일정",""),
                    new MenuDTO("국제학회 일정","")
            )));

            put(MENU4,new ArrayList<MenuDTO>(Arrays.asList(
                    new MenuDTO("공지사항","https://www.ultrasound.or.kr/app/php/bbs/list.php?code=notice"),
                    new MenuDTO("뉴스레터","https://www.ultrasound.or.kr/app/php/bbs/newsletter.php"),
                    new MenuDTO("게시판","https://www.ultrasound.or.kr/app/php/bbs/list.php?code=board"),
                    new MenuDTO("자료실","https://www.ultrasound.or.kr/app/php/bbs/list.php?code=pds"),
                    new MenuDTO("증례토론","https://www.ultrasound.or.kr/app/php/case/index.php")
            )));

            put(MENU5,new ArrayList<MenuDTO>(Arrays.asList(
                    new MenuDTO("연수교육 자료","https://www.ultrasound.or.kr/app/php/bbs/reference.php?code=bbs_training"),
                    new MenuDTO("VODs","https://www.ultrasound.or.kr/app/php/bbs/vod.php"),
                    new MenuDTO("KAFE 자료","https://www.ultrasound.or.kr/app/php/bbs/reference.php?code=bbs_kafe"),
                    new MenuDTO("일반인을 위한 초음파 진료 소개","https://www.ultrasound.or.kr/app/php/education/info.php")
            )));

        }
    };

    public boolean extSearch(String ext) {
        for (String s : exts) {
            if (ext.contains(s)) {
                return true;
            }
        }
        return false;
    }

    public boolean imgExtSearch(String ext) {
        for (String s : imgExts) {
            if (ext.contains(s)) {
                return true;
            }
        }
        return false;
    }

    public boolean extPDFSearch(String ext) {
        if (ext.contains("pdf")) {
            return true;
        }
        return false;
    }

}
