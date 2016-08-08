package by.dmitry.exchange;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity implements View.OnClickListener {
    HTMLParser parser;
    Button btUpdate;
    ArrayList<Map<String, Object>> listBanks;

    RelativeLayout txPreload;
    ListView list;
    SimpleAdapter sa;

    String[] from = {"name", "address", "kurs0", "kurs1"};
    int[] to = {R.id.tx_name, R.id.tx_address, R.id.tx_kurs0, R.id.tx_kurs1};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        btUpdate = (Button) findViewById(R.id.bt_update);
        btUpdate.setOnClickListener(this);

        txPreload = (RelativeLayout) findViewById(R.id.preloader);
        txPreload.setVisibility(View.INVISIBLE);

        list = (ListView) findViewById(R.id.bank_list);
        listBanks = new ArrayList<Map<String, Object>>();

        parser = new HTMLParser();
        sa = new SimpleAdapter(this, listBanks, R.layout.list_item, from, to);
        list.setAdapter(sa);
        parser.execute();


    }

    void doParse() {
        parser = new HTMLParser();
        parser.execute();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_update:

                doParse();

                break;
        }
    }


    class HTMLParser extends AsyncTask<Void, Void, Void> {





        HTMLParser() {



        }

        @Override
        protected Void doInBackground(Void... voids) {
            getData();
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //if (listBanks != null) {
                listBanks.clear();
                sa.notifyDataSetChanged();
            //}
            txPreload.setVisibility(View.VISIBLE);

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            txPreload.setVisibility(View.INVISIBLE);
            sa.notifyDataSetChanged();
            if (listBanks.size() < 1) {
                Toast.makeText(getApplicationContext(), "Ошибка: Проверьте подключение к интернету.", Toast.LENGTH_LONG).show();
            }


        }

        void getData() {
            Map<String, Object> m;
          //  setHtml();


            Document docs;
            try {
                docs = Jsoup.connect("http://finance.tut.by/kurs/minsk/dollar/vse-banki/?sortBy=sell&sortDir=up").get();

                Log.d("log", "TITLE: " + docs.title().toString());

                Elements banks = docs.select("p.descr");


                Elements kurses = docs.select("div.w-currency_link");
                System.out.println("kurs " + kurses.size());

                Elements adresses = docs.select("p.address");
                System.out.println("adress " + adresses.size());


                for (int i = 0; i < banks.size(); i++) {
                    m = new HashMap<String, Object>();

                    m.put("name", banks.get(i).text().toString());
                    m.put("address", adresses.get(i).text().toString());

/*
                    if (i == 0) {

                        m.put("kurs0", kurses.get(i).text().toString());
                        m.put("kurs1", kurses.get(i + 1).text().toString());
*/

//                    } else {
                        m.put("kurs1", kurses.get(2 * i + 1).text().toString());
                        m.put("kurs0", kurses.get(2 * i).text().toString());
//                    }

                    listBanks.add(m);

                }

            } catch (IOException e) {
                e.printStackTrace();
                Log.d("log", "Error");



            }

        }


        ArrayList<Map<String, Object>> getList() {
            return listBanks;
        }

    }
}
