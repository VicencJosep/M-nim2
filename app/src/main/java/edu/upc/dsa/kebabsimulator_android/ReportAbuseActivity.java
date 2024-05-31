package edu.upc.dsa.kebabsimulator_android;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import edu.upc.dsa.kebabsimulator_android.databinding.ActivityReportAbuseBinding;
import edu.upc.dsa.kebabsimulator_android.models.API;
import edu.upc.dsa.kebabsimulator_android.models.AbuseReport;
import edu.upc.dsa.kebabsimulator_android.models.User;
import retrofit2.Call;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportAbuseActivity extends AppCompatActivity {

    private EditText abuseNameEditText;
    private EditText abuseDescriptionEditText;
    private Button sendReportButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_abuse);

        abuseNameEditText = findViewById(R.id.abuseNameEditText);
        abuseDescriptionEditText = findViewById(R.id.abuseDescriptionEditText);
        sendReportButton = findViewById(R.id.sendReportButton);

        sendReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String abuseName = abuseNameEditText.getText().toString();
                String abuseDescription = abuseDescriptionEditText.getText().toString();


                System.out.println("Nombre del abuso: " + abuseName);
                System.out.println("Descripción del abuso: " + abuseDescription);
                if(abuseNameEditText.getText().toString().isEmpty() || abuseDescriptionEditText.getText().toString().isEmpty() ){
                    Toast.makeText(ReportAbuseActivity.this, "Register Failed", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    AbuseReportAPIcall(abuseName, abuseDescription);
                }

            }
        });

    }
    public void AbuseReportAPIcall(String abuseName, String abuseDescription){

        AbuseReport report = new AbuseReport(abuseName, abuseDescription);
        API apiService = API.retrofit.create(API.class);
        Call<AbuseReport> call = apiService.reportAbuse(report);
        Toast.makeText(ReportAbuseActivity.this, "Sending report...", Toast.LENGTH_SHORT).show();
        call.enqueue(new Callback<AbuseReport>() {
            @Override
            public void onResponse(Call<AbuseReport> call, Response<AbuseReport> response) {
                if (response.isSuccessful()) {
                    Log.d("Success", "User added successfully");
                    Toast.makeText(ReportAbuseActivity.this, "Report sent successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Log.w("FAIL", "Failed to add user, HTTP " + response.code());
                    Toast.makeText(ReportAbuseActivity.this, "Report failed to send", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<AbuseReport> call, Throwable t) {
                Log.e("FAIL(onFailure)", "Error in Retrofit: " + t.toString());
            }
        });
    }
    

}