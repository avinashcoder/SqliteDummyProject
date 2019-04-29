package com.xcelcorp.sqlitedummyproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



import java.util.List;

public class AdapterClass extends RecyclerView.Adapter<AdapterClass.Viewholder> {

    private Context mContext;
    private List<ModelClass> modelClasses;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public AdapterClass(Context context, List<ModelClass> modelClasses){
        mContext = context;
        this.modelClasses=modelClasses;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_layout,viewGroup,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder viewholder, int position) {

        String id=modelClasses.get(position).getId();
        String name=modelClasses.get(position).getName();
        String age=modelClasses.get(position).getAge();
        String contact=modelClasses.get(position).getContact();
        String email=modelClasses.get(position).getEmail();
        String address=modelClasses.get(position).getAddress();

        viewholder.setData(id,name,age,contact,email,address);

    }

    @Override
    public int getItemCount() {
        return modelClasses.size();
    }

    class Viewholder extends RecyclerView.ViewHolder {

        private TextView tvId,tvName,tvAge,tvContact,tvEmail,tvAddress;


        public Viewholder(@NonNull View itemView) {
            super(itemView);

            tvId = itemView.findViewById(R.id.item_layout_id);
            tvName = itemView.findViewById(R.id.item_layout_name);
            tvAge = itemView.findViewById(R.id.item_layout_age);
            tvContact = itemView.findViewById(R.id.item_layout_contact);
            tvEmail = itemView.findViewById(R.id.item_layout_email);
            tvAddress = itemView.findViewById(R.id.item_layout_address);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener !=null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }

        private void setData(String id, String name,  String age, String contact, String email, String address){

            tvId.setText(id);
            tvName.setText(name);
            tvAge.setText(age);
            tvContact.setText(contact);
            tvEmail.setText(email);
            tvAddress.setText(address);
        }
    }
}
