package com.example.SalesforceChatTester.EntitiesFolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.SalesforceChatTester.DatabaseFolder.OrgEntity;
import com.example.SalesforceChatTester.R;

import java.util.List;

public class EntitiesAdapter extends ArrayAdapter<OrgEntity> {

    public EntitiesAdapter(@NonNull Context context, List<OrgEntity> entities) {
        super(context, 0, entities);
    }

    public EntitiesAdapter(@NonNull Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public EntitiesAdapter(@NonNull Context context, int resource, @NonNull OrgEntity[] objects) {
        super(context, resource, objects);
    }

    public EntitiesAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull OrgEntity[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public EntitiesAdapter(@NonNull Context context, int resource, @NonNull List<OrgEntity> objects) {
        super(context, resource, objects);
    }

    public EntitiesAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<OrgEntity> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        OrgEntity entity = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.entity_item, parent, false);
        }
        TextView titulo = convertView.findViewById(R.id.entityTitle);
        TextView itemId = convertView.findViewById(R.id.entityItemId);
        titulo.setText(" - " + entity.getObjectName());
        itemId.setText(String.valueOf(entity.getId()));

        return convertView;
    }
}
