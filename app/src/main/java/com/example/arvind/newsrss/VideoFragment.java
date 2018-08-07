package com.example.arvind.newsrss;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class VideoFragment extends Fragment {

    ListView newsListview;
    int selection;
    ArrayList<News> newsArray=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.video_fragment,container,false);

        return view;
    }

    class NewsFeed extends AsyncTask<String ,Void,Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... strings) {
            InputStream inputStream=network(strings[0]);
            parsing(inputStream);
            return null;
        }

        @Override
        protected void onPostExecute(Void avoid) {
            super.onPostExecute(avoid);

            CustomAdapter customAdapter=new CustomAdapter(getActivity(),newsArray);
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
    }
}
