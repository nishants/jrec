package jrec;
import lombok.Getter;

public enum HttpHeaderTags {
  ACCEPT("Accept"),
  ACCEPT_CHARSET("Accept-Charset"),
  ALLOW("Allow"),
  CACHE_CONTROL("Cache-Control"),
  CONTENT_DISPOSITION("Content-Disposition"),
  CONTENT_LENGTH("Content-Length"),
  CONTENT_TYPE("Content-Type"),
  DATE("Date"),
  ETAG("ETag"),
  EXPIRES("Expires"),
  IF_MODIFIED_SINCE("If-Modified-Since"),
  IF_NONE_MATCH("If-None-Match"),
  LAST_MODIFIED("Last-Modified"),
  LOCATION("Location"),
  PRAGMA("Pragma");

  HttpHeaderTags(String tag){
    this.tag = tag;
  }

  @Getter
  private String tag;
}
