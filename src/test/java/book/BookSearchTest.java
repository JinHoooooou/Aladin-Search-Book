package book;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.select.Evaluator.IsEmpty;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

class BookSearchTest {

  public static final String SUCCESS_URL = "https://www.aladin.co.kr/search/wsearchresult.aspx?SearchTarget=All&SearchWord=refactoring&x=0&y=0";
  public static final String FAIL_URL = "https://www.aladin.co.kr/search/wsearchresult.aspx?SearchTarget=All&SearchWord=&x=0&y=0";
  private static final int RESULT_FAIL_SIZE = 0;
  private static final int RESULT_SUCCESS_SIZE = 1;
  Document mockCrawlingResult = mock(Document.class);
  Elements mockSearch3Result = mock(Elements.class);
  BookSearch bookSearch = new BookSearch();

  @Test
  @DisplayName("알라딘 도서 검색창에 책을 검색했을때 결과가 나오지 않으면 status : 404, message : NOT_FOUND를 Json형태로 리턴한다.")
  public void testShouldReturn404AndNotFoundWhenNotExistBookInAladin()
      throws IOException, JSONException {
    // Given: 없는 책을 검색한다. (아무것도 입력안함)
    setGivenMocking(RESULT_FAIL_SIZE);

    // When: searchBook 메서드를 호출한다.
    JSONObject actual = bookSearch.searchBook(mockCrawlingResult);

    // Then: 404 status와 NOT_FOUND message를 리턴한다.
    assertEquals(HttpStatusCode.NOT_FOUND.statusCode, getStatusCode(actual));
    assertEquals(HttpStatusCode.NOT_FOUND.message, getMessage(actual));
  }

  @Test
  @DisplayName("알라딘 도서 검색창에 책을 검색했을때 결과가 나오면 status : 200, message : OK를 Json형태로 리턴한다.")
  public void testShouldReturn200AndOKWhenExistBookInAladin()
      throws IOException, JSONException {
    // Given: 있는 책을 검색한다. ("refactoring")
    setGivenMocking(RESULT_SUCCESS_SIZE);

    // When: searchBook 메서드를 호출한다.
    JSONObject actual = bookSearch.searchBook(mockCrawlingResult);

    // Then: 200 status와 OK message를 리턴한다.
    assertEquals(HttpStatusCode.OK.statusCode, getStatusCode(actual));
    assertEquals(HttpStatusCode.OK.message, getMessage(actual));
  }

  @Test
  @DisplayName("결과 값이 없는 경우 bookList는 empty list이어야 한다")
  public void testShouldReturnEmptyBookListWhenStatusIs404() throws JSONException {
    // Given: 인풋값이 빈칸
    setGivenMocking(RESULT_FAIL_SIZE);

    // When: searchBook 메서드를 호출한다.
    JSONObject actual = bookSearch.searchBook(mockCrawlingResult);

    // Then: 그런 책은 없다고 말해줌
    assertEquals(0, getBookList(actual).length());
  }

  private int getStatusCode(JSONObject jsonObject) throws JSONException {
    return jsonObject.getInt("status");
  }

  private String getMessage(JSONObject jsonObject) throws JSONException {
    return jsonObject.getString("message");
  }

  private JSONArray getBookList(JSONObject jsonObject) throws JSONException {
    return jsonObject.getJSONArray("bookList");
  }

  private void setGivenMocking(int searchResultSize) {
    when(mockCrawlingResult.select("div#Search3_Result")).thenReturn(mockSearch3Result);
    when(mockSearch3Result.size()).thenReturn(searchResultSize);
  }
}