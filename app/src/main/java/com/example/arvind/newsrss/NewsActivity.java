package com.example.arvind.newsrss;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity {

    ListView newsListview;
    int selection;
    ArrayList<News> newsArray=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        newsListview=findViewById(R.id.newsListView);

        String url=getIntent().getStringExtra("url");

        selection=getIntent().getIntExtra("selection",0);


        new NewsFeed().execute(url);

    }


    class NewsFeed extends AsyncTask<String ,Void,Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... strings) {
            InputStream inputStream=network(strings[0]);

            if(selection==0) {
                parsing(inputStream);
            }else if(selection==1){
                hindiParsing(inputStream);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void avoid) {
            super.onPostExecute(avoid);

            CustomAdapter customAdapter=new CustomAdapter(NewsActivity.this,newsArray);
            newsListview.setAdapter(customAdapter);
        }

        InputStream network(String ur){
           try{

               URL url=new URL(ur);
               HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
               return httpURLConnection.getInputStream();

           }catch (Exception e){
                e.printStackTrace();
           }
           return null;
        }

        void parsing(InputStream inputStream){
            XmlPullParser parser= Xml.newPullParser();
            try{
                Log.i("Inside","parsing");
                parser.setInput(inputStream,"utf-8");
                int event=parser.getEventType();
                News news=null;
                while(event!=XmlPullParser.END_DOCUMENT) {
                    String tag = parser.getName();

                    if (event == XmlPullParser.START_TAG) {

                        if (tag.equals("item") && news == null) {
                            news = new News(null, null, null, null);
                        } else if (tag.equals("title") && news != null) {
                            String title = parser.nextText();
                            news.setTitle(title);
                            Log.i("Title",title);
                        } else if (tag.equals("description") && news != null) {

                            try {
                                String description = parser.nextText();
//                                int startIndex=description.lastIndexOf(".cms")+4;
//                                int endIndex=description.lastIndexOf("https://");
//                                String image=description.substring(startIndex+1,endIndex);
//                                news.setImage(image);

                                int ds=description.indexOf("</a>");
                                if(ds!=-1) {
                                    Log.i("ds int", String.valueOf(ds));
                                    description = description.substring(ds+4,description.length());
                                    Log.i("ds", description);
                                    news.setDiscription(description);
                                }else{
                                    news.setDiscription(description);
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }else if(tag.equals("link") && news!=null){
                            String link=parser.nextText();
                            int startIndex = link.indexOf(".com") + 4;
                            String newLink=link.substring(0,startIndex);
                            int i;
                            for(i=0;i<link.length();i++){
                                if(link.charAt(i)>=48&&link.charAt(i)<=57){
                                    break;
                                }
                            }
                            newLink=newLink+"/photo/"+link.substring(i,link.length());
                            Log.i("Link",newLink);
                            news.setImage(newLink);

                        }


                        else if(tag.equals("pubDate") && news!=null){
                            String pubDate=parser.nextText();
                            news.setPubDates(pubDate);
                            newsArray.add(news);
                            Log.i("pubDate",pubDate);
                            news=null;
                        }
                    }
                    event=parser.next();
                }
                inputStream.close();

            }catch (Exception e){
                e.printStackTrace();
            }
        }


        void hindiParsing(InputStream inputStream){
            XmlPullParser parser= Xml.newPullParser();
            try {
                parser.setInput(inputStream, "utf-8");

                int event=parser.getEventType();
                News news=null;
                while(event!=XmlPullParser.END_DOCUMENT){

                    String tag=parser.getName();
                    if(event==XmlPullParser.START_TAG){

                        if(tag.equals("item")&&news==null){
                            news = new News(null, null, null, null);
                        } else if (tag.equals("title") && news != null) {
                            String title = parser.nextText();
                            news.setTitle(title);
                            Log.i("title","----------------------------"+title);
                        }else if(tag.equals("description")&&news!=null){
                            String description=parser.nextText();

                            int imageStartIndex=description.indexOf("src=")+4;
                            int imageLastIndex=description.indexOf(".jpg>")+5;
                            String imageLink="https:"+description.substring(imageStartIndex,imageLastIndex-1);

                            news.setImage(imageLink);
                            Log.i("imageLink","----------------------------"+imageLink);


                            String descript=description.substring(imageLastIndex,description.length());
                            news.setDiscription(descript);

                            Log.i("description","----------------------------"+descript);
                        }else if(tag.equals("pubDate")){
                            String pubDate=parser.nextText();
                            news.setPubDates(pubDate);
                            Log.i("pubDate","----------------------------"+pubDate);
                            newsArray.add(news);
                            news=null;
                        }
                    }
                    event=parser.next();
                }
                inputStream.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

}

/*
# XmlParser
---
---
* This XmlParser is used to get the data from any xml file.
* To get the data, first we need to parse that file.
* We need to choose a parser which is used to parse the xml file. Here i am choosing XmlPullParser to parse the
 xml file. So first i need to make an object of parser.
*An Xml file consist of event ,text,Name,AttributeValues etc so XmlPullParser has seperate function to
 retrieve the data.
```java
    public void parsing(InputStream inputStream) {

        XmlPullParser parser=Xml.newPullParser();//This will return an instance of XmlPullParser
        //To parse the xml file we need XmlPullParser object
        try {
            parser.setInput(inputStream, null);//setting inputStream to parser to read data.

            int event=parser.getEventType();//it will return the type of event that happens e.g Document  start
            //Document end

            //This below loop run till the file is not ended.
            while (event!=parser.END_DOCUMENT){

                //Below one will return the name of the tag e.g <link>,<Image>,<Temperature>
                String tag=parser.getName();

                //if event is startTag then below condition will be executed
                if(event==parser.START_TAG){

                    //These string like city,temperature,humidity ,pressure must match with the xml file.

                      if(tag.equals("city")){
                          //it will return the attributeValue(here attribute value is name) of city tag
                          String cityName=parser.getAttributeValue(null,"name");//

                          //The below one, will return the text present inside <city>...</city> tag
                          String desription=parser.nextText();
                      }
                      else if(tag.equals("temperature")){
                          String temperature=parser.getAttributeValue("null","value");
                          String unit=parser.getAttributeValue("null","unit");
                      }else if(tag.equals("humidity")){
                          String humidity=parser.getAttributeValue("null","value");
                      }else if(tag.equals("pressure")){
                          String pressure=parser.getAttributeValue("null","value");
                      }
                }
                event=parser.next();//it will move the parser into next event

            }
            inputStream.close();//it will clost the inputStream.
        }catch(Exception e){
            e.printStackTrace();
        }
    }


```
*/