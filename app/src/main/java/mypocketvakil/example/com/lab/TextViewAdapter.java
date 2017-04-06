package mypocketvakil.example.com.lab;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

/**
 * Created by sanyam jain on 23-11-2016.
 */
public class TextViewAdapter extends BaseAdapter {
    Context context;
    String[] name1;
    public TextViewAdapter(Context context, String[] name1) {
        this.context=context;
        this.name1=name1;

    }

    @Override
    public int getCount() {
        return name1.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tv;
        if (convertView == null) {
            tv = new TextView(context);
            tv.setLayoutParams(new GridView.LayoutParams(120, 120));
        }
        else {
            tv = (TextView) convertView;
        }

        tv.setText(name1[position]);
        return tv;
    }
}
