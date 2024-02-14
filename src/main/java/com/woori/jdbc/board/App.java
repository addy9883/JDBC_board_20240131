package com.woori.jdbc.board;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {

  List<Article> articles;
  int articleLastId;

  public App() {
    articles = new ArrayList<>();
    articleLastId = 0;
  }

  public void run() {
    System.out.println("== JDBC 게시판 프로그램 ==");

    Scanner sc = new Scanner(System.in);
    Connection conn = null;

    while (true) {
      System.out.printf("명령) ");
      String cmd = sc.nextLine().trim();
      Rq rq = new Rq(cmd);

      // JDBC 드라이버 로드 / DB 연결 시작
      String jdbcDriver = "com.mysql.cj.jdbc.Driver";

      // 데이터베이스 연결 정보
      String url = "jdbc:mysql://127.0.0.1:3306/text_board?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";
      String username = "root";
      String password = "";

      try {
        // JDBC 드라이버 로드
        Class.forName(jdbcDriver);

        // 데이터베이스에 연결
        conn = DriverManager.getConnection(url, username, password);

        doAction(conn, sc,cmd, rq);

      } catch (ClassNotFoundException e) {
        System.out.println("드라이버 로딩 실패");
      } catch (SQLException e) {
        System.out.println("에러 : " + e);
      } finally {
        try {
          if (conn != null && !conn.isClosed()) {
            // 연결 닫기
            conn.close();
          }
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
  }

  private void doAction(Connection conn, Scanner sc, String cmd, Rq rq) {
    if (rq.getUrlPath().equals("/usr/article/write")) {
      writeArticle(conn, sc);

    } else if (rq.getUrlPath().equals("/usr/article/list")) {
      showArticleList(conn);

    } else if (rq.getUrlPath().equals("/usr/article/modify")) {
      modifyArticle(conn, sc, rq);

    } else if (rq.getUrlPath().equals("exit")) {
      System.out.println("== 프로그램을 종료합니다 ==");
      System.exit(0);

    } else {
      System.out.println("명령어를 잘못 입력하셨습니다.");
    }
  }

  private void writeArticle(Connection conn, Scanner sc) {
    System.out.println("== 게시물 작성 ==");
    System.out.printf("제목 : ");
    String title = sc.nextLine();

    System.out.printf("내용 : ");
    String body = sc.nextLine();

    String sql = "INSERT INTO article";
    sql += " SET regDate = NOW()";
    sql += ", updateDate = NOW()";
    sql += ", title = \"" + title + "\"";
    sql += ", `body` = \"" + body + "\"";

    try (PreparedStatement pstat = conn.prepareStatement(sql)) {
      pstat.executeUpdate();
    } catch (SQLException e) {
      System.out.println("에러 : " + e);
    }
  }

  private void showArticleList(Connection conn) {
    System.out.println("== 게시물 리스트 ==");

    String sql = "SELECT * FROM article ORDER BY id DESC;";
    try (PreparedStatement pstat = conn.prepareStatement(sql);
         ResultSet rs = pstat.executeQuery()) {

      List<Article> articles = new ArrayList<>();
      while (rs.next()) {
        int id = rs.getInt("id");
        String title = rs.getString("title");
        String body = rs.getString("body");

        Article article = new Article(id, title, body);
        articles.add(article);
      }

      if (articles.isEmpty()) {
        System.out.println("게시물이 존재하지 않습니다.");
        return;
      }

      System.out.println("번호 / 제목 / 내용 ");
      for (Article article : articles) {
        System.out.printf("%d / %s / %s\n", article.id, article.title, article.body);
      }

    } catch (SQLException e) {
      System.out.println("에러 : " + e);
    }
  }

  private void modifyArticle(Connection conn, Scanner sc, Rq rq) {
    int id = rq.getIntParam("id", 0);

    if (id == 0) {
      System.out.println("id를 올바르게 입력하시오");
      return;
    }

    System.out.printf("새제목 : ");
    String title = sc.nextLine();
    System.out.printf("새내용 : ");
    String body = sc.nextLine();

    String sql = "UPDATE article";
    sql += " SET updateDate = NOW()";
    sql += ", title = \"" + title + "\"";
    sql += ", `body` = \"" + body + "\"";
    sql += " WHERE id = " + id;

    try (PreparedStatement pstat = conn.prepareStatement(sql)) {
      pstat.executeUpdate();
    } catch (SQLException e) {
      System.out.println("에러 : " + e);
    }

    System.out.printf("%d번 게시물이 수정되었습니다.\n", id);
  }
}
