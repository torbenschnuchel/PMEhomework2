package de.uni_passau.fim.eislab.pme.schnuchel.lectureapp;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.app.Fragment;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.lang.ref.SoftReference;

import de.svenjacobs.loremipsum.LoremIpsum;

/**
 * A placeholder fragment containing a simple view.
 */
public class SlidesOverViewFragment extends Fragment {

    private String mPath;
    private PdfRenderer mPDFPdfRenderer;

    public SlidesOverViewFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPath = getArguments().getString("path");
        try {
            mPDFPdfRenderer = new PdfRenderer(getActivity().getAssets().openFd(mPath).getParcelFileDescriptor());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_slides_overview, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.slidesListView);
        listView.setAdapter(mListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getActivity(), SlideDetailActivity.class);
                intent.putExtra("path", mPath);
                intent.putExtra("pageIndex", position);
                startActivity(intent);
            }
        });

        return rootView;
    }

    private ListAdapter mListAdapter;

    {
        mListAdapter = new ListAdapter() {
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
                return mPDFPdfRenderer.getPageCount();
            }

            @Override
            public Object getItem(int position) {
                return null;
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
            public View getView(int position, View convertView, ViewGroup parent) {

                View result = convertView;
                ViewHolder viewHolder = null;
                if (result == null) {

                    LayoutInflater inflater = getActivity().getLayoutInflater();

                    result = inflater.inflate(R.layout.listitem_slide_overview, parent, false);
                    viewHolder = new ViewHolder();
                    viewHolder.imageView = (ImageView) result.findViewById(R.id.listItemImageView);
                    viewHolder.textView = (TextView) result.findViewById(R.id.listItemTextView);

                    result.setTag(viewHolder);

                } else {

                    viewHolder = (ViewHolder) result.getTag();
                }

                PdfRenderer.Page page = mPDFPdfRenderer.openPage(position);
                Bitmap bitmap = Bitmap.createBitmap(page.getWidth(), page.getHeight(), Bitmap.Config.ARGB_8888);
                page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
                page.close();

                viewHolder.imageView.setImageBitmap(bitmap);
                viewHolder.imageView.setAdjustViewBounds(true);

                LoremIpsum loremIpsum = new LoremIpsum();
                viewHolder.textView.setText("Notiz: " + loremIpsum.getWords(4));

                return result;
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
                return false;
            }
        };
    }

    static class ViewHolder {
        ImageView imageView;
        TextView textView;
    }
}
