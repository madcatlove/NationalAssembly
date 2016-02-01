package kr.or.osan21.nationalassembly;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.ArrayList;
import java.util.List;

import kr.or.osan21.nationalassembly.Media.Media;
import kr.or.osan21.nationalassembly.Media.MediaAPI;
import kr.or.osan21.nationalassembly.Utils.API;
import kr.or.osan21.nationalassembly.Utils.CustomFont;
import kr.or.osan21.nationalassembly.Utils.MediaImageView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MediaActivity extends AppCompatActivity {
    final String LOG_TAG = "MediaActivity";
    private ListView media_list;
    private CustomAdapter adapter;
    private MediaAPI api;
    private Typeface tf, tf2;
    private TextView bar_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);

        tf = CustomFont.getCustomFont(this, "hans");
        tf2 = CustomFont.getCustomFont(this, "CJKB");

        //init();

        bar_title = (TextView)findViewById(R.id.myImageViewText);
        bar_title.setTypeface(tf);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.BLACK);
        }

        media_list = (ListView)findViewById(R.id.media_list);
        media_list.setDivider(null);
        adapter = new CustomAdapter();
        media_list.setAdapter(adapter);

        //api호출
        api = new MediaAPI();
        api.getMediaList(new Callback<List<Media>>() {
            @Override
            public void success(List<Media> medias, Response response) {

                for(Media m : medias) {
                    Log.d(LOG_TAG, "" + m);
                }

                adapter.setMedias(medias);
                adapter.notifyDataSetInvalidated();
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

    }

    public void gotoback(View v) {
        finish();
    }

    private class CustomAdapter extends BaseAdapter {
        private List<Media> medias;

        public CustomAdapter() {
            medias = new ArrayList<Media>();
        }

        public void setMedias(List<Media> medias) {
            this.medias = medias;
        }

        public List<Media> getMedias() {
            return medias;
        }

        @Override
        public int getCount() {
            return medias.size();
        }

        @Override
        public Object getItem(int position) {
            return medias.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final int pos = position;
            final Context context = parent.getContext();
            MediaViewHolder holder;
            Media mediaItem = (Media) getItem(position);

            // 리스트가 길어지면서 현재 화면에 보이지 않는 아이템은 converView가 null인 상태로 들어 옴
            if (convertView == null) {
                // view가 null일 경우 커스텀 레이아웃을 얻어 옴
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.media_item_layout, parent, false);

                holder = new MediaViewHolder();

                holder.title = (TextView) convertView.findViewById(R.id.media_title);
                holder.content = (TextView) convertView.findViewById(R.id.media_content);
                holder.image = (ImageView) convertView.findViewById(R.id.media_image);

                convertView.setTag(holder);
            }
            else {
                holder = (MediaViewHolder)convertView.getTag();
            }


            holder.title.setTypeface(tf2);
            holder.content.setTypeface(tf);

            // 기사 제목 글자수제한
            String str_temp = "";
            if(medias.get(position).getTitle().length() > 23) {
                str_temp = medias.get(position).getTitle();
                if((str_temp.contains("'")&&str_temp.contains("'")) || (str_temp.contains("[")&&str_temp.contains("]")) || (str_temp.contains("(")&&str_temp.contains(")")))
                    str_temp = str_temp.substring(0, 24);
                else
                    str_temp = str_temp.substring(0, 23);
                str_temp += "..";
                holder.title.setText(str_temp);
            } else {
                holder.title.setText(medias.get(position).getTitle());
            }

            holder.content.setText(medias.get(position).getContent());

            Log.w(LOG_TAG, "getMediaImage :: " + mediaItem.getMedia_image());
            Log.w(LOG_TAG, "getMediaTitle :: " + mediaItem.getTitle());
            Log.w(LOG_TAG, "getMediaContent :: " + mediaItem.getContent());

            if( mediaItem.getMedia_image() != null) {
                Log.i(LOG_TAG, "--- IMAGE IS NOT NULL ");

                final String URL= API.UPLOAD_URL + mediaItem.getMedia_image();
                // 방법1 ) 카드뷰 크기 유동적, 이미지 세로크기 유동적 가로크기는 고정
                ((MediaImageView) holder.image).setViewReady(new MediaImageView.ViewReady() {
                    @Override
                    public void onViewReady(View v, int width, int height) {
                        Log.d(LOG_TAG, " URL :: " + URL);
                        Glide.with(MediaActivity.this)
                                .load(URL)
                                .asBitmap()
                                .into(new SImageTarget((ImageView) v));
                    }
                });

                /* 이미지뷰 재탕가능성이 있기 때문에 인벨리데이트 호출 */
                holder.image.invalidate();


//                Glide.with(MediaActivity.this)
//                        .load(API.UPLOAD_URL + medias.get(position).getMedia_image())
//                        .asBitmap()
//                        .transform(new MediaImageTransformation(MediaActivity.this))
//                        .into(holder.image);


                // 방법2 ) 카드뷰 크기는 동일, 이미지 가로, 세로 크기 동일 (이미지 잘리는 현상)
//                Glide.with(MediaActivity.this)
//                        .load(API.UPLOAD_URL + medias.get(position).getMedia_image())
//                        .centerCrop()
//                        .into(holder.image);

                // 방법3 ) 카드뷰 크기는 동일, 이미지가 크기 가로,세로 모두 유동적
                /*Glide.with(MediaActivity.this)
                        .load(API.UPLOAD_URL + medias.get(position).getMedia_image())
                        .fitCenter()
                        .into(holder.image);
                */
            }

            if(medias.get(position).getMedia_image() != null) {
                holder.content.setVisibility(View.GONE);
                holder.image.setVisibility(View.VISIBLE);
                //Uri.parse(medias.get(position).getMedia_image())
            }
            else {
                holder.image.setVisibility(View.GONE);
                holder.content.setVisibility(View.VISIBLE);
            }

            // 리스트 아이템을 터치 했을 때 이벤트 발생
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivityForResult(new Intent(getBaseContext(), MediaContentActivity.class).putExtra("m_id", medias.get(pos).getNum()), 1);
                }
            });
            return convertView;
        }
    }

    class MediaViewHolder {
        TextView title;
        TextView content;
        ImageView image;
    }

    class SImageTarget extends SimpleTarget<Bitmap> {

        MediaImageView imageView = null;
        private Bitmap sBitmap;

        public SImageTarget(ImageView v) {
            if(v instanceof MediaImageView) {
                imageView = (MediaImageView) v;
            }

        }
        @Override
        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
            Log.d(LOG_TAG, ">>>> sImage onResourceReady");
            int width = imageView.getViewWidth();
            int height = imageView.getViewHeight();
            int drawableWidth = resource.getWidth();
            int drawableHeight = resource.getHeight();


            Log.i(LOG_TAG, String.format("ImageView:%d,%d  BitmapDrawable:%d,%d", width, height, drawableWidth, drawableHeight));

            double imageRatio = (double) width / height;
            double drawableRatio = (double) drawableWidth / drawableHeight;

            // 새로운 너비(이미지뷰 너비와동일), 높이(비율계산)
            int nWidth = width;
            int nHeight = (int) ((drawableHeight * nWidth ) / (double) drawableWidth);

            Log.d(LOG_TAG, "nWidth : " + nWidth + " nHeight " + nHeight);
            if( !( nWidth > 0 && nHeight > 0 ) ) {
                Log.e(LOG_TAG, "sImage Error");
                return;
            }

            sBitmap = Bitmap.createScaledBitmap(resource, nWidth, nHeight, false);

            imageView.setMaxWidth(nWidth);
            imageView.setMaxHeight(nHeight);
            imageView.setLayoutParams( new LinearLayout.LayoutParams(nWidth, nHeight) );

            imageView.setImageBitmap(sBitmap);

        }

        @Override
        public void onDestroy() {
            super.onDestroy();

            Log.w(LOG_TAG, "SImageTarget>>> RELEASE BITMAP ");
            if( sBitmap != null) {
                sBitmap.recycle();
                sBitmap = null;
            }
        }

    }



}
