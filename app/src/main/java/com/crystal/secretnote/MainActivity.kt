package com.crystal.secretnote

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.edit

class MainActivity : AppCompatActivity() {
    private val numberPicker1: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPicker1)
            .apply {
                minValue = 0
                maxValue = 9
            }
    }
    private val numberPicker2: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPicker2)
            .apply {
                minValue = 0
                maxValue = 9
            }
    }
    private val numberPicker3: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPicker3)
            .apply {
                minValue = 0
                maxValue = 9
            }
    }
    private val openButton: AppCompatButton by lazy {
        findViewById<AppCompatButton>(R.id.openButton)

    }
    private val changePWButton: AppCompatButton by lazy {
        findViewById<AppCompatButton>(R.id.changePasswordButton)
    }

    private var changePasswordMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numberPicker1
        numberPicker2
        numberPicker3

        openButton.setOnClickListener {
            if (changePasswordMode) {
                Toast.makeText(this, "비밀번호 변경중입니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val pwPreference = getSharedPreferences("password", Context.MODE_PRIVATE)
            val pwFromUser = "${numberPicker1.value}${numberPicker2.value}${numberPicker3.value}"
            if (pwPreference.getString("password", "000").equals(pwFromUser)) {
                //성공
                //TODO 다이어리 페이지 작성후에 넘겨주어야함
                startActivity(Intent(this, DiaryActivity::class.java))
            } else {
                //실패
                showErrorAlertDialog()
            }
        }

        changePWButton.setOnClickListener {
            val pwPreference = getSharedPreferences("password", Context.MODE_PRIVATE)
            val pwFromUser = "${numberPicker1.value}${numberPicker2.value}${numberPicker3.value}"

            if (changePasswordMode) {
                //번호를 저장하는 기능
                pwPreference.edit(true) {
                    putString("password", pwFromUser)
                }

                changePasswordMode = false
                changePWButton.setBackgroundColor(Color.BLACK)

            } else {
                //chagePassWordMode가 아닐때는 변경모드 활성화 :: 비밀번호가 맞는지 체크하고 변경한다

                if (pwPreference.getString("password", "000").equals(pwFromUser)) {
                    //성공
                    //change Pass Word Mode 활성화
                    changePasswordMode = true
                    Toast.makeText(this, "비밀번호 변경 모드 활성화", Toast.LENGTH_SHORT).show()
                    //버튼 색 변경
                    changePWButton.setBackgroundColor(Color.RED)
                } else {
                    //실패
                    showErrorAlertDialog()
                }
            }
        }
    }
    private fun showErrorAlertDialog(){
        AlertDialog.Builder(this)
            .setTitle("실패!")
            .setMessage("비밀번호가 일치하지 않습니다.")
            .setPositiveButton("확인", { _, _ -> })
            .create()
            .show()
    }
}
