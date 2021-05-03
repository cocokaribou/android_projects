package com.example.beeradvisor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

public class FindBeerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_beer);
    }

    private BeerExpert expert = new BeerExpert();

    public void onClickFindBeer(View view) {
        TextView brands = (TextView) findViewById(R.id.brands); //xml의 TextView 요소를 가져온다 returns View, 적절한 view 유형으로 형변환해
        Spinner color = (Spinner) findViewById(R.id.color); //xml의 Spinner 요소를 가져온다
        String beerType = String.valueOf(color.getSelectedItem()); //Spinner의 선택된 요소를 문자열 변수에 대입 getSelectedItem() returns Object
        List<String> brandList = expert.getBrands(beerType);

        //brands.setText(brandList); //TextView를 Spinner 요소로 set한다..

        //근데 문자열 리스트를 텍스트뷰에 어떻게 전달하지?
        //->stringbuilder를 이용해서 문자열을 append한다


        StringBuilder brandsFormatted = new StringBuilder();//string을 조작하기 좋게 stringbuilder 선언
        for(String brand: brandList){
            brandsFormatted.append(brand).append('\n');
        }

        brands.setText(brandsFormatted);
    }
}