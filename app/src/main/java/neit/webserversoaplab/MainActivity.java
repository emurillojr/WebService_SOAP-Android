package neit.webserversoaplab;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParserException;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String SOAP_ACTION = "http://services.aonaware.com/webservices/Define"; /////
    private static final String METHOD_NAME = "Define"; //
    private static final String NAMESPACE = "http://services.aonaware.com/webservices/"; //
    private static final String URL = "http://services.aonaware.com/DictService/DictService.asmx";  /////

    TextView txtWord;
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        txtWord = (TextView)findViewById(R.id.txtWord);
        result = (TextView) findViewById(R.id.textView2);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class WebOperation  extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String resultOfCall = "test";
            try {

                String Word;


                Word = params[0];

                // This won't work because you need to pull components from the main thread
                //resultOfCall = GetWeatherInfo(City.getText().toString(), Country.getText().toString());
                resultOfCall = Define(Word);
            } catch (IOException e) {
                Log.e("Allied Error", "Foo didn't work: " + e.getMessage());
                Log.e("Allied Error2", "Full stack track:" + Log.getStackTraceString(e));
            } catch (XmlPullParserException e) {
                Log.e("Allied Error", "Foo didn't work: " + e.getMessage());
                Log.e("Allied Error2", "Full stack track:" + Log.getStackTraceString(e));
            }
            //    result.setText(resultOfCall);

            return resultOfCall;
        }
        protected void onPostExecute(String ResultOfCall) {
            result.setText(ResultOfCall);
        }
    }
    public void DefineButton(View view){
        // Use AsyncTask execute Method To Prevent ANR Problem
        String requestedWord;
        requestedWord = txtWord.getText().toString();
        new WebOperation().execute(requestedWord);

    }

    private String Define(String Word) throws IOException, XmlPullParserException{


        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

        PropertyInfo WordProp = new PropertyInfo();
        WordProp.type = PropertyInfo.STRING_CLASS;
        WordProp.name = "word";
        WordProp.setNamespace(NAMESPACE);
        WordProp.setValue(Word);
        request.addProperty(WordProp);



        // this works too
//		request.addProperty("AnnualInsurance",AnnualInsurance);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE ht;
        System.out.println(envelope.bodyOut.toString());
        ht = new HttpTransportSE(URL);

        ht.debug = true;
        ht.call(SOAP_ACTION, envelope);



        //final  SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
        SoapObject response = (SoapObject)envelope.getResponse();
        SoapObject def = (SoapObject)response.getProperty("Definitions");
        String o = new String();

        for(int i = 0; i<def.getPropertyCount(); i++){
            SoapObject dict = (SoapObject)def.getProperty(i);
            o += dict.getPrimitivePropertyAsString("Word") + "\n";
            o += dict.getPrimitivePropertyAsString("WordDefinition") + "\n";
        }


        //oapPrimitive response1 = (SoapPrimitive)response.getProperty(0);

//        String str = "Definition: " + String.format("%.2f", Float.parseFloat(MonthlyPrincipalAndInterest)) +
//                "\nMonthly Tax = " +String.format("%.2f", Float.parseFloat(MonthlyTax)) +
//                "\nMonthly Insurance = " + String.format("%.2f", Float.parseFloat(MonthlyInsurance)) +
//                "\nTotal Payment = " +String.format("%.2f", Float.parseFloat(TotalPayment));
        String str = o;

        //String str="";
        return str;
    }
}
