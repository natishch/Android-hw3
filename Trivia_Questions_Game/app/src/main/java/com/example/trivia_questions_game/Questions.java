package com.example.trivia_questions_game;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;
import java.util.regex.Pattern;


public class Questions extends AsyncTask<Void,Void,Void> {
    public Question[] questions;
    public int numOfQues;

    public Questions(int players_choice) {
        super();
        numOfQues = players_choice;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        questions = new Question[numOfQues];
        String ourUrl = "https://opentdb.com/api.php?amount="+String.valueOf(numOfQues)+"&type=multiple";
        Log.d(null, "doInBackground:"+ourUrl);
        String line = "";
        String apiAnswer = "";

        try{
            URL url = new URL(ourUrl);
            HttpURLConnection httpUC = (HttpURLConnection) url.openConnection();
            InputStream inStream = httpUC.getInputStream();
            BufferedReader buffReader = new BufferedReader(new InputStreamReader(inStream));

            while(line != null){
                line = buffReader.readLine();
                apiAnswer = apiAnswer + line;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            JSONObject jsonObj = new JSONObject(apiAnswer);
            JSONArray jsonArr = jsonObj.getJSONArray("results");

            int randNum,worngIndex;
            Random rand = new Random();
            String[] worngAns = new String[3];
            String correctAns;
            for(int index=0; index < jsonArr.length(); index++){
                String[] ansOptions = new String[4];
                JSONObject jsonQues = (JSONObject) jsonArr.get(index);
                String check = jsonQues.toString();
                Log.d(null,check);
                JSONArray incorrentArr = jsonQues.getJSONArray("incorrect_answers");

                for(int i=0; i < 3; i++){
                    worngAns[i] = incorrentArr.getString(i);
                }
                correctAns = (String) jsonQues.get("correct_answer");

                randNum = rand.nextInt(4);
                worngIndex = 0;
                for(int j=0; j < 4; j++){
                    if(j == randNum){
                        ansOptions[j] = correctAns;
                    } else {
                        ansOptions[j] = worngAns[worngIndex++];
                    }
                }

                questions[index] = new Question((String) jsonQues.get("question"),ansOptions,correctAns);
                Log.d(null,"questions"+index+": ques: "+questions[index].getTextQuestion());
                Log.d(null,"questions"+index+": ans1: "+questions[index].getAnswer(1));
                Log.d(null,"questions"+index+": ans2: "+questions[index].getAnswer(2));
                Log.d(null,"questions"+index+": ans3: "+questions[index].getAnswer(3));
                Log.d(null,"questions"+index+": ans4: "+questions[index].getAnswer(4));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    public Question[] getQuestions() {
        return questions;
    }
}
