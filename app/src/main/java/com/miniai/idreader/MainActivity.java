package com.miniai.idreader;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.miniai.idsdk.IdReader;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends Activity {

    private static final int REQUEST_CAMERA_PERMISSION = 100;
    private static final int REQUEST_GALLERY_PERMISSION = 101;
    private static final int REQUEST_CAMERA = 1;
    private static final int REQUEST_GALLERY = 2;

    public final String SCEN_FULL = "FullProcess";
    public final String SCEN_MRZ = "Mrz";
    public final String SCEN_BARCODE = "Barcode";
    public final String SCEN_LOCATE = "flF";
    public final String SCEN_OCR = "Ocr";
    public final String SCEN_DOCTYPE = "DocType";
    public final String SCEN_MRZ_BARCODE = "MrzOrBarcode";
    public final String SCEN_MRZ_LOCATE = "MrzOrLocate";
    public final String SCEN_MRZ_OCR = "MrzOrOcr";
    public final String SCEN_MRZ_OCR_BARCODE = "MrzOrBarcodeOrOcr";
    public final String SCEN_LOCATE_MRZ_OCR = "LocateVisual_And_MrzOrOcr";
    public final String SCEN_CREDIT = "CreditCard";
    public final String SCEN_ID3RUS = "Id3Rus";
    public final String SCEN_RUSSTAMP = "RusStamp";
    public final String SCEN_OCRFREE = "OcrFree";

    public String m_strScenario;
    public int PICK_IMAGE_REQUEST = 1;
    public boolean m_bProcessing = false;
    public TextView textViewError;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button_fullproc).setOnClickListener(v -> {
            if (m_bProcessing == true) return;
            showOptionsDialog(SCEN_FULL);
        });

        findViewById(R.id.button_barcode).setOnClickListener(v -> {
            if (m_bProcessing == true) return;
            showOptionsDialog(SCEN_BARCODE);
        });

        findViewById(R.id.button_mrz).setOnClickListener(v -> {
            if (m_bProcessing == true) return;
            showOptionsDialog(SCEN_MRZ);
        });

        findViewById(R.id.button_bank).setOnClickListener(v -> {
            if (m_bProcessing == true) return;
            showOptionsDialog(SCEN_CREDIT);
        });

        findViewById(R.id.button_About).setOnClickListener(v -> {
            startActivity(new Intent(this, AboutActivity.class));
        });

        textViewError = findViewById(R.id.text_error);
        textViewError.setVisibility(View.GONE);
    }

    private void showGallery(String scenario) {
        m_strScenario = scenario;
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    public void showScanner(String scenario) {
        m_strScenario = scenario;
        IdReader.GetInstance().from_camera(MainActivity.this, m_strScenario, completion);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            m_bProcessing = true;
            Uri selectedImageUri = data.getData();
            Bitmap imageData = getImageBitmap(selectedImageUri);
            IdReader.GetInstance().from_bitmap(this, imageData, m_strScenario, completion);
        }
    }

    private final IdReader.ICallbackAnaylisis  completion = new IdReader.ICallbackAnaylisis() {
        @Override
        public void OnSuccess() {
            textViewError.setText("");
            textViewError.setVisibility(View.GONE);

            Intent resultShowIntent = new Intent(MainActivity.this, ResultActivity.class);
            startActivity(resultShowIntent);
            m_bProcessing = false;
        }

        @Override
        public void OnFailed(String message) {
            textViewError.setText(message);
            textViewError.setVisibility(View.VISIBLE);
        }
    };

    private Bitmap getImageBitmap(Uri uri) {
        Bitmap bitmap = null;
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    private void showOptionsDialog(final String state) {
        Dialog cameraGalleryOptionsDialog = new Dialog(this, R.style.MediaPickerDialog);
        cameraGalleryOptionsDialog.setContentView(R.layout.media_picker_dialog_layout);

        // Set the gravity to the bottom
        Window window = cameraGalleryOptionsDialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.BOTTOM;

        // Set the width of the dialog to match the parent
        params.width = WindowManager.LayoutParams.MATCH_PARENT;

        // Add a bottom margin (adjust the value as needed)`
        params.verticalMargin = 0.1f; // 10% margin from the bottom

        // Apply the changes
        window.setAttributes(params);

        onCameraOptionClicked(cameraGalleryOptionsDialog, state);
        onGalleryOptionClicked(cameraGalleryOptionsDialog, state);
        dismissOnExternalTap(cameraGalleryOptionsDialog);
        cameraGalleryOptionsDialog.show();
    }
    private void onCameraOptionClicked(Dialog cameraGalleryOptionsDialog, final String state){
        // Handle Action for camera
        Button btnCamera = cameraGalleryOptionsDialog.findViewById(R.id.btn_camera);
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraGalleryOptionsDialog.dismiss();
                showScanner(state);
            }
        });
    }
    private void onGalleryOptionClicked(Dialog cameraGalleryOptionsDialog, final String state){
        // Handle Action for Gallery
        Button btnGallery = cameraGalleryOptionsDialog.findViewById(R.id.btn_gallery);
        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraGalleryOptionsDialog.dismiss();
                showGallery(state);
            }
        });
    }

    private void dismissOnExternalTap(Dialog cameraGalleryOptionsDialog){
        // Set touch listener to the cameraGalleryPopup layout of the custom popup
        View cameraGalleryPopup = cameraGalleryOptionsDialog.findViewById(R.id.camera_gallery_popup);
        cameraGalleryPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dismiss the dialog when the background is clicked
                cameraGalleryOptionsDialog.dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {
//        System.exit(0);

        super.onBackPressed(); // Call this to perform the default back action
    }
}