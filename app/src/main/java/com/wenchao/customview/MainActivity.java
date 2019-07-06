package com.wenchao.customview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wenchao.customview.explosion.ExplosionField;
import com.wenchao.customview.explosion.FallingParticleFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ExplosionField explosionField = new ExplosionField(this, new FallingParticleFactory());
        explosionField.addListener(findViewById(R.id.ll_body));
    }
}
