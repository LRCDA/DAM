    package dam_a51472.helloworld

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private  var signUpBtn: Button? = null
    private  var  emailEdt : EditText? = null
    private  var passwordEdt : EditText? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        println("Activity " + this@MainActivity.localClassName + " onCreate")
        println(getString(R.string.activity_oncreate_msg, this@MainActivity.localClassName));

        //Foi compreendido que é necessário ter cuidado com o layout, não se pode alterar todos os componentes só por estar em layouts diferentes!
        initView()
    }

    private fun initView() {
        signUpBtn = findViewById(R.id.button)
        emailEdt = findViewById(R.id.editTextEmail)
        passwordEdt = findViewById(R.id.editTextPassword)

        signUpBtn?.setOnClickListener {
            val email = emailEdt?.text?.toString()?.trim()?: ""
            val password = passwordEdt?.text?.toString()?.trim()?: ""

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText( //Toast: Mini mensagem do sistema
                    this,
                    "Please enter email and password",
                    Toast.LENGTH_SHORT
                ).show()

            } else {
                Toast.makeText(
                    this,
                    "Sign Up Successfully",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}