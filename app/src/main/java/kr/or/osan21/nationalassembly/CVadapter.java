package kr.or.osan21.nationalassembly;

import android.graphics.Typeface;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import kr.or.osan21.nationalassembly.Media.Media;
import kr.or.osan21.nationalassembly.Utils.CustomFont;

/**
 * Created by Jeonghoon on 2015-12-29.
 */
public class CVadapter extends RecyclerView.Adapter<CVadapter.ViewHolder> {
    private List<Media> data;

    public CVadapter(List<Media> medias) {
        data = new ArrayList<Media>();
        Media a = new Media();
        a.setContent("국회 교육문화체육관광위원회 소속 새정치민주연합 안민석(오산)의원은 정부와 피감기관들의 잘못된 정책에 대해서는 절대 봐주거나 대충 넘어가는 법이 없다. 철저한 사전 국감 준비로 인해 피감기관 관계자들보다도 더 전문가답다는 평가를 받다보니 야당 의원 가운데서도 가장 두려워하는 의원으로 꼽힌다");
        a.setTitle("[국감인물] 안민석 교육문화체육관광위 새정치민주연합 의원");
        data.add(a);
        data.add(a);
        //data = medias;
    }

    @Override
    public CVadapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.media_card, null);
        ViewHolder vh = new ViewHolder(itemView);
        Typeface tf = CustomFont.getCustomFont(viewGroup.getContext(), "hans");
        TextView title = (TextView)itemView.findViewById(R.id.media_title);
        title.setTypeface(CustomFont.getCustomFont(viewGroup.getContext(), "CJKB"));
        TextView content = (TextView)itemView.findViewById(R.id.media_content);
        content.setTypeface(tf);
        return vh;
    }

    @Override
    public void onBindViewHolder(CVadapter.ViewHolder vh, int i) {
        Media mc = data.get(i);
        int len = mc.getTitle().length();
        String sub;
        if(len > 24)
        {
            sub = mc.getTitle().substring(0, 24) + "... " ;
        }
        else
            sub = mc.getTitle();
        vh.title.setText(sub);
        len = mc.getContent().length();
        Log.d("LENGTH", len+"");
        if(len > 165)
            sub = mc.getContent().substring(0, 165) + "... ";
        else
            sub = mc.getContent();
        vh.content.setText(sub);
        //vh.photo.setImageResource(mc.getPhoto());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView content;
        //ImageView photo;

        public ViewHolder(View v) {
            super(v);
            title = (TextView)v.findViewById(R.id.media_title);
            content = (TextView)v.findViewById(R.id.media_content);
            //photo = (ImageView)v.findViewById(R.id.media_photo);
        }

    }
}
