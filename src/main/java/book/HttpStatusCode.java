package book;

public enum HttpStatusCode {
  NOT_FOUND(404, "NOT_FOUND"),
  OK(200, "OK");

  public int statusCode;
  public String message;

  HttpStatusCode(int statusCode, String message) {
    this.statusCode = statusCode;
    this.message = message;
  }
}
