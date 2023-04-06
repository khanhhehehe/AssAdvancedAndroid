package khanhnqph27525.fpoly.assignment_nangcao.Activity_News;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import khanhnqph27525.fpoly.assignment_nangcao.MainActivity;
import khanhnqph27525.fpoly.assignment_nangcao.R;

public class ActivityNews extends AppCompatActivity {
    private ListView listView;
    private List<String> listLink = new ArrayList<>();
    private List<String> listTitle = new ArrayList<>();
    private ArrayAdapter<String> adapter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        listView = findViewById(R.id.listView_news);
        new KetNoiSeverEducation().execute("https://vnexpress.net/rss/giao-duc.rss");
    }
    public class KetNoiSeverEducation extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder builder = new StringBuilder();
            try {
                URL url = new URL(strings[0]);
                InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = "";
                while ((line=bufferedReader.readLine())!=null){
                    builder.append(line);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return builder.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            PhanTichXML ptxml = new PhanTichXML();
            try {
                Document document = ptxml.getDocument(s);
                NodeList nodeList = document.getElementsByTagName("item");
                String link = "";
                String title = "";
                for (int i=0;i<nodeList.getLength();i++){
                    Element element = (Element) nodeList.item(i);
                    title = ptxml.getValue(element,"title");
                    link = ptxml.getValue(element,"link");
                    listLink.add(link);
                    listTitle.add(title);
                }
                adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,listTitle);
                adapter.notifyDataSetChanged();
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(ActivityNews.this,DetailNews.class);
                        String link = listLink.get(position);
                        intent.putExtra("link",link);
                        startActivity(intent);
                    }
                });
            }catch (IOException e){
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }
        }
    }
}