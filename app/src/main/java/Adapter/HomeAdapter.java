package Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import JavaBean.Home;
import ma.wj.manageapp.R;

public class HomeAdapter extends ArrayAdapter<Home> implements Filterable {
    private int resourceId;
    public HomeAdapter(Context context, int textViewRes, List<Home> objs){
        super(context,textViewRes,objs);
        resourceId=textViewRes;
    }

    @SuppressLint("SetTextI18n")
    @Override
    @NonNull
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Home home=getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView== null){
            view=LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.homeAddress=view.findViewById(R.id.search_address);
            viewHolder.homeType=view.findViewById(R.id.search_type);
            viewHolder.homePic=view.findViewById(R.id.home_pic);
            viewHolder.homeUser=view.findViewById(R.id.search_username);
            viewHolder.homeCost=view.findViewById(R.id.home_cost);
            view.setTag(viewHolder);
        }else {
            view=convertView;
            viewHolder= (ViewHolder) view.getTag();
        }

        if (home!=null){
            viewHolder.homeAddress.setText("地址: "+home.getRoomAddress());
            viewHolder.homeType.setText("型号: "+home.getRoomType());
            viewHolder.homeUser.setText("户主: "+home.getRoomUser());
            viewHolder.homeCost.setText("物业费: "+home.getRoomCost()+"/月");
        }
        return view;

    }

    class ViewHolder{
        ImageView homePic;
        TextView homeAddress;
        TextView homeType;
        TextView homeUser;
        TextView homeCost;
    }
}
