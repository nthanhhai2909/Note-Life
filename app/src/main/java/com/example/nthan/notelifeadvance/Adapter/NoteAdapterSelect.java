package com.example.nthan.notelifeadvance.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nthan.notelifeadvance.Model.Note;
import com.example.nthan.notelifeadvance.R;

import java.util.List;

/**
 * Created by nthan on 10/23/2017.
 */

public class NoteAdapterSelect extends RecyclerView.Adapter<NoteAdapterSelect.ViewHolder>{
    private RecyclerViewClickListener mListener;
    private List<Note> listNote;
    private Context context;

    public NoteAdapterSelect(List<Note> listNote, Context context,  RecyclerViewClickListener recyclerViewClickListener) {
        this.listNote = listNote;
        this.context = context;
        this.mListener = recyclerViewClickListener;
    }

    public interface RecyclerViewClickListener {

        void onClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private RecyclerViewClickListener mListener;
        TextView textViewTitle;
        TextView textViewContent;
        TextView textViewDateCreate;
        TextView textViewDateUpdateLast;
        ImageView image;

        public ViewHolder(View itemView, RecyclerViewClickListener listener) {
            super(itemView);

            image = (ImageView)itemView.findViewById(R.id.image_check);
            textViewTitle = (TextView)itemView.findViewById(R.id.textview_title_select);
            textViewContent = (TextView)itemView.findViewById(R.id.textview_content_select);
            textViewDateCreate = (TextView)itemView.findViewById(R.id.textview_datecreate_select);
            textViewDateUpdateLast = (TextView)itemView.findViewById(R.id.textview_datelastupdate_select);

            mListener = listener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            if((Integer)image.getTag() == R.drawable.uncheck){
                image.setTag(R.drawable.check);
                image.setImageResource(R.drawable.check);
            }
            else{
                image.setTag(R.drawable.uncheck);
                image.setImageResource(R.drawable.uncheck);
            }
            //notifyItemChanged(getAdapterPosition());
            mListener.onClick(v, getAdapterPosition());
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater  = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.row_recycleview_select_note, parent,false);
        return new NoteAdapterSelect.ViewHolder(itemView, mListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.image.setImageResource(R.drawable.uncheck);
        holder.image.setTag(R.drawable.uncheck);
        holder.textViewTitle.setText(listNote.get(position).getTitle());
        holder.textViewContent.setText(listNote.get(position).getContent());
        holder.textViewDateCreate.setText(listNote.get(position).getDate_create());
        holder.textViewDateUpdateLast.setText(listNote.get(position).getDate_Update_last());

        holder.image.setBackgroundColor(Color.parseColor(listNote.get(position).getColor()));
        holder.textViewTitle.setBackgroundColor(Color.parseColor(listNote.get(position).getColor()));
        holder.textViewContent.setBackgroundColor(Color.parseColor(listNote.get(position).getColor()));
        holder.textViewDateCreate.setBackgroundColor(Color.parseColor(listNote.get(position).getColor()));
        holder.textViewDateUpdateLast.setBackgroundColor(Color.parseColor(listNote.get(position).getColor()));
    }



    @Override
    public int getItemCount() {
        return listNote.size();
    }
}
