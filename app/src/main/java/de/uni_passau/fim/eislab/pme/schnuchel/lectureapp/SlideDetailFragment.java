package de.uni_passau.fim.eislab.pme.schnuchel.lectureapp;

import android.app.ActivityOptions;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.IOException;

/**
 * A placeholder fragment containing a simple view.
 */
public class SlideDetailFragment extends Fragment {

    private PdfRenderer mPDFPdfRenderer;
    private int mPageIndex;

    public SlideDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getActivity().getIntent().getExtras();
        String path = extras.getString("path");

        try {
            mPDFPdfRenderer = new PdfRenderer(getActivity().getAssets().openFd(path).getParcelFileDescriptor());
        } catch (IOException e) {
            e.printStackTrace();
        }

        mPageIndex = extras.getInt("pageIndex");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_slide_detail, container, false);

        PdfRenderer.Page page = mPDFPdfRenderer.openPage(mPageIndex);
        Bitmap bitmap = Bitmap.createBitmap(page.getWidth(), page.getHeight(), Bitmap.Config.ARGB_8888);
        page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
        page.close();

        ImageView imageView = (ImageView) rootView.findViewById(R.id.slideDetailImageView);
        imageView.setImageBitmap(bitmap);
        return rootView;
    }
}
