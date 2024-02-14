package com.woori.jdbc.board;

import java.util.Comparator;
import java.util.Map;

public class Article {
  public int id;
  public String title;
  public String body;
  public String regDate;;
  public String updateDate;;

  public Article(int id, String title, String body, String regDate, String updateDate) {
    this.id = id;
    this.title = title;
    this.body = body;
    this.regDate = regDate;
    this.updateDate = updateDate;
  }

  //생성자 메서드 : 객체가 만들어질 때 딱 한번 실행된다.
  public Article(int id,String title,String body){
    this.id = id;
    this.title = title;
    this.body = body;
  }

  public Article(Map<String, Object> articleMap) {
    this.id = (int) articleMap.get("id");
    this.regDate = (String) articleMap.get("regDate");
    this.updateDate = (String) articleMap.get("updateDate");
    this.title = (String) articleMap.get("title");
    this.body = (String) articleMap.get("body");

  }

  public int getId() {
    return id;
  }

  @Override
  public String toString() {
    return "Article{" +
        "id=" + id +
        ", title='" + title + '\'' +
        ", body='" + body + '\'' +
        '}';
  }
}

