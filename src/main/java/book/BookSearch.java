package book;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class BookSearch {

  public JSONObject searchBook(Document crawlingResult) throws JSONException {
    Elements elements = crawlingResult.select("div#Search3_Result");
    if (elements.size() == 0) {
      JSONArray bookList = new JSONArray();
      return getJsonFormat(HttpStatusCode.NOT_FOUND.statusCode, HttpStatusCode.NOT_FOUND.message,
          bookList);
    }

    JSONArray bookList = new JSONArray();
//    Elements books = elements.select("b.bo3");
//    for (int i = 0; i < books.size(); i++) {
//      bookList.put(books.get(i).text());
//    }
    return getJsonFormat(HttpStatusCode.OK.statusCode, HttpStatusCode.OK.message, bookList);
  }

  private JSONObject getJsonFormat(int statusCode, String message, JSONArray bookList)
      throws JSONException {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("status", statusCode);
    jsonObject.put("message", message);
    jsonObject.put("bookList", bookList);
    return jsonObject;
  }


}
