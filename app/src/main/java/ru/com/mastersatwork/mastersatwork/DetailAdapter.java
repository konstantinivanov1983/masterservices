package ru.com.mastersatwork.mastersatwork;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

public class DetailAdapter extends BaseAdapter {

    private final ArrayList mData;


    public static class ViewHolder {
        TextView key;
        TextView value;
        ImageButton image;
    }

    public DetailAdapter(Map<String, String> map) {
        mData = new ArrayList();
        mData.addAll(map.entrySet());
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Map.Entry<String, String> getItem(int position) {
        return (Map.Entry) mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_item, parent, false);

            viewHolder.image = (ImageButton) convertView.findViewById(R.id.detail_item_image);
            viewHolder.key = (TextView) convertView.findViewById(R.id.detail_item_key);
            viewHolder.value = (TextView) convertView.findViewById(R.id.detail_item_value);
            viewHolder.image = (ImageButton) convertView.findViewById(R.id.detail_item_image);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Map.Entry<String, String> item = getItem(position);

        viewHolder.key.setText(item.getKey());
        viewHolder.value.setText(item.getValue());

        // Choosing pic:

        switch(item.getKey()) {
            case "Услуга": viewHolder.image.setImageResource(R.drawable.ic_work_black_36px);
                break;
            case "Стоимость": viewHolder.image.setImageResource(R.drawable.ic_payment_black_36px);
                break;
            case "Клиент": viewHolder.image.setImageResource(R.drawable.ic_perm_identity_black_36px);
                break;
            case "Адрес": viewHolder.image.setImageResource(R.drawable.ic_place_black_second_36px);
                break;
            case "Телефон": viewHolder.image.setImageResource(R.drawable.ic_phone_black_second_36px);
                break;
            case "Дата заказа": viewHolder.image.setImageResource(R.drawable.ic_date_range_black_36px);
                break;
            case "Комментарий": viewHolder.image.setImageResource(R.drawable.ic_comment_black_36px);
                break;
        }

        return convertView;
    }
}
