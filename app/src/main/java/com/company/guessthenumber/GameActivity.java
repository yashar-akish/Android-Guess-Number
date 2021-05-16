package com.company.guessthenumber;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    int remainingRight = 10;
    boolean twoDigits, threeDigits, fourDigits;
    Random r= new Random();
    int random, second;
    ArrayList<Integer> guessList = new ArrayList<>();
    int userAttempts = 0;
    private TextView tvLast, tvRight, tvHint;
    private Button btnConfirm;
    private EditText etGuess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        tvLast = findViewById(R.id.tvLast);
        tvRight = findViewById(R.id.tvRight);
        tvHint = findViewById(R.id.tvHint);
        btnConfirm = findViewById(R.id.btnConfirm);
        etGuess = findViewById(R.id.etGuess);

        twoDigits = getIntent().getBooleanExtra("two", false);
        threeDigits = getIntent().getBooleanExtra("three", false);
        fourDigits = getIntent().getBooleanExtra("four", false);

        if (twoDigits) {
            random = r.nextInt(90)+10;
        }
        if (threeDigits) {
            random = r.nextInt(900) + 100;
        }
        if (fourDigits) {
            random = r.nextInt(9000) + 1000;
        }

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String guess = etGuess.getText().toString();
                if (guess.equals("")) {
                    Toast.makeText(GameActivity.this, "Please enter a guess", Toast.LENGTH_LONG).show();
                } else {
                    tvLast.setVisibility(View.VISIBLE);
                    tvRight.setVisibility(View.VISIBLE);
                    tvHint.setVisibility(View.VISIBLE);

                    userAttempts++;
                    remainingRight--;
                    int userGuess = Integer.parseInt(guess);
                    guessList.add(userGuess);

                    tvLast.setText("Your last Guess is: " + guess);
                    tvRight.setText("Your remaining right is: " + remainingRight);
                    if (random == userGuess) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
                        builder.setTitle("Number Guessing Game");
                        builder.setCancelable(false);
                        builder.setMessage("Congratulation. My Guess was " + random +
                                "\n\n You know my number in " + userAttempts +
                                " attempts. \n\n Your guess : " + guessList +
                                "\n\n Would you like to play again?");
                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(GameActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                moveTaskToBack(true);
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                            }
                        });
                        builder.create().show();
                    }
                    if (random < userGuess) {
                        tvHint.setText("Decrease your guess");
                    }
                    if (random > userGuess) {
                        tvHint.setText("Increase your guess");
                    }

                    if (remainingRight <= 3) {
                        tvRight.setTextColor(Color.parseColor("#ff0000"));
                        tvRight.setTypeface(Typeface.DEFAULT_BOLD);
                    }
                    if (remainingRight == 0) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
                        builder.setTitle("Number Guessing Game");
                        builder.setCancelable(false);
                        builder.setMessage("Sorry, your right to guess is over!" +
                                "\n\n My Guess was " + random +
                                "\n\n Your guess : " + guessList +
                                "\n\n Would you like to play again?");
                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(GameActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                moveTaskToBack(true);
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                            }
                        });
                        builder.create().show();
                    }
                    etGuess.setText("");
                }
            }
        });

    }
}