package com.woori.jdbc.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBC_Test {
  public static void main(String[] args) {

    //데이터베이스 연결 정보
    String jdbcDriver = "com.mysql.cj.jdbc.Driver";
    String url = "jdbc:mysql://127.0.0.1:3306/text_board?useUnicode=true&characterEncoding=UTF-8";
    String user = "root";
    String password = "";

    Connection conn = null;

    try {
      Class.forName(jdbcDriver);

      conn = DriverManager.getConnection(url, user, password);

      System.out.println("연결 성공");

    } catch (ClassNotFoundException e) {
      System.out.println("드라이버 로딩 실패");
    } catch (SQLException e) {
      System.out.println("에러: " + e);
    } finally {
      try {
        if (conn != null && !conn.isClosed()) {
          conn.close();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }
}