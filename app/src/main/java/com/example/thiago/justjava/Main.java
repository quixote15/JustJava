package com.example.thiago.justjava;


        import android.content.Intent;
        import android.net.Uri;
        import android.os.Bundle;
        import android.support.v7.app.ActionBarActivity;
        import android.util.Log;
        import android.view.View;
        import android.widget.CheckBox;
        import android.widget.EditText;
        import android.widget.TextView;

        import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class Main extends ActionBarActivity {
    int quantity=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        CheckBox box = (CheckBox) findViewById(R.id.box_view);
        CheckBox chocolate = (CheckBox) findViewById(R.id.chocolate_box);
        EditText name = (EditText) findViewById(R.id.name_view);
        String priceMessage = "Name: "+name.getText()+"\n" +
                "Add Whipped Cream? ";
        priceMessage += box.isChecked() ? "Yes\n":"No\n";

        priceMessage+="Add Chocolate? ";
        priceMessage+= chocolate.isChecked() ? "Yes\n":"No\n";

        priceMessage+="Quantity: " + quantity + "\n";
        priceMessage+=displayTotal();

        ////////////////////////////////////////////////////////
        //Mailing
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(intent.EXTRA_SUBJECT,"JustJava Order for " + name.getText());
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }


    }

    /**
     *
     * @return String with the total price
     */
    private String displayTotal(){
        int toppings = 0;
        CheckBox box = (CheckBox) findViewById(R.id.box_view);
        CheckBox chocolate = (CheckBox) findViewById(R.id.chocolate_box);
        toppings+= box.isChecked() ? 1:0;
        toppings+=chocolate.isChecked() ? 2:0;
        String message = "\nTotal: R$" + calcularValor(quantity,toppings) + "\nThank You!";
        Log.i("Toppings", " " + toppings);
        return message;
    }

    private String calcularValor(int quant,int tops){

        return "" + quant * (tops+5);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


    /**
     *  This method increments the numbers of coffes when plus button is clicked
     *
     *  why the app crashes if i dont put the View Parameter ??????
     */
    public void increment(View view){
        quantity++;
        display(quantity);
    }


    /**
     *  This method decrements the numbers of coffes when minus button is clicked
     */
    public void decrement(View view){
        if(quantity>0)
        quantity--;
        display(quantity);
    }
}