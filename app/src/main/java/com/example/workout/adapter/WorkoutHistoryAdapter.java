package com.example.workout.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.workout.R;
import com.example.workout.interfaces.IOnItemClickListener;
import com.example.workout.model.Workout;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

public class WorkoutHistoryAdapter extends RecyclerView.Adapter<WorkoutHistoryAdapter.ViewHolder> {

    private final List<Workout> workoutList;
    private final Context context;
    protected IOnItemClickListener onItemClickListener;

    public WorkoutHistoryAdapter(List<Workout> workoutList, Context context) {
        this.workoutList = workoutList;
        this.context = context;
    }

    @NonNull
    @Override
    public WorkoutHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.workout_history_recyclerview_element, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutHistoryAdapter.ViewHolder holder, int position) {
        holder.workoutNameTextView.setText(workoutList.get(position).getWorkoutName());
        holder.workoutDateTextView.setText(workoutList.get(position).getDateOfWorkout());
        Glide.with(context)
                .load(workoutList.get(position).getPhotoUri())
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.workoutImageImageView);
    }

    @Override
    public int getItemCount() {
        return workoutList.size();
    }

    public void setOnClickListener(IOnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView workoutNameTextView;
        private final TextView workoutDateTextView;
        private final CircularImageView workoutImageImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //initialize elements
            this.workoutDateTextView = itemView.findViewById(R.id.workout_history_recyclerview_date_textView);
            this.workoutImageImageView = itemView.findViewById(R.id.workout_history_recyclerview_image_imageView);
            this.workoutNameTextView = itemView.findViewById(R.id.workout_history_recyclerview_name_textView);

            itemView.setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        onItemClickListener.onItemClick(position);
                    }
                }
            });
        }
    }
}
