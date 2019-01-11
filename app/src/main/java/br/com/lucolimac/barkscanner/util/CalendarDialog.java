//package br.com.lucolimac.barkscanner.util;
//
//import android.app.DatePickerDialog;
//import android.content.Context;
//import android.view.View;
//import android.widget.DatePicker;
//import android.widget.EditText;
//
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Locale;
//
//import br.com.lucolimac.barkscanner.cadastro.CadastroCachorro;
//
//public class CalendarDialog implements View.OnFocusChangeListener, DatePickerDialog.OnDateSetListener, View.OnClickListener {
//
//    private EditText editText;
//    private Calendar myCalendar;
//
//    public CalendarDialog(EditText editText, Context ctx) {
//        this.editText = editText;
//        this.editText.setOnFocusChangeListener(this);
//        myCalendar = Calendar.getInstance();
//    }
//
//    @Override
//    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//        // this.editText.setText();
//
//        String myFormat = "MMM dd, yyyy"; //In which you need put here
//        SimpleDateFormat sdformat = new SimpleDateFormat(myFormat, Locale.getDefault());
//        myCalendar.set(Calendar.YEAR, year);
//        myCalendar.set(Calendar.MONTH, monthOfYear);
//        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//
//        editText.setText(sdformat.format(myCalendar.getTime()));
//
//    }
//
//    @Override
//    public void onFocusChange(View v, boolean hasFocus) {
//        // TODO Auto-generated method stub
//        if (hasFocus) {
//            new DatePickerDialog(CadastroCachorro.class, this, myCalendar
//                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//        }
//    }
//
//    @Override
//    public void onClick(View v) {
//
//    }
//}