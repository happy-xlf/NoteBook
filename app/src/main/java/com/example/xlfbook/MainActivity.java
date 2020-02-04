package com.example.xlfbook;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<CostBean> mCostBeanList;
    private DatabaseHelper mdatabaseHelper;
    private CostListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mdatabaseHelper=new DatabaseHelper(this);
        mCostBeanList=new ArrayList<>();
        ListView costList=(ListView)findViewById(R.id.lv_main);
        initCostData();
        mAdapter = new CostListAdapter(this, mCostBeanList);
        costList.setAdapter(mAdapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);

                LayoutInflater inflater=LayoutInflater.from(MainActivity.this);
                View viewDialog=inflater.inflate(R.layout.new_cost_data,null);
                final EditText title=(EditText) viewDialog.findViewById(R.id.et_cost_title);
                final EditText money=(EditText)viewDialog.findViewById(R.id.et_cost_money);
                final DatePicker date=(DatePicker) viewDialog.findViewById(R.id.dp_cost_data);
                builder.setView(viewDialog);
                builder.setTitle("New Cost");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CostBean costBean=new CostBean();
                        costBean.costTitle=title.getText().toString();
                        costBean.costMoney=money.getText().toString();
                        costBean.costDate=date.getYear()+"-"+(date.getMonth()+1)+"-"+date.getDayOfMonth();
                        mdatabaseHelper.insertCost(costBean);
                        mCostBeanList.add(costBean);
                        mAdapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Cancel",null);//点击取消
                builder.create().show();//显示dialog的布局
            }
        });
    }

    private void initCostData() {
      // mdatabaseHelper.deleteAllData();
       /* CostBean costBean=new CostBean();
        for (int i=0;i<6;i++) {
            costBean.costTitle=i+"imooc";
            costBean.costDate="11-11";
            costBean.costMoney="20";
            mdatabaseHelper.insertCost(costBean);
        }*/
        Cursor cursor=mdatabaseHelper.getAllCostData();
       if(cursor!=null){
           while(cursor.moveToNext()){
               CostBean costBean1=new CostBean();
               costBean1.costTitle=cursor.getString(cursor.getColumnIndex("cost_title"));
               costBean1.costDate=cursor.getString(cursor.getColumnIndex("cost_date"));
               costBean1.costMoney=cursor.getString(cursor.getColumnIndex("cost_money"));
               mCostBeanList.add(costBean1);
           }
           cursor.close();
       }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_chart) {
            Intent intent=new Intent(MainActivity.this,ChartsActivity.class);
            intent.putExtra("cost_list", (Serializable) mCostBeanList);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
