package book;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class BookSearch {

  public JSONObject searchBook(Document crawlingResult) throws JSONException {
    Elements elements = crawlingResult.select("div#Search3_Result");
    if (elements.size() == 0) {
      return getJsonFormat(HttpStatusCode.NOT_FOUND.statusCode, HttpStatusCode.NOT_FOUND.message);
    }
    return getJsonFormat(HttpStatusCode.OK.statusCode, HttpStatusCode.OK.message);
  }

  private JSONObject getJsonFormat(int statusCode, String message)
      throws JSONException {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("status", statusCode);
    jsonObject.put("message", message);
    return jsonObject;
  }
}
