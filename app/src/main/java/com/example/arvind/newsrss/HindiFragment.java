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

public class HindiFragment extends Fragment {

    String[] category={"राष्ट्रीय","क्रिकेट","दुनिया","बॉलीवुड","टेक ज्ञान","धार्मिक स्थान","बॉक्स ऑफिस"};

    ListView listView;
    ArrayAdapter<String> arrayAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.hindi_fragment,container,false);


        listView=view.findViewById(R.id.listView);
        arrayAdapter=new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,category);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getActivity(),NewsActivity.class);

                switch (i){
                    case 0:
                        intent.putExtra("url","http://rss.jagran.com/rss/news/national.xml");
                        intent.putExtra("selection",1);
                        break;
                    case 1:
                        intent.putExtra("url","http://rss.jagran.com/rss/cricket/headlines.xml");
                        intent.putExtra("selection",1);
                        break;

                    case 2:
                        intent.putExtra("url","http://rss.jagran.com/rss/news/world.xml");
                        intent.putExtra("selection",1);
                        break;


                    case 3:
                        intent.putExtra("url","http://rss.jagran.com/rss/entertainment/bollywood.xml");
                        intent.putExtra("selection",1);
                        break;

                    case 4:
                        intent.putExtra("url","http://rss.jagran.com/rss/technology/tech-news.xml");
                        intent.putExtra("selection",1);
                        break;

                    case 5:
                        intent.putExtra("url","http://rss.jagran.com/rss/spiritual/mukhye-dharmik-sthal.xml");
                        intent.putExtra("selection",1);
                        break;

                    case 6:
                        intent.putExtra("url","http://rss.jagran.com/rss/entertainment/box-office.xml");
                        intent.putExtra("selection",1);
                        break;
                }
                startActivity(intent);
            }
        });

        return view;
    }

    public static HindiFragment getInstance(){

        return new HindiFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Hindi News");
    }
}
