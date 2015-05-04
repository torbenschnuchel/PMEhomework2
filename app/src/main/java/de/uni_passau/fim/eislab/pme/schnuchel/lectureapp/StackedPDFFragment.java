package de.uni_passau.fim.eislab.pme.schnuchel.lectureapp;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class StackedPDFFragment extends Fragment {


    public StackedPDFFragment() {
        // Required empty public constructor
    }


    public void setBitmaps(Bitmap bitmap1,Bitmap bitmap2, Bitmap bitmap3) {

        ImageView imageView1 = (ImageView) getView().findViewById(R.id.imageView1);
        imageView1.setImageBitmap(bitmap1);
        ImageView imageView2 = (ImageView) getView().findViewById(R.id.imageView2);
        imageView2.setImageBitmap(bitmap2);
        ImageView imageView3 = (ImageView) getView().findViewById(R.id.imageView3);
        imageView3.setImageBitmap(bitmap3);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stacked_pdf, container, false);
    }


}
