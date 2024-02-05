package com.woori.jdbc.test;

import com.woori.jdbc.board.Article;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCSelectTest {
  public static void main(String[] args) {

    //데이터베이스 연결 정보
    String jdbcDriver = "com.mysql.cj.jdbc.Driver";
    String url = "jdbc:mysql://127.0.0.1:3306/text_board?useUnicode=true&characterEncoding=UTF-8";
    String user = "root";
    String password = "";

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    List<Article> articles = new ArrayList<>();

    try {
      Class.forName(jdbcDriver);

      conn = DriverManager.getConnection(url, user, password);

      String sql = "SELECT *";
      sql += "FROM article ";
      sql += "ORDER BY id DESC";

      ps = conn.prepareStatement(sql);
      rs = ps.executeQuery(sql);

      while (rs.next()){
        int id = rs.getInt("id");
        String regDate = rs.getString("regDate");
        String updateDate = rs.getString("updateDate");
        String title = rs.getString("title");
        String body = rs.getString("body");

        Article article = new Article(id,regDate,updateDate,title,body);
        articles.add(article);
      }

      System.out.println("결과 : "+ articles);

    } catch (ClassNotFoundException e) {
      System.out.println("드라이버 로딩 실패");
    } catch (SQLException e) {
      System.out.println("에러: " + e);
    } finally {
      try {
        if (rs != null && !rs.isClosed()) {
          rs.close();
        }
        if (ps != null && !ps.isClosed()) {
          ps.close();
        }
        if (conn != null && !conn.isClosed()) {
          conn.close();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }
}