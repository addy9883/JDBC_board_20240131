package com.woori.jdbc.board;

import com.woori.jdbc.board.util.MysqlUtil;
import com.woori.jdbc.board.util.SecSql;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class App {

  public List<Article> articles;

  public App() {
    articles = new ArrayList<>();
  }

  private static boolean isDevMode() {
    // 이 부분을 false로 바꾸면 production 모드 이다.
    return true;
  }

  public void run() {
    System.out.println("== JDBC 게시판 프로그램 ==");
    Scanner sc = new Scanner(System.in);

    while (true) {
      System.out.printf("명령) ");
      String cmd = sc.nextLine();
      Rq rq = new Rq(cmd);

      // DB 세팅
      MysqlUtil.setDBInfo("localhost", "root", "", "text_board");
      MysqlUtil.setDevMode(isDevMode());

      // 명령 로직 실행
      doAction(sc, rq);
    }
  }

  private void doAction(Scanner sc, Rq rq) {
    if (rq.getUrlPath().equals("/usr/article/write")) {
      System.out.println("== 게시물 작성 ==");
      System.out.printf("제목 : ");
      String title = sc.nextLine();

      System.out.printf("내용 : ");
      String body = sc.nextLine();

      SecSql sql = new SecSql();
      sql.append("INSERT INTO article");
      sql.append("SET regDate = NOW()");
      sql.append(", updateDate = NOW()");
      sql.append(", title = ?", title);
      sql.append(", `body` = ?", body);

      int id = MysqlUtil.insert(sql);

      Article article = new Article(id, title, body);
      articles.add(article);

      System.out.printf("%d번 게시물이 작성되었습니다.\n", id);
    } else if (rq.getUrlPath().equals("/usr/article/list")) {
      System.out.println("== 게시물 리스트 ==");

      SecSql sql = new SecSql();
      sql.append("SELECT *");
      sql.append("FROM article");
      sql.append("ORDER BY id DESC");

      List<Map<String, Object>> articlesListMap = MysqlUtil.selectRows(sql);

      for(Map<String, Object> articleMap : articlesListMap) {
        articles.add(new Article(articleMap));
      }

      if (articles.isEmpty()) {
        System.out.println("게시물이 존재하지 않습니다.");
        return;
      }

      System.out.println("번호 / 제목");
      for (Article article : articles) {
        System.out.printf("%d / %s\n", article.id, article.title);
      }
    } else if (rq.getUrlPath().equals("/usr/article/modify")) {
      int id = rq.getIntParam("id", 0);

      if (id == 0) {
        System.out.println("id를 올바르게 입력해주세요.");
        return;
      }

      System.out.printf("새 제목 : ");
      String title = sc.nextLine();
      System.out.printf("새 내용 : ");
      String body = sc.nextLine();

      SecSql sql = new SecSql();
      sql.append("UPDATE article");
      sql.append("SET updateDate = NOW()");
      sql.append(", title = ?", title);
      sql.append(", `body` = ?", body);
      sql.append("WHERE id = ?", id);

      MysqlUtil.update(sql);

      System.out.printf("%d번 게시물이 수정되었습니다.\n", id);
    } else if (rq.getUrlPath().equals("exit")) {
      System.out.println("== 프로그램을 종료합니다 ==");
      System.exit(0);
    } else {
      System.out.println("명령어를 잘못 입력하셨습니다.");
    }
  }
}