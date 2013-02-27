package com.example.sample;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.android.quickreturnview.QuickReturnListView;
import com.android.quickreturnview.QuickReturnViewType;

public class MainActivity extends Activity {
    private String[] listItems = {
            "Android", "Android", "Android", "Android", "Android", "Android", "Android",
            "Android", "Android", "Android", "Android", "Android", "Android", "Android",
            "Android", "Android", "Android", "Android", "Android", "Android", "Android",
            "Android", "Android", "Android", "Android", "Android", "Android", "Android",
            "Android", "Android", "Android", "Android", "Android", "Android", "Android"
    };
    private QuickReturnListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        listView = (QuickReturnListView) findViewById(R.id.listview);
        View quickReturnView = findViewById(R.id.return_view);
        listView.setQuickReturnView(quickReturnView);
        listView.setReturnViewType(QuickReturnViewType.BOTTOM);

        listView.setAdapter(new ArrayAdapter(this,  android.R.layout.simple_list_item_1, listItems));
    }
}
