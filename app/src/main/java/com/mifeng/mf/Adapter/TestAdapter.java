package com.mifeng.mf.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mifeng.mf.Component.MFExpandableListView.ExpandableLayoutItem;
import com.mifeng.mf.MFNavigation.Utils.CompatUtils;
import com.mifeng.mf.MFTitleBar.Utils.ScreenUtils;
import com.mifeng.mf.MsgBean.TestBean;
import com.mifeng.mf.R;

import java.util.List;

public class TestAdapter extends BaseAdapter {


    List<TestBean> testBeans;
    private Context mContext;

    public TestAdapter(List<TestBean> testBeans, Context context) {
        this.testBeans = testBeans;
        mContext = context;
    }
    @Override
    public int getCount() {
        return testBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){

            TestBean testBean = testBeans.get(position);

            String parentString = testBean.getParent();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.view_row,parent,false);

            holder = new ViewHolder();
            holder.expandableLayoutItem = convertView.findViewById(R.id.row);
            if (position == 1) {
                holder.expandableLayoutItem.show();
            }
            holder.parent = convertView.findViewById(R.id.header_text);
            holder.parent.setText(parentString);

            holder.child = convertView.findViewById(R.id.child_base_view);

            addTextViewToChild(holder.child,testBean.getChild());

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }



    private class ViewHolder {
        TextView parent;
        RelativeLayout child;
        ExpandableLayoutItem expandableLayoutItem;
    }

    private void addTextViewToChild(ViewGroup parentView,List<String> childList) {
        int id = 0;


        LinearLayout linearLayout = new LinearLayout(mContext);


        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        linearLayout.setOrientation(LinearLayout.VERTICAL);



        for (int i = 0; i < childList.size(); i++) {
            View testView = LayoutInflater.from(mContext).inflate(R.layout.text_layout, linearLayout, false);
            TextView textViews = testView.findViewById(R.id.test_text);
            textViews.setText(childList.get(i));
            linearLayout.addView(testView);
        }
        parentView.addView(linearLayout);


    }
}
