package com.miniai.idreader;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class AboutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View aboutPage = new AboutPage(this)
                .setImage(R.drawable.logo_name)
                .setDescription("MiniAiLive is a provider of Touchless Biometrics Authentication, ID verification solutions. We offer strong security solutions with cutting-edge technologies for facial recognition, liveness detection, and ID document recognition. We also ensure that these solutions seamlessly integrate with our clients’ existing systems.\n")
                .addWebsite("https://www.miniai.live/")
                .addYoutube("UCU3D895D0XiF4TGy02GhN_Q")
                .addGitHub("MiniAiLive")
                .addEmail("info@miniai.live")
                .addItem(getCopyRightsElement())
                .create();

        setContentView(aboutPage);
    }

    private Element getCopyRightsElement() {
        Element copyRightsElement = new Element();
        String copyrights = String.format(getString(R.string.copy_right), Calendar.getInstance().get(Calendar.YEAR));
        copyRightsElement.setTitle(copyrights);
        copyRightsElement.setIconDrawable(R.drawable.about_icon_copy_right);
        copyRightsElement.setAutoApplyIconTint(true);
        copyRightsElement.setIconTint(mehdi.sakout.aboutpage.R.color.about_item_icon_color);
        copyRightsElement.setIconNightTint(android.R.color.white);
        copyRightsElement.setGravity(Gravity.CENTER);
        copyRightsElement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AboutActivity.this, copyrights, Toast.LENGTH_SHORT).show();
            }
        });
        return copyRightsElement;
    }
}
