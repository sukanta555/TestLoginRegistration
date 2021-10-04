package com.example.testloginregistration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;

import org.json.JSONObject;

public class RazorPay extends AppCompatActivity implements PaymentResultWithDataListener {
    AppCompatButton payBtn;
    AppCompatEditText payAmt;
    ImageView hpage,lout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_razor_pay);
        Checkout.preload(getApplicationContext()); // Add razor pay link
        getSupportActionBar().hide();//title hide

        //Type casting
        payBtn=(AppCompatButton)findViewById(R.id.payBtn);
        payAmt=(AppCompatEditText)findViewById(R.id.payAmt);
        hpage=(ImageView)findViewById(R.id.hPage);
        lout=(ImageView)findViewById(R.id.lOut);
        //page called
        hpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent h=new Intent(getApplicationContext(),Dashboard.class);
                startActivity(h);
                Toast.makeText(RazorPay.this,"Welcome to Dashboard",Toast.LENGTH_SHORT).show();
            }
        });

        //logout
        lout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent l=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(l);
            }
        });
        //payment method process
        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (payAmt.getText().toString().equals("")){
                    Toast.makeText(RazorPay.this,"Please fill payment",Toast.LENGTH_SHORT).show();
                    return;
                }
                startPayment();

            }
        });
    }

    private void startPayment() {
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_g8KVN5dVQ8rFGi");
        checkout.setImage(R.drawable.razorpay);
        final Activity activity = this;
        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            options.put("name", "Madhumita Biswas");
            options.put("description", "Reference No. #123456");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            //options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            // amount value called
            String payment = payAmt.getText().toString(); // input amount convert to string
            double total = Double.parseDouble(payment);
            total = total * 100; // always amount multiply 100

            options.put("amount", total);//pass amount in currency subunits
            options.put("prefill.email", "gaurav.kumar@example.com");
            options.put("prefill.contact","7890067002");
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(activity, options);

        } catch(Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }
    }
    // Two method are generated when you input the - implements PaymentResultWithDataListener
    // and then onPaymentSuccess / onPaymentError generated

    @Override
    public void onPaymentSuccess(String s,PaymentData paymentData) {
        Toast.makeText(this,"Payment Successfully",Toast.LENGTH_SHORT).show();
       // Toast.makeText(this, "oid"+paymentData.getOrderId()+"pid"+paymentData.getPaymentId()+"user contact" +
               // paymentData.getUserContact()+"user email"+paymentData.getUserEmail()  , Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onPaymentError(int i,String s,PaymentData paymentData) {
       // payAmt.setText("Payment not successfully"+s);
        Toast.makeText(this,"Payment not Successfully",Toast.LENGTH_SHORT).show();
    }
}