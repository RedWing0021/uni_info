package com.example.uni_info;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class UniversityAdapter extends RecyclerView.Adapter<UniversityAdapter.ViewHolder> {

    private List<University> universityList;
    private OnWebPagesClickListener onWebPagesClickListener;

    public UniversityAdapter(List<University> universityList, OnWebPagesClickListener onWebPagesClickListener) {
        this.universityList = universityList;
        this.onWebPagesClickListener = onWebPagesClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_university, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        University university = universityList.get(position);
        holder.bind(university);
    }

    @Override
    public int getItemCount() {
        return universityList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textName;
        private TextView textCountry;
        private TextView textWebPages; // Updated variable name

        public ViewHolder(View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.textName);
            textCountry = itemView.findViewById(R.id.textCountry);
            textWebPages = itemView.findViewById(R.id.textWebPages); // Updated view ID
        }

        public void bind(final University university) {
            textName.setText(university.getName());
            textCountry.setText("Country: " + university.getCountry());

            // Set the website text
            List<String> webPages = university.getWebPages();
            if (webPages != null && !webPages.isEmpty()) {
                // Join multiple web pages into a comma-separated string
                String webPagesText = TextUtils.join(", ", webPages);
                textWebPages.setText(webPagesText);
            } else {
                textWebPages.setText("Website: N/A"); // Handle the case where no websites are available
            }


            // Set an OnClickListener to open the web pages in WebViewActivity when clicked
            textWebPages.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onWebPagesClickListener != null) {
                        onWebPagesClickListener.onWebPagesClick(webPages);
                    }
                }
            });
        }
    }

    // Callback interface for web pages link clicks
    public interface OnWebPagesClickListener {
        void onWebPagesClick(List<String> webPages);
    }
}
