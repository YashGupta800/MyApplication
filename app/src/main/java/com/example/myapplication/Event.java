package com.example.myapplication;

import java.net.URL;

public class Event {
  private String mHeadline;
  private String mSummary;
  private String mAuthor;
  private String mUrl;
  Event(String Headline, String Summary, String Author, String url)
  {
      mHeadline=Headline;
      mSummary=Summary;
      mAuthor=Author;
      mUrl=url;
  }
  public String getHeadline()
  {
      return mHeadline;
  }
  public String getSummary()
  {
return mSummary;
  }
  public String getAuthor()
  {
      return mAuthor;
  }
  public String getUrl(){return mUrl;}
}
