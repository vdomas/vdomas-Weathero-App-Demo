package com.gmail.vdomasapp.weathero.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gmail.vdomasapp.weathero.R;
import com.gmail.vdomasapp.weathero.model.dailyweather.DailyWeatherInfo;
import com.gmail.vdomasapp.weathero.utils.LogErrors;
import com.gmail.vdomasapp.weathero.utils.WeatherIcons;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context recyclerViewContext;
    private static List<DailyWeatherInfo> dailyWeatherInfoList;
    private OnCardViewListener onCardViewRecyclerListener;
    private Resources res;

    public RecyclerViewAdapter(Context context, List<DailyWeatherInfo> dailyWeatherInfoList, OnCardViewListener listener) {
        recyclerViewContext = context;
        RecyclerViewAdapter.dailyWeatherInfoList = dailyWeatherInfoList;
        this.onCardViewRecyclerListener = listener;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        recyclerViewContext = recyclerView.getContext();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        res = recyclerViewContext.getResources();
        return new ViewHolder(view, onCardViewRecyclerListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            DailyWeatherInfo dailyWeatherInfo = dailyWeatherInfoList.get(position);
            holder.tvItemDay.setText(String.valueOf(dailyWeatherInfoList.get(position).getFormattedDay()));

            int itemMaxTemperature = dailyWeatherInfoList.get(position).getDailyWeatherTemperature().getFormattedMaxTemperature();
            int itemMinTemperature = dailyWeatherInfoList.get(position).getDailyWeatherTemperature().getFormattedMinTemperature();
            holder.tvItemWeather.setText((res.getString(R.string.card_item_weather,itemMaxTemperature,itemMinTemperature)));

            Object currentWeatherIcon = WeatherIcons.weatherIconCollection().get(dailyWeatherInfo.getDailyWeatherIconList().get(0).getIcon());

            GlideApp.with(recyclerViewContext)
                    .load((Integer) currentWeatherIcon)
                    .into(holder.imgItemWeatherIcon);

        } catch (NullPointerException | IndexOutOfBoundsException e) {
            LogErrors.logException(e);
        }
    }

    @Override
    public int getItemCount() {
        return dailyWeatherInfoList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvItemDay,tvItemWeather;
        ImageView imgItemWeatherIcon;
        OnCardViewListener onCardViewHolderListener;

        public ViewHolder(@NonNull View itemView, OnCardViewListener listener) {
            super(itemView);

            this.onCardViewHolderListener = listener;
            itemView.setOnClickListener(this);
            imgItemWeatherIcon = itemView.findViewById(R.id.img_item_weather_icon);
            tvItemDay = itemView.findViewById(R.id.tv_item_day);
            tvItemWeather = itemView.findViewById(R.id.tv_item_weather);
        }

        @Override
        public void onClick(View v) {
            onCardViewHolderListener.onItemClick(itemView, getAdapterPosition(), dailyWeatherInfoList.get(getAdapterPosition()));
        }
    }

    public interface OnCardViewListener {
        void onItemClick(View child, int childPosition, DailyWeatherInfo dailyWeatherInfo);
    }
}
