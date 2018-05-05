package com.example.merve.findcity;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Random;
import java.util.jar.Pack200;

public class MainActivity extends AppCompatActivity {
   String[] sehirler=new String[] {"ADANA","KONYA","ADIYAMAN","KÜTAHYA","AFYONKARAHİSAR","MALATYA","AĞRI",
            "MANİSA","AMASYA","KAHRAMANMARAŞ","ANKARA","MARDİN","ANTALYA","MUĞLA","ARTVİN","MUŞ","AYDIN","NEVŞEHİR",
            "BALIKESİR","NİĞDE","BİLECİK","ORDU","BİNGÖL","RİZE","BİTLİS","SAKARYA","BOLU","SAMSUN","BURDUR","SİİRT",
            "BURSA","SİNOP","ÇANAKKALE","SİVAS","ÇANKIRI","TEKİRDAĞ","ÇORUM","TOKAT","DENİZLİ","TRABZON","DİYARBAKIR",
            "TUNCELİ","EDİRNE","ŞANLIURFA","ELAZIĞ","UŞAK","ERZİNCAN","VAN","ERZURUM","YOZGAT","ESKİŞEHİR","ZONGULDAK",
            "GAZİANTEP","AKSARAY","GİRESUN","BAYBURT","GÜMÜŞHANE","KARAMAN","HAKKARİ","KIRIKKALE","HATAY","BATMAN",
            "ISPARTA","ŞIRNAK","MERSİN","BARTIN","İSTANBUL","ARDAHAN","İZMİR","IĞDIR","KARS","YALOVA","KASTAMONU",
            "KARABÜK","KAYSERİ","KİLİS","KIRKLARELİ","OSMANİYE","KIRŞEHİR","DÜZCE","KOCAELİ"};
    String secilenSehir="";
    LinearLayout llSehir;
    int ekranGenisligi=0;
    TextView tvDenemeSayisi,tvPuan;
    int maksimumHarfDenemeSayisi=0;
    int puan=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvDenemeSayisi= (TextView) findViewById(R.id.tvHarfSInırı);
        tvPuan= (TextView) findViewById(R.id.tvPuan);
        ekranGenisligi=Resources.getSystem().getDisplayMetrics().widthPixels;
        llSehir= (LinearLayout) findViewById(R.id.llSehir);

        sehirSec();

    }
    public void sehirSec()
    {
        Random rnd=new Random();
        int index=rnd.nextInt(sehirler.length);
        secilenSehir=sehirler[index];

        llSehir.removeAllViews();

        for (int i=0;i<secilenSehir.length();i++)
        {
            TextView textView= new TextView(this);
            //textView.setHeight(dpToPx(20));
            //textView.setWidth(dpToPx(20));
            textView.setPadding(5,0,5,0);
            textView.setTextColor(Color.MAGENTA);
            textView.setTextSize(24);
            textView.setText("_");
            llSehir.addView(textView);
        }
        butonYerlestir();
        maksimumHarfDenemeSayisi=10+new Random().nextInt(10);
        tvDenemeSayisi.setText("Kalan Harf Hakkınız"+String.valueOf(maksimumHarfDenemeSayisi));

    }
    public void butonYerlestir()
    {
        TableLayout tlButon= (TableLayout) findViewById(R.id.tlButton);
        tlButon.removeAllViews();
        String[] alfabe = new String[]{"A","B","C","Ç","D","E","F","G","Ğ","H","I","İ","J","K","L","M","N","O","Ö","P","R","S","Ş","T","U","Ü","V","Y","Z"};
        TableRow tableRow=new TableRow(this);
        int genislik=(ekranGenisligi/6)-2;
        for(int i=0;i<alfabe.length;i++) {
            if(i%6==0)
            {
                tableRow=new TableRow(this);
                tlButon.addView(tableRow);
            }

            final  Button btn = new Button(this);
            btn.setText(alfabe[i]);
            tableRow.addView(btn);
            final ViewGroup.LayoutParams params=btn.getLayoutParams();
            params.width=genislik;
            params.height=genislik;
            btn.setLayoutParams(params);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    maksimumHarfDenemeSayisi--;
                    if(maksimumHarfDenemeSayisi<=0)
                        return;
                        //TODO Oyun Bitti

                    String basilanHarf=btn.getText().toString();
                    int bilinenHarfSayisi=0;
                    for (int j=0;j<secilenSehir.length();j++)
                    {
                        if(secilenSehir.charAt(j)== basilanHarf.charAt(0))
                        {
                            TextView tv= (TextView) llSehir.getChildAt(j);
                            tv.setText(basilanHarf);
                            bilinenHarfSayisi++;
                        }
                    }
                    if(bilinenHarfSayisi>0)
                    {
                        puan += bilinenHarfSayisi*10;
                    }
                    else
                    {
                        puan -= 5;
                    }
                    if(puan<0)
                    {
                        puan=0;
                    }
                    tvPuan.setText("Puan Durumu:"+puan);
                    btn.setEnabled(false);

                    tvDenemeSayisi.setText("Kalan Harf Hakkınız:"+String.valueOf(maksimumHarfDenemeSayisi));
                }
            });
        }


    }
    public int dpToPx(float dp)
    {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,getResources().getDisplayMetrics());
    }

    public void sonrakiSehir(View view) {
        sehirSec();
    }

    public void tahminEt(View view) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Tahmini Giriniz");
        final EditText etTahmin=new EditText(this);
        String tahminEdilen="";
        for (int i=0;i<llSehir.getChildCount();i++)
        {
            tahminEdilen+=((TextView) llSehir.getChildAt(i)).getText().toString();
        }
        etTahmin.setHint(tahminEdilen);
        etTahmin.setInputType(InputType.TYPE_CLASS_TEXT| InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);

        builder.setView(etTahmin);
        builder.setNegativeButton("İPTAL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setPositiveButton("TAMAM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(etTahmin.getText().toString().equals(secilenSehir))
                {
                    int bilinmeyenHarfSayisi=0;
                    for (int j=0;j<llSehir.getChildCount();j++)
                    {
                        TextView textView= ((TextView) llSehir.getChildAt(j));
                        if(textView.getText().equals("_"))
                            bilinmeyenHarfSayisi++;
                       textView.setText(Character.toString( secilenSehir.charAt(j)));
                    }
                    puan+=bilinmeyenHarfSayisi*30;
                }
                else
                {
                    puan=-50;
                }
                tvPuan.setText("Puan Durumu:"+puan);
            }
        });
        builder.show();
    }
}
