import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.motomoapp.R

class creditCardActivity : AppCompatActivity() {

    private lateinit var backButton: Button
    private lateinit var cardNumber: EditText
    private lateinit var expiryMonth: EditText
    private lateinit var expiryYear: EditText
    private lateinit var amountCard: EditText
    private lateinit var limitCard: EditText
    private lateinit var continueButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_credit_card)

        backButton = findViewById(R.id.backActionButton)
        backButton.setOnClickListener {
            finish()
        }
    }
}
