package com.woori.jdbc.board;

public class Article {
  public int id;
  public String title;
  public String content;

  //생성자 메서드 : 객체가 만들어질 때 딱 한번 실행된다.
  public Article(int id,String title,String content){
    this.id = id;
    this.title = title;
    this.content = content;
  }

  @Override
  public String toString() {
    return "Article{" +
        "id=" + id +
        ", title='" + title + '\'' +
        ", content='" + content + '\'' +
        '}';
  }
}
