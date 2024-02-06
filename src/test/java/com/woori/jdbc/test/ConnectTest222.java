package com.woori.jdbc.test;

import com.woori.jdbc.board.Article;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConnectTest222 {
  public static void main(String[] args) throws SQLException {

  String jdbcDriver = "com.mysql.cj.jdbc.Driver";

  String url = "jdbc:mysql://localhost:3306/text_board";
  String user = "root";
  String password = "";

  Connection conn = null;
  PreparedStatement pstat = null;
  ResultSet rs = null;

    List<Article> articles = new ArrayList<>();

  try{
    Class.forName(jdbcDriver);

    conn = DriverManager.getConnection(url,user,password);

    String sql = "SELECT id,regDate,updateDate,title,body FROM article ORDER BY id DESC;";
    pstat = conn.prepareStatement(sql);
    rs = pstat.executeQuery(sql);

    while (rs.next()){
      int id = rs.getInt("id");
      String regDate = rs.getString("regDate");
      String updateDate = rs.getString("updateDate");
      String title = rs.getString("title");
      String body = rs.getString("body");

      Article article = new Article(id,regDate,updateDate,title,body);
      articles.add(article);
    }

    System.out.println("결과 : " + articles);


    System.out.println("\n축하한다. 연결에 성공했구나!");


  }catch(ClassNotFoundException e){
    System.out.println("드라이버 로딩 실패다.");

    }catch (SQLException sqlException){
    System.out.println("sql 문법 오류로구만");
  } finally {
    if(conn != null && conn.isClosed()){
      conn.close();
    }
  }
  }
}
