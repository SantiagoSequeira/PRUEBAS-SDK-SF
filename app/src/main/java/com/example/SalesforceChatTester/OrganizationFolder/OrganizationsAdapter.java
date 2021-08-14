package com.example.SalesforceChatTester.OrganizationFolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.SalesforceChatTester.DatabaseFolder.Organization;
import com.example.SalesforceChatTester.R;

import java.util.List;

public class OrganizationsAdapter extends ArrayAdapter<Organization> {

    public OrganizationsAdapter(@NonNull Context context, List<Organization> organizations) {
        super(context, 0, organizations);
    }

    public OrganizationsAdapter(@NonNull Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public OrganizationsAdapter(@NonNull Context context, int resource, @NonNull Organization[] objects) {
        super(context, resource, objects);
    }

    public OrganizationsAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull Organization[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public OrganizationsAdapter(@NonNull Context context, int resource, @NonNull List<Organization> objects) {
        super(context, resource, objects);
    }

    public OrganizationsAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<Organization> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Organization org = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.organization_item, parent, false);
        }
        TextView titulo = convertView.findViewById(R.id.orgItemTitle);
        TextView itemId = convertView.findViewById(R.id.orgItemId);
        titulo.setText(" - " + org.getTitle());
        itemId.setText(String.valueOf(org.getId()));

        return convertView;
    }
}
