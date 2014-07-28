package com.tod.android.bths;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;

/**
 * Created by Margolin on 12/23/13.
 */
    public class MySimpleArrayAdapter extends ArrayAdapter<String> {
    String test;
    Element email;
        private final Context context;
        private final ArrayList values;
        private final String link;
        private final Document doc;
        public MySimpleArrayAdapter(Context context, ArrayList values, String link, Document doc) {
            super(context, R.layout.emailbrowsingcard, values);
            this.context = context;
            this.values = values;
            this.link = link;
            this.doc=doc;
        }
    static class ViewHolder {
        TextView textView;
        LinearLayout net;
        LinearLayout emailimage;
        View divider;
    }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ViewHolder holder;
            if (convertView == null) {

                convertView = inflater.inflate(R.layout.emailbrowsingcard, null);
                holder = new ViewHolder();
                holder.net = (LinearLayout) convertView.findViewById(R.id.internet);
                holder.divider=convertView.findViewById(R.id.dividerid);
                holder.textView = (TextView) convertView.findViewById(R.id.name);
                holder.emailimage = (LinearLayout) convertView.findViewById(R.id.email);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }
//            TextView textView = (TextView) rowView.findViewById(R.id.name);
            holder.textView.setText(values.get(position).toString());
//            ImageView net = (ImageView) rowView.findViewById(R.id.internet);
            test = holder.textView.getText().toString();
            email = doc.select("tr:contains("+ test + ")>td>a:contains(send)").last();
//            ImageView emailimage = (ImageView) rowView.findViewById(R.id.email);

            holder.net.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Element link = doc.select("a:contains(" + values.get(position).toString() + ")").first();
                    String absHref = link.attr("href");
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.bths.edu" + absHref));
                    browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(browserIntent);
                }
            });
            holder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), values.get(position).toString(),
                            Toast.LENGTH_SHORT).show();
                }
            });
            if (email != null){
                holder.emailimage.setVisibility(View.VISIBLE);
                holder.divider.setVisibility(View.VISIBLE);
            }else{
                holder.emailimage.setVisibility(View.GONE);
                holder.divider.setVisibility(View.GONE);
            }
            holder.emailimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    email = doc.select("tr:contains(" + values.get(position).toString() + ")>td>a:contains(send e-mail)").first();
                    String absHref = email.attr("href");
                    Intent intent = new Intent(context.getApplicationContext(), WebViewActivity.class);
                    intent.putExtra("LINK", "http://www.bths.edu" + absHref);
                    intent.putExtra("TITLE", values.get(position).toString());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });

            return convertView;
}}
