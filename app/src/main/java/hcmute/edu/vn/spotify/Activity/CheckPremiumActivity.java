package hcmute.edu.vn.spotify.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import hcmute.edu.vn.spotify.Database.DAOUser;
import hcmute.edu.vn.spotify.Model.Playlist;
import hcmute.edu.vn.spotify.Model.User;
import hcmute.edu.vn.spotify.R;
import hcmute.edu.vn.spotify.Service.ThreadSafeLazyUserSingleton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class CheckPremiumActivity extends AppCompatActivity {
    ImageView backIv;
    LinearLayout premiumLayout;
//    public static final String clientId = "AShNu9Lv170XmDbOIqIgiDbTgRYewZkdxavBi4_Jl7ysYl0eLw6GDt08vr1nvqgKC1XRMbevsdMPejGC";
//    public static final int PAYPAL_REQUEST_CODE = 123;
//    public static PayPalConfiguration configuration = new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
//            .clientId(clientId);
    String SECRET_KEY = "sk_test_51MtAeiCSMopNwrvZWKVeLY2TU2cAgPySWEUBN6jipHdLF2O97mFKiLFtKZ2fp00LMFRj1j00LiIcJuLYpV4T26CI00mRhTzQuw";
    String PUBLISH_KEY = "pk_test_51MtAeiCSMopNwrvZPCmtwT3hny4sD51zHBQg13kYWn07NLetyq0iOFMAVyor6cYzWoNPQMjb14I7KFawxSbHEC6v00fXcEhd66";
    PaymentSheet paymentSheet;
    String customerID;
    String EphericalKey;
    String ClientSecret;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_premium_check);
        // Mapping the view
        backIv = (ImageView) findViewById(R.id.activityPremiumCheck_backIv);
        premiumLayout = (LinearLayout) findViewById((R.id.premiumLayout));
        Toast.makeText(CheckPremiumActivity.this, "qwer", Toast.LENGTH_SHORT).show();

        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get back to previous activity
                finish();
            }
        });



        PaymentConfiguration.init(this, PUBLISH_KEY);
        paymentSheet = new PaymentSheet(this, paymentSheetResult -> {
            onPaymentResult(paymentSheetResult);
        });

        premiumLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://api.stripe.com/v1/customers", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            customerID=object.getString("id");
                            Toast.makeText(CheckPremiumActivity.this, customerID, Toast.LENGTH_SHORT).show();
                            Toast.makeText(CheckPremiumActivity.this, "2", Toast.LENGTH_SHORT).show();

                            getEphericalKey(customerID);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> header = new HashMap<>();
                        header.put("Authorization","Bearer "+SECRET_KEY);
                        return header;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(CheckPremiumActivity.this);
                requestQueue.add(stringRequest);

                //PaymentFlow();
            }
        });


    }

    private void onPaymentResult(PaymentSheetResult paymentSheetResult) {
        if(paymentSheetResult instanceof  PaymentSheetResult.Completed)
        {
            // Call singleton and get it value (global user)
            User user = new User();
            ThreadSafeLazyUserSingleton singleton = ThreadSafeLazyUserSingleton.getInstance(user);
            user = singleton.user;
            User finalUser = user;
            DAOUser daoUser = new DAOUser();
            finalUser.setPremium(true);
            finalUser.setEmail("qwe");
            finalUser.setName("Premium Member");
            daoUser.updateUser(finalUser).addOnSuccessListener(suc-> {
                Toast.makeText(CheckPremiumActivity.this, "Update user successfully!", Toast.LENGTH_SHORT).show();
            }).addOnFailureListener(er->{
                Toast.makeText(CheckPremiumActivity.this, er.getMessage(), Toast.LENGTH_SHORT).show();
            });
        }
    }

    private void getEphericalKey(String customerID) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://api.stripe.com/v1/ephemeral_keys", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    EphericalKey=object.getString("id");
                    Toast.makeText(CheckPremiumActivity.this, EphericalKey, Toast.LENGTH_SHORT).show();

                    getClientSecret(customerID, EphericalKey);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Authorization","Bearer "+SECRET_KEY);
                header.put("Stripe-Version","2022-11-15");
                return header;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("customer", customerID);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(CheckPremiumActivity.this);
        requestQueue.add(stringRequest);
    }

    private void getClientSecret(String customerID, String ephericalKey) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://api.stripe.com/v1/payment_intents", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    ClientSecret=object.getString("client_secret");
                    Toast.makeText(CheckPremiumActivity.this, ClientSecret, Toast.LENGTH_SHORT).show();
                    PaymentFlow();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Authorization","Bearer "+SECRET_KEY);

                return header;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("customer", customerID);
                params.put("amount", "1000"+"00");
                params.put("currency", "usd");
                params.put("automatic_payment_methods[enabled]", "true");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(CheckPremiumActivity.this);
        requestQueue.add(stringRequest);
    }

    private void PaymentFlow() {
        paymentSheet.presentWithPaymentIntent(
                ClientSecret, new PaymentSheet.Configuration("ABC Company", new PaymentSheet.CustomerConfiguration(
                        customerID,EphericalKey
                ))
        );
    }


//    private void getPayment() {
//        String amounts = "200";
//        PayPalPayment payment = new PayPalPayment(new BigDecimal(String.valueOf(amounts)), "USD", "Music", PayPalPayment.PAYMENT_INTENT_SALE);
//
//        Intent intent = new Intent(this, PaymentActivity.class);
//        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, configuration);
//        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
//        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode==PAYPAL_REQUEST_CODE)
//        {
//            PaymentConfirmation config = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
//            if(config!=null)
//            {
//                try {
//                    String paymentDetail = config.toJSONObject().toString(4);
//                    JSONObject payObj = new JSONObject(paymentDetail);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//
//                    Log.e("Error", "Something went wrong");
//
//                }
//            }
//        } else if (requestCode== Activity.RESULT_CANCELED)
//        {
//            Log.i("Error", "Something went wrong");
//        }zz
//        else if (requestCode == PaymentActivity.RESULT_EXTRAS_INVALID)
//        {
//            Log.i("Payment", "Invalid Payment");
//        }
  //  }

}
