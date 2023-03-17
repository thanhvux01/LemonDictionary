package com.example.lemondictionary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lemondictionary.API.APICALL;
import com.example.lemondictionary.Retrofit.RetrofitInstance;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TranslateActivity extends AppCompatActivity {
    Button btnCancel,btnConvert,btnOcancel;
    TableLayout tableLang,tableLangOutput;
    ImageView btnSelectLang,btnSelectLangOutPut;
    TextView Source,txtOutput;
    String target = "";
    String source = "";
    Button btnCn,btnJp,btnKr,btnEn;
    Button btnOCn,btnOJp,btnOKr,btnOEn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);

        btnCancel = findViewById(R.id.btnCancel);
        btnCn = findViewById(R.id.btnCn);
        btnJp = findViewById(R.id.btnJp);
        btnEn = findViewById(R.id.btnEn);
        btnOcancel = findViewById(R.id.btnOCancel);
        btnKr = findViewById(R.id.btnKr);
        btnOEn = findViewById(R.id.btnOEn);
        btnOKr = findViewById(R.id.btnOKr);
        btnOJp = findViewById(R.id.btnOJp);
        tableLangOutput = findViewById(R.id.TableLangOuput);
        btnSelectLangOutPut  = findViewById(R.id.btnSelectLangOutput);
        btnOCn = findViewById(R.id.btnOCn);
        btnSelectLang = findViewById(R.id.btnSelectLang);
        btnConvert = findViewById(R.id.btnConvert);
        tableLang = findViewById(R.id.TableLang);
        Source = findViewById(R.id.txtSource);
        txtOutput = findViewById(R.id.txtOutput);
        btnJp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                source = "ja";
                tableLang.setVisibility(View.GONE);
            }
        });
        btnCn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  source = "zh-CN";
                tableLang.setVisibility(View.GONE);
            }
        });
        btnEn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                source = "en";
                tableLang.setVisibility(View.GONE);
            }
        });
        btnKr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                source = "ko";
                tableLang.setVisibility(View.GONE);
            }
        });
        btnOEn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                target = "en";
                tableLangOutput.setVisibility(View.GONE);
            }
        });
        btnOKr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                target = "ko";
                tableLangOutput.setVisibility(View.GONE);
            }
        });
        btnOCn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                target = "zh-CN";
                tableLangOutput.setVisibility(View.GONE);
            }
        });
        btnOJp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                target = "ja";
                tableLangOutput.setVisibility(View.GONE);
            }
        });
        btnSelectLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    tableLang.setVisibility(View.VISIBLE);
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tableLang.setVisibility(View.GONE);
            }
        });
        btnOcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tableLangOutput.setVisibility(View.GONE);
            }
        });
        btnSelectLangOutPut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tableLangOutput.setVisibility(View.VISIBLE);
            }
        });
        btnConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                APICALL myApi = RetrofitInstance.Init();
                String text = Source.getText().toString();
                if(target.length()==0){
                    target = "vi";
                }
                if(source.length()==0){
                    source = "en";
                }
                Call<ResponseBody> call = myApi.GetTranslatedText(text,target,source);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.code()!=200){
                            Toast.makeText(TranslateActivity.this,"No response",Toast.LENGTH_LONG).show();
                        }
                        try {
                            String translatedText = response.body().string();
                            txtOutput.setText(translatedText);
                            Log.w(null,translatedText);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        t.printStackTrace();
                    }
                });


            }
        });

    }
}