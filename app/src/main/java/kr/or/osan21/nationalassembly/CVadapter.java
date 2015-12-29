package kr.or.osan21.nationalassembly;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeonghoon on 2015-12-29.
 */
public class CVadapter extends RecyclerView.Adapter<CVadapter.ViewHolder> {
    private static ArrayList<MediaClass> data;

    public CVadapter(ArrayList<MediaClass> medias) {
        data = medias;
    }

    @Override
    public CVadapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.media_card, null);
        ViewHolder vh = new ViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(CVadapter.ViewHolder vh, int i) {
        MediaClass mc = data.get(i);
        vh.title.setText(mc.getTitle());
        vh.content.setText(mc.getContent());
        vh.photo.setImageResource(mc.getPhoto());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView content;
        ImageView photo;

        public ViewHolder(View v) {
            super(v);
            title = (TextView)v.findViewById(R.id.media_title);
            content = (TextView)v.findViewById(R.id.media_content);
            photo = (ImageView)v.findViewById(R.id.media_photo);
        }

    }
}
