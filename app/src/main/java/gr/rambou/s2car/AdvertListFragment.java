package gr.rambou.s2car;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AdvertListFragment extends Fragment {

    List<Advert> adverts;
    RecyclerView recyclerView;

    public AdvertListFragment() {

    }

    public AdvertListFragment(List<Advert> adverts) {
        this.adverts = adverts;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView rv = (RecyclerView) inflater.inflate(
                R.layout.fragment_cheese_list, container, false);
        setupRecyclerView(rv);
        this.recyclerView = rv;
        return rv;
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(new SimpleStringRecyclerViewAdapter(getActivity(),
                adverts));
    }

    public void refreshRecyclerView(List<Advert> listRefreshed) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(new SimpleStringRecyclerViewAdapter(getActivity(),
                listRefreshed));
    }

    // TODO: 10/1/2016 Remove 
    private List<String> getRandomSublist(String[] array, int amount) {
        ArrayList<String> list = new ArrayList<>(amount);
        Random random = new Random();
        while (list.size() < amount) {
            list.add(array[random.nextInt(array.length)]);
        }
        return list;
    }

    public static class SimpleStringRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleStringRecyclerViewAdapter.ViewHolder> {

        private final TypedValue mTypedValue = new TypedValue();
        private int mBackground;
        /**
         * List of adverts
         */
        private List<Advert> mValues;

        public SimpleStringRecyclerViewAdapter(Context context, List<Advert> items) {
            context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
            mBackground = mTypedValue.resourceId;
            mValues = items;
        }

        public Advert getValueAt(int position) {
            return mValues.get(position);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false);
            view.setBackgroundResource(mBackground);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mBoundString = mValues.get(position).getVehicleDescription();
            holder.mTextView.setText(mValues.get(position).getVehicleDescription());
            holder.mParseObjId.setText(mValues.get(position).getObjectId());
            holder.mIsFavorite.setText("false");

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String objId = ((TextView) v.findViewById(R.id.parse_obj_id)).getText().toString();
                    Context context = v.getContext();
                    Intent intent = new Intent(context, AdvertDetailActivity.class);

                    Bundle mBundle = new Bundle();
                    for (int i = 0; i < mValues.size(); i++) {
                        if (mValues.get(i).getObjectId().equals(objId)) {
                            mBundle.putString("VhlType", mValues.get(i).getVehicleType());
                            mBundle.putString("Brand", mValues.get(i).getVehicleBrand());
                            mBundle.putString("VhlModel", mValues.get(i).getVehicleModel());
                            mBundle.putString("VhlYear", mValues.get(i).getVehiclePurchaseYear());
                            mBundle.putString("Vhlkm", String.valueOf(mValues.get(i).getVehicleKm()));
                            mBundle.putString("Vhlcc", String.valueOf(mValues.get(i).getVehicleCc()));
                            mBundle.putString("Vhlbhp", String.valueOf(mValues.get(i).getVehicleHp()));
                            mBundle.putString("SpnFuel", mValues.get(i).getVehicleFuel());
                            mBundle.putString("SpnAdType", mValues.get(i).getAdvertType());
                            mBundle.putString("VhlPrice", mValues.get(i).getVehiclePrice());
                            mBundle.putString("VhlAdDescription", mValues.get(i).getVehicleDescription());
                            mBundle.putByteArray("Photo", mValues.get(i).getPhoto());
                            String temp[] = mValues.get(i).getLocation().split("\\|");
                            mBundle.putString("Latitude", mValues.get(i).getLocation().split("\\|")[0]);
                            mBundle.putString("Longitude", mValues.get(i).getLocation().split("\\|")[1]);
                            break;
                        }
                    }
                    intent.putExtras(mBundle);
                    context.startActivity(intent);
                }
            });

            holder.mImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageButton imgBtn = (ImageButton) v;

                    LinearLayout row = (LinearLayout) v.getParent();
                    String objId = ((TextView) row.findViewById(R.id.parse_obj_id)).getText().toString();
                    TextView isFavorite = ((TextView) row.findViewById(R.id.is_favorite));

                    if (isFavorite.getText().toString().equals("false")) {
                        imgBtn.setImageResource(R.drawable.star_checked);
                        isFavorite.setText("true");

                        for (int i = 0; i < mValues.size(); i++) {
                            if (mValues.get(i).getObjectId().equals(objId)) {
                                mValues.get(i).pinInBackground();
                                break;
                            }
                        }
                    } else {
                        isFavorite.setText("false");
                        imgBtn.setImageResource(R.drawable.star_unchecked);

                        for (int i = 0; i < mValues.size(); i++) {
                            if (mValues.get(i).getObjectId().equals(objId)) {
                                mValues.get(i).unpinInBackground();
                                break;
                            }
                        }
                    }
                }
            });

            Glide.with(holder.mImageView.getContext())
                    .load(mValues.get(position).getPhoto())
                    .fitCenter()
                    .into(holder.mImageView);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final ImageView mImageView;
            public final TextView mTextView;
            public final ImageButton mImageButton;
            public final TextView mParseObjId;
            public final TextView mIsFavorite;
            public String mBoundString;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mImageView = (ImageView) view.findViewById(R.id.avatar);
                mTextView = (TextView) view.findViewById(android.R.id.text1);
                mImageButton = (ImageButton) view.findViewById(R.id.imgbtn_star);
                mParseObjId = (TextView) view.findViewById(R.id.parse_obj_id);
                mIsFavorite = (TextView) view.findViewById(R.id.is_favorite);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mTextView.getText();
            }
        }
    }
}
