package com.example.SalesforceChatTester.UserItemDataFolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.SalesforceChatTester.DatabaseFolder.UserItemData;
import com.example.SalesforceChatTester.R;

import java.util.List;

public class UserItemDataAdapter extends ArrayAdapter<UserItemData> {
    public UserItemDataAdapter(@NonNull Context context, List<UserItemData> userItemDataList) {
        super(context, 0, userItemDataList);
    }

    public UserItemDataAdapter(@NonNull Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public UserItemDataAdapter(@NonNull Context context, int resource, @NonNull UserItemData[] objects) {
        super(context, resource, objects);
    }

    public UserItemDataAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull UserItemData[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public UserItemDataAdapter(@NonNull Context context, int resource, @NonNull List<UserItemData> objects) {
        super(context, resource, objects);
    }

    public UserItemDataAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<UserItemData> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        UserItemData org = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.user_data_item, parent, false);
        }
        TextView titulo = convertView.findViewById(R.id.userDataItemTitle);
        TextView itemId = convertView.findViewById(R.id.userDataItemId);
        titulo.setText(" - " + org.getObjectFieldName());
        itemId.setText(String.valueOf(org.getId()));

        return convertView;
    }
}
