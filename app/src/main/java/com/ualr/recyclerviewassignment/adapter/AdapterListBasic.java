package com.ualr.recyclerviewassignment.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ualr.recyclerviewassignment.R;
import com.ualr.recyclerviewassignment.model.Email;

import java.util.List;

public class AdapterListBasic extends RecyclerView.Adapter {

    private Context mContext;
    private List<Email> mEmails;

    private OnEmailClickListener mOnEmailClickListener;
    private OnDeleteClickListener mOnDeleteClickListener;

    private int selected_index;

    public void selectEmail(int position) {
        // clear prior selection if it exists
        if(selected_index >= 0 && selected_index < mEmails.size()){
            mEmails.get(selected_index).setSelected(false);
            this.notifyItemChanged(selected_index);
        }
        // set current selection
        selected_index=position;
        mEmails.get(selected_index).setSelected(true);
        this.notifyItemChanged(selected_index);
    }

    public interface OnEmailClickListener {
        void onEmailClick(int position);
    }
    public void setOnEmailClickListener(final OnEmailClickListener mEmailClickListener){
        this.mOnEmailClickListener = mEmailClickListener;
    }
    public interface OnDeleteClickListener {
        void onDeleteClick(int position);
    }
    public void setOnDeleteClickListener(final OnDeleteClickListener mDeleteClickListener){
        this.mOnDeleteClickListener = mDeleteClickListener;
    }

    public AdapterListBasic(Context context, List<Email> emails){
        mContext = context;
        mEmails = emails;
        selected_index = -1;
    }

    public void removeEmail(int position){
        if(position >= mEmails.size())
            return;
        if(position == selected_index)
            selected_index=-1;
        else if (position < selected_index)
            selected_index--;
        mEmails.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }

    public void addEmail(int position, Email email){
        mEmails.add(position, email);
        if(position <= selected_index)
            selected_index++;
        notifyItemInserted(position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.email_preview, parent, false);
        vh = new EmailViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        EmailViewHolder vh = (EmailViewHolder) holder;
        Email e = mEmails.get(position);
        vh.name.setText(e.getFrom());
        vh.date.setText(e.getDate());
        vh.content.setText(e.getMessage());
        vh.email.setText(e.getEmail());
        vh.initial.setText(e.getFrom().substring(0,1));
        if(e.isSelected()) {
            vh.initial.setVisibility(View.INVISIBLE);
            vh.delete_email.setVisibility(View.VISIBLE);
            vh.clickParent.setBackgroundColor(mContext.getResources().getColor(R.color.grey_20,null));
        }
        else {
            vh.initial.setVisibility(View.VISIBLE);
            vh.delete_email.setVisibility(View.INVISIBLE);
            vh.clickParent.setBackgroundColor(mContext.getResources().getColor(R.color.grey_3,null));
        }

    }

    @Override
    public int getItemCount() {
        return this.mEmails.size();
    }
    public class EmailViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView delete_email;
        public ImageView thumbnail;
        public TextView email;
        public TextView date;
        public TextView name;
        public TextView content;
        public TextView initial;
        public View clickParent;


        public EmailViewHolder(@NonNull View itemView) {
            super(itemView);
            delete_email=itemView.findViewById(R.id.delete_icon);
            thumbnail=itemView.findViewById(R.id.thumbnail);
            thumbnail.setOnClickListener(this);
            email = itemView.findViewById(R.id.email_address);
            date = itemView.findViewById(R.id.date);
            name = itemView.findViewById(R.id.name);
            content = itemView.findViewById(R.id.email_content);
            initial = itemView.findViewById(R.id.initial);
            clickParent = itemView.findViewById(R.id.preview_parent);
            clickParent.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view instanceof ImageView
                    && mEmails.get(getLayoutPosition()).isSelected())
                mOnDeleteClickListener.onDeleteClick(getLayoutPosition());
            else
                mOnEmailClickListener.onEmailClick(getLayoutPosition());
        }
    }
}
