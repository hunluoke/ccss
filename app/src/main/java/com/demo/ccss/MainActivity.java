package com.demo.ccss;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textNum;
    private Button button;
    private Button allButton;
    private Button personButton;
    private Button officeButton;
    private Button businessButton;
    private ListView listView;
    private List<User> list,newList;
    private UtilDao dao;
    private MyAdapter adapter;
    private int listNum = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化控件
        initWidget();
        //实例dao
        DbUtil();
        //显示ListView
        showListView();
        //显示listView的条目数量
        linkmanNum();
    }
    /**
     * 初始化控件
     * */
    private void initWidget(){
        button = findViewById(R.id.main_but);
        allButton = findViewById(R.id.all_button);
        personButton = findViewById(R.id.person_button);
        officeButton= findViewById(R.id.office_button);
        businessButton = findViewById(R.id.business_button);
        listView = findViewById(R.id.main_list_view);
        textNum = findViewById(R.id.main_num);
        newList = new ArrayList<>();
        list = new ArrayList<>();
    }

    /**
     * 显示ListView
     * */
    public void showListView(){
        //查询数据
        /**
         * 添加数据到链表中
         * **/
        list = dao.inquireData();

        /**
         * 创建并绑定适配器
         * */
        adapter = new MyAdapter(this,R.layout.item,list);
        listView.setAdapter(adapter);

        /**
         * ListView事件监听
         * */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dialogList();
                listNum = i;
            }
        });

        button.setOnClickListener(this);
        allButton.setOnClickListener(this);
        personButton.setOnClickListener(this);
        officeButton.setOnClickListener(this);
        businessButton.setOnClickListener(this);
    }

    /**
     * 普通对话框
     * */
    public void dialogNormal(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        DialogInterface.OnClickListener dialogOnClick = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                User userDel = list.get(listNum);
                switch (i){
                    case DialogInterface.BUTTON_POSITIVE:
                        dao.delData("userName=?",new String[]{userDel.getName()});
                        refresh();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                    default:break;
                }
            }
        };
        builder.setTitle("删除联系人");
        builder.setMessage("确定要删除吗？");
        builder.setPositiveButton("确定", dialogOnClick);
        builder.setNegativeButton("取消",dialogOnClick);
        builder.create().show();
    }

    /**
     * 选项列表
     * */
    public void dialogList(){
        final String[] items = {"拨打电话","发送短信","编辑","删除"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                //拿到当前选中项的 User 对象
                User userNum = list.get(listNum);
                Intent intent;
                switch (i){
                    //拨打电话
                    case 0: intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:" + userNum.getPhone()));
                        startActivity(intent);
                        break;
                    //发送短信
                    case 1: intent = new Intent(Intent.ACTION_SENDTO);
                        intent.setData(Uri.parse("smsto:" + userNum.getPhone()));
                        startActivity(intent);
                        break;
                    case 2: intent = new Intent(MainActivity.this,AddData.class);
                        //传入当前选中项的姓名和电话以在编辑页面中显示在输入框中
                        intent.putExtra("edit_name",userNum.getName().toString());
                        intent.putExtra("edit_sort",userNum.getSort().toString());
                        intent.putExtra("edit_phone",userNum.getPhone().toString());
                        startActivityForResult(intent,2);
                        break;
                    //弹出对话框提示是否删除
                    case 3: dialogNormal();
                        break;
                    default:
                        break;
                }
            }
        });
        builder.create().show();
    }

    //刷新
    public void refresh(){
        //最后查询数据刷新列表
        getNotifyData();
    }

    //页面顶部显示ListView条目数
    public void linkmanNum(){
        textNum.setText("("+list.size()+")");
    }

    //点击按钮
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.main_but:
                //跳转到 AddData Activity 传入请求码 1
                Intent intent = new Intent(MainActivity.this,AddData.class);
                startActivityForResult(intent,1);
                break;
            case R.id.all_button:
                //使用新的容器获得最新查询出来的数据
                newList = dao.inquireData();
                //清除原容器里的所有数据
                list.clear();
                //将新容器里的数据添加到原来容器里
                list.addAll(newList);
                //更新页面顶部括号里显示数据
                linkmanNum();
                //刷新适配器
                adapter.notifyDataSetChanged();
                break;
            case R.id.person_button:
                //使用新的容器获得最新查询出来的数据
                newList = dao.listBySort("个人类");
                //清除原容器里的所有数据
                list.clear();
                //将新容器里的数据添加到原来容器里
                list.addAll(newList);
                //更新页面顶部括号里显示数据
                linkmanNum();
                //刷新适配器
                adapter.notifyDataSetChanged();
                break;
            case R.id.office_button:
                //使用新的容器获得最新查询出来的数据
                newList = dao.listBySort("办公类");
                //清除原容器里的所有数据
                list.clear();
                //将新容器里的数据添加到原来容器里
                list.addAll(newList);
                //更新页面顶部括号里显示数据
                linkmanNum();
                //刷新适配器
                adapter.notifyDataSetChanged();
                break;
            case R.id.business_button:
                //使用新的容器获得最新查询出来的数据
                newList = dao.listBySort("商务类");
                //清除原容器里的所有数据
                list.clear();
                //将新容器里的数据添加到原来容器里
                list.addAll(newList);
                //更新页面顶部括号里显示数据
                linkmanNum();
                //刷新适配器
                adapter.notifyDataSetChanged();
                break;
            default:break;
        }
    }

    public void DbUtil(){
        dao = ((MyApplication)this.getApplication()).getDao();
    }

    /**
     * 当页面回到此活动时，调用此方法，刷新ListView
     * */
    @Override
    protected void onResume() {
        super.onResume();
        getNotifyData();
    }

    /**
     * 这个是用来动态刷新 * */
    public void getNotifyData(){
        //使用新的容器获得最新查询出来的数据
        newList = dao.inquireData();
        //清除原容器里的所有数据
        list.clear();
        //将新容器里的数据添加到原来容器里
        list.addAll(newList);
        //更新页面顶部括号里显示数据
        linkmanNum();
        //刷新适配器
        adapter.notifyDataSetChanged();
    }

    /**
     * 上一个页面传回来的值
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            //请求码为1，表示点击了添加按钮
            case 1:
                //执行添加方法
                if(resultCode == RESULT_OK){
                    String[] key = data.getStringArrayExtra("key");
                    String[] values = data.getStringArrayExtra("values");
                    List<User> users = dao.inquireData();
                    boolean i = true;
                    if (users.size() == 15) {
                        Toast.makeText(this,"联系人超过15人，存储空间已满，不能再录入新数据！",Toast.LENGTH_SHORT).show();
                        i = false;
                    }
                    for (User user : users) {
                        if (values[0].equals(user.getName()) || values[1].equals(user.getPhone())) {
                            i = false;
                            Toast.makeText(this,"此联系人已存在！",Toast.LENGTH_SHORT).show();
                        }
                    }
                    if (i)
                        dao.addData("UserInfo",key,values);
                }
                break;
            //请求码为2，表示点击了编辑按钮
            case 2:
                //执行修改方法
                if(resultCode == RESULT_OK){
                    User user = list.get(listNum);
                    String name = data.getStringExtra("name");
                    String phone = data.getStringExtra("phone");
                    String sort = data.getStringExtra("sort");
                    String[] values = {name,phone,sort,user.getName()};
                    System.out.println(name+phone+sort+"===============");
                    List<User> users = dao.inquireData();
                    boolean i = true;
                    for (User useri : users) {
                        if (values[0].equals(useri.getName()) || values[1].equals(useri.getPhone()))
                            if (!user.getName().equals(useri.getName()) || !user.getPhone().equals(useri.getPhone())) {
                                i = false;
                                Toast.makeText(this, "此联系人已存在！", Toast.LENGTH_SHORT).show();
                            }
                    }
                    if (i)
                        dao.update(values);
                }
                break;
        }
    }
}