package com.woori.jdbc.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JDBCInsertTest {
  public static void main(String[] args) {

    //데이터베이스 연결 정보
    String jdbcDriver = "com.mysql.cj.jdbc.Driver";
    String url = "jdbc:mysql://127.0.0.1:3306/text_board?useUnicode=true&characterEncoding=UTF-8";
    String user = "root";
    String password = "";

    Connection conn = null;
    PreparedStatement ps = null;

    try {
      Class.forName(jdbcDriver);

      conn = DriverManager.getConnection(url, user, password);

      String sql = "INSERT INTO article";
      sql += " SET regDate = NOW(),";
      sql += " updateDate = NOW(),";
      sql += " title = CONCAT(\"제목\",RAND()),";
      sql += " `body` = CONCAT(\"내용\",RAND())";

      ps = conn.prepareStatement(sql);
      int affectedRows = ps.executeUpdate();

      System.out.println("affectedRows : " + affectedRows);

    } catch (ClassNotFoundException e) {
      System.out.println("드라이버 로딩 실패");
    } catch (SQLException e) {
      System.out.println("에러: " + e);
    } finally {
      try {
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