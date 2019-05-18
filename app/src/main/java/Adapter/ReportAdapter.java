package Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.List;

import JavaBean.Report;
import ma.wj.manageapp.R;

public class ReportAdapter extends ArrayAdapter<Report> implements Filterable {
    private int resId;
    public ReportAdapter(Context context, int textViewRes, List<Report> objs){
        super(context,textViewRes,objs);
        resId=textViewRes;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Report report=getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView==null){
            view=LayoutInflater.from(getContext()).inflate(resId,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.reportContent=view.findViewById(R.id.report_content);
            viewHolder.reportTitle=view.findViewById(R.id.report_title);
            view.setTag(viewHolder);
        }else {
            view=convertView;
            viewHolder= (ViewHolder) view.getTag();
        }
        if (report!=null){
           viewHolder.reportTitle.setText("小区："+report.getReportType());
           viewHolder.reportContent.setText("内容：'\n    "+report.getReportCont());
        }
        return view;
    }

    class ViewHolder{
        TextView reportTitle;
        TextView reportContent;
    }
}
