package org.example.form;

import org.example.service.UI;

public class Main {
    public static void main(String[] args) {
        // UI/서비스 매니저 객체 생성 (초기화)
        UI ui = new UI();
        // 메인 메뉴(첫 화면) 실행
        new MainMenu(ui);
    }
}