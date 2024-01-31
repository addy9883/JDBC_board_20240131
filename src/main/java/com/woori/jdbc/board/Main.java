package com.woori.jdbc.board;

import java.util.Scanner;

//TIP 코드를 <b>실행</b>하려면 <shortcut actionId="Run"/>을(를) 누르거나
// 에디터 여백에 있는 <icon src="AllIcons.Actions.Execute"/> 아이콘을 클릭하세요.
public class Main {
  public static void main(String[] args) {
    System.out.println(" == JDBC 게시판 프로그램 == ");
    Scanner sc = new Scanner(System.in);

    int articleLastId = 0;

    while (true) {
      System.out.printf("명령 ) ");
      String cmd = sc.nextLine();

      if (cmd.equals("/user/article/write")) {
        System.out.println("== 게시물 작성 ==");
        System.out.printf("제목 : ");
        String title = sc.nextLine();
        System.out.printf("내용 : ");
        String content = sc.nextLine();

        int id = ++articleLastId;

        System.out.printf("%d번 게시물이 작성되었습니다.\n", id);

      } else if (cmd.equals("exit")) {
        System.out.println("게시판 프로그램을 종료합니다.");
        break;
      } else {
        System.out.println("명령어를 잘못 입력하셨습니다.");
      }
    }
  }
}