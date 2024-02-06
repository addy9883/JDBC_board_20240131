package com.woori.jdbc.board;

import java.util.Comparator;

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
  public static Comparator<Article> idComparator = Comparator.comparingInt(Article::getId);
}

