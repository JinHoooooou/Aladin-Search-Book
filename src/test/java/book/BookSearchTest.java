package book;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class BookSearchTest {

  Document mockCrawlingResult = mock(Document.class);
  Elements mockSearch3Result = mock(Elements.class);
  BookSearch bookSearch = new BookSearch();

  @Test
  @DisplayName("알라딘 도서 검색창에 책을 검색했을때 결과가 나오지 않으면 status : 404, message : NOT_FOUND를 Json형태로 리턴한다.")
  public void testShouldReturn404AndNotFoundWhenNotExistBookInAladin()
      throws JSONException {
    // Given: 없는 책을 검색한다. (아무것도 입력안함)
    when(mockCrawlingResult.select("div#Search3_Result")).thenReturn(mockSearch3Result);
    when(mockSearch3Result.size()).thenReturn(0);

    // When: searchBook 메서드를 호출한다.
    JSONObject actual = bookSearch.searchBook(mockCrawlingResult);

    // Then: 404 status와 NOT_FOUND message를 리턴한다.
    assertEquals(HttpStatusCode.NOT_FOUND.statusCode, getStatusCode(actual));
    assertEquals(HttpStatusCode.NOT_FOUND.message, getMessage(actual));
  }

  @Test
  @DisplayName("알라딘 도서 검색창에 책을 검색했을때 결과가 나오면 status : 200, message : OK를 Json형태로 리턴한다.")
  public void testShouldReturn200AndOKWhenExistBookInAladin()
      throws JSONException {
    // Given: 있는 책을 검색한다. ("refactoring")
    when(mockCrawlingResult.select("div#Search3_Result")).thenReturn(mockSearch3Result);
    when(mockSearch3Result.size()).thenReturn(0);

    // When: searchBook 메서드를 호출한다.
    JSONObject actual = bookSearch.searchBook(mockCrawlingResult);

    // Then: 200 status와 OK message를 리턴한다.
    assertEquals(HttpStatusCode.OK.statusCode, getStatusCode(actual));
    assertEquals(HttpStatusCode.OK.message, getMessage(actual));
  }

  private int getStatusCode(JSONObject jsonObject) throws JSONException {
    return jsonObject.getInt("status");
  }

  private String getMessage(JSONObject jsonObject) throws JSONException {
    return jsonObject.getString("message");
  }
}