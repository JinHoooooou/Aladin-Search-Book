package com.example.demo;

import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest
class SearchBookTest {

  public static final String SUCCESS_URL = "https://www.aladin.co.kr/search/wsearchresult.aspx?SearchTarget=All&SearchWord=refactoring&x=0&y=0";
  public static final String FAIL_URL = "https://www.aladin.co.kr/search/wsearchresult.aspx?SearchTarget=All&SearchWord=&x=20&y=16";
  SearchBook searchBook = new SearchBook();
  Document crawlingResult;


  @Test
  void testShouldReturn404NotFoundWhenNotExist() throws IOException, JSONException {

    //Given : Set search name is not found
    crawlingResult = Jsoup.connect(FAIL_URL).get();

    //When : Call searchBook method
    JSONObject actual = searchBook.searchBook(crawlingResult);

    //Then : Should return status 404
    assertEquals(HttpStatusCode.NOT_FOUND, getStatusCode(actual));
  }

  @Test
  void testShouldReturn200NotFoundWhenExistBook() throws IOException, JSONException {

    //Given : Set search name will found
    crawlingResult = Jsoup.connect(SUCCESS_URL).get();

    //When : Call searchBook method
    JSONObject actual = searchBook.searchBook(crawlingResult);

    //Then : Should return status 200
    assertEquals(HttpStatusCode.OK, getStatusCode(actual));
  }

  private int getStatusCode(JSONObject jsonObject) throws JSONException {
    return jsonObject.getInt("status");
  }
}
