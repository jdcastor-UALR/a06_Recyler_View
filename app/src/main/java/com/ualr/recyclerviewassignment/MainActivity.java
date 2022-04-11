package com.ualr.recyclerviewassignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.ualr.recyclerviewassignment.Utils.DataGenerator;
import com.ualr.recyclerviewassignment.adapter.AdapterListBasic;
import com.ualr.recyclerviewassignment.model.Email;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdapterListBasic mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_multi_selection);
        initComponent();
    }

    private void initComponent() {
        List<Email> emails = DataGenerator.getInboxData(getApplicationContext());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        mAdapter = new AdapterListBasic(this, emails);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnEmailClickListener(position -> mAdapter.selectEmail(position));
        mAdapter.setOnDeleteClickListener(position -> mAdapter.removeEmail(position));

        // TODO 09. Create a new instance of the created Adapter class and bind it to the RecyclerView instance created in step 03

        findViewById(R.id.fab).setOnClickListener(view -> {
            mAdapter.addEmail(0,DataGenerator.getRandomInboxItem(this));
            recyclerView.smoothScrollToPosition(0);
        });
    }
}