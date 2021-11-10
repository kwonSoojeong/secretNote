package com.crystal.secretnote

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.widget.addTextChangedListener

class DiaryActivity : AppCompatActivity() {

    private val detail = "detail"
    private val diary = "diary"

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary)
        val diaryEditText = findViewById<EditText>(R.id.diaryEditText)
        val diaryPreferences = getSharedPreferences(diary, Context.MODE_PRIVATE)
        val diaryString = diaryPreferences.getString(detail, "")
        diaryEditText.setText(diaryString)

        //thread
        val runnable = Runnable {
            getSharedPreferences(diary, Context.MODE_PRIVATE).edit {
                //비동기적으로 apply를 사용하겟음
                putString(detail, diaryEditText.text.toString())
            }
        }

        diaryEditText.addTextChangedListener {
            handler.removeCallbacks(runnable) //runnable이 존재하면 삭제한다.
            handler.postDelayed(runnable,1000) // 1초뒤에 runnable을 실행한다.
            //add text changed 가 일어나지 않으면 remove가 불리지 않고, 5초뒤에 실행하게 된다.
        }

    }



}