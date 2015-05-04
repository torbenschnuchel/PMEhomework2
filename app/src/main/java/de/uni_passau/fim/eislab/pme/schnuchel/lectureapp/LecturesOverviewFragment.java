package de.uni_passau.fim.eislab.pme.schnuchel.lectureapp;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.Fragment;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class LecturesOverviewFragment extends Fragment {

    public static final String TAG = "LecturesOverview";

    //private List<Integer> mDrawableIDs;
    private List<String> mPaths;
    public LecturesOverviewFragment() {

        mPaths = new LinkedList<>();
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            String[] allAssetPaths = activity.getAssets().list("");
            for (String path : allAssetPaths) {
                if (path.endsWith(".pdf")) {

                    Log.d(TAG, "Found PDF: " + path);
                    mPaths.add(path);

                } else {

                    Log.w(TAG, "No PDF: " + path);
                }
            }

        } catch (IOException e) {

            e.printStackTrace();
        }

        /*
        try {
            mBitmaps = new LinkedList<>();
            PdfRenderer pdfRenderer = new PdfRenderer(activity.getAssets().openFd("Test1.pdf").getParcelFileDescriptor());
            PdfRenderer.Page page = pdfRenderer.openPage(0);
            Bitmap bitmap = Bitmap.createBitmap(page.getWidth(), page.getHeight(), Bitmap.Config.ARGB_8888);
            page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
            mBitmaps.add(bitmap);

            PdfRenderer pdfRenderer2 = new PdfRenderer(activity.getAssets().openFd("Test2.pdf").getParcelFileDescriptor());
            PdfRenderer.Page page2 = pdfRenderer2.openPage(1);
            Bitmap bitmap2 = Bitmap.createBitmap(page2.getWidth(), page2.getHeight(), Bitmap.Config.ARGB_8888);
            page2.render(bitmap2, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
            mBitmaps.add(bitmap2);

        } catch (IOException e) {
            e.printStackTrace();
        }
        */
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lectures_overview, container, false);

        GridView gridView = (GridView) rootView.findViewById(R.id.lecturesGridView);
        gridView.setAdapter(mAdapter);
        gridView.setOnItemClickListener(mOnItemClickListener);
        gridView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getBackground().setHotspot(event.getX(), event.getY());
                return false;
            };
        });

        return rootView;
    }

    private BaseAdapter mAdapter = new BaseAdapter() {
        @Override
        public boolean areAllItemsEnabled() {
            return true;
        }

        @Override
        public boolean isEnabled(int position) {
            return true;
        }

        @Override
        public void registerDataSetObserver(DataSetObserver observer) {

        }

        @Override
        public void unregisterDataSetObserver(DataSetObserver observer) {

        }

        @Override
        public int getCount() {
            return mPaths.size();
        }

        @Override
        public Object getItem(int position) {
            return mPaths.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {


            CardView cardView;
            if (convertView == null) {
                cardView = new CardView(getActivity());
            } else {
                cardView = (CardView) convertView;
            }

            cardView.setRadius(10);
            cardView.setCardElevation(10);

            String path = mPaths.get(position);
            try {

                PdfRenderer pdfRenderer = new PdfRenderer(getActivity().getAssets().openFd(path).getParcelFileDescriptor());
                PdfRenderer.Page page = pdfRenderer.openPage(0);
                Bitmap bitmap = Bitmap.createBitmap(page.getWidth(), page.getHeight(), Bitmap.Config.ARGB_8888);
                page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
                page.close();

                ImageView imageView = new ImageView(getActivity());
                imageView.setImageBitmap(bitmap);
                imageView.setAdjustViewBounds(true);
                cardView.addView(imageView);

            } catch (IOException e) {

                e.printStackTrace();
            }

            return cardView;
        }

        @Override
        public int getItemViewType(int position) {
            return 0;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public boolean isEmpty() {
            return mPaths.size() == 0;
        }
    };

    private AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Log.d(TAG, "Click: " + mPaths.get(position));
            Intent intent = new Intent(getActivity(), SlidesOverviewActivity.class);
            intent.putExtra("path", mPaths.get(position));

            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
        }
    };
}
