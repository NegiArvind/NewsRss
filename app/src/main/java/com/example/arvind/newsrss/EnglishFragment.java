package com.example.arvind.newsrss;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class EnglishFragment extends Fragment {

    String[] category={"Top Stories","India","Sport","Science","Technical","Cricket"};

    ListView listView;
    ArrayAdapter<String> arrayAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.english_fragment,container,false);


        listView=view.findViewById(R.id.listView);
        arrayAdapter=new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,category);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getActivity(),NewsActivity.class);

                switch (i){
                    case 0:
                        intent.putExtra("url","https://timesofindia.indiatimes.com/rssfeedstopstories.cms");
                        //intent.putExtra("url","https://www.indiatvnews.com/rssnews/topstory.xml");
                        intent.putExtra("selection",0);
                        break;
                    case 1:
                        intent.putExtra("url","https://timesofindia.indiatimes.com/rssfeeds/-2128936835.cms");
                        intent.putExtra("selection",0);
                        break;

                    case 2:
                        intent.putExtra("url","https://timesofindia.indiatimes.com/rssfeeds/4719148.cms");
                        intent.putExtra("selection",0);
                        break;


                    case 3:
                        intent.putExtra("url","https://timesofindia.indiatimes.com/rssfeeds/-2128672765.cms");
                        intent.putExtra("selection",0);
                        break;

                    case 4:
                        intent.putExtra("url","https://timesofindia.indiatimes.com/rssfeeds/5880659.cms");
                        intent.putExtra("selection",0);
                        break;

                    case 5:
                        intent.putExtra("url","https://timesofindia.indiatimes.com/rssfeeds/4719161.cms");
                        intent.putExtra("selection",0);
                        break;
                }
                startActivity(intent);
            }
        });

        return view;
    }

    public static EnglishFragment getInstance(){

        return new EnglishFragment();
    }


    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("English News");
    }
}
