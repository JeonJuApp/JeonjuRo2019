package org.androidtown.jeonjuro2018;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {
    LayoutInflater inflater = null;
    private ArrayList<ItemData> m_oData = null;
    private int nListCnt = 0;

    public ListAdapter(ArrayList<ItemData> _oData)
    {
        m_oData = _oData;
        nListCnt = m_oData.size();
    }

    @Override
    public int getCount()
    {
        return nListCnt;
    }

    @Override
    public Object getItem(int position){
        return null;
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if(convertView == null)
        {
            final Context context = parent.getContext();
            if(inflater == null)
            {
                inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            convertView = inflater.inflate(R.layout.listview_item, parent, false);


        }

        TextView listTitle = (TextView)convertView.findViewById(R.id.listTitle);
        TextView listTitle2 = (TextView)convertView.findViewById(R.id.listTitle2);
        TextView listBus = (TextView)convertView.findViewById(R.id.listBusStop);
        TextView listBus2= (TextView)convertView.findViewById(R.id.listBusStop2);
        TextView listBusTime = (TextView)convertView.findViewById(R.id.timeText);

        listTitle.setText(m_oData.get(position).listTitle);
        listTitle2.setText(m_oData.get(position).listTitle2);
        listBusTime.setText(m_oData.get(position).listBusStopNum + " ("+m_oData.get(position).listBusTime + ")");
        listBus.setText(m_oData.get(position).listBus+" >");
        listBus2.setText(m_oData.get(position).listBus2+" >");

        return convertView;
    }

}