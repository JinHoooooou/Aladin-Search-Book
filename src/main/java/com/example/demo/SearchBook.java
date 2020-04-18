package com.example.demo;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class SearchBook {

  public JSONObject searchBook(Document crawlingResult) throws JSONException {
    Elements elements = crawlingResult.select("div#Search3_Result");
    if (elements.size() == 0) {
      return getJsonFormat(HttpStatusCode.NOT_FOUND, "NOT_FOUND");
    }
    return getJsonFormat(HttpStatusCode.OK, "OK");
  }

  public JSONObject getJsonFormat(int statusCode, String message) throws JSONException {
    JSONObject resultJson = new JSONObject();
    resultJson.put("status", statusCode);
    resultJson.put("message", message);
    return resultJson;
  }
}
