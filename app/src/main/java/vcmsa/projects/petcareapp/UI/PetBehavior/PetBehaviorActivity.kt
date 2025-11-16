package vcmsa.projects.petcareapp.UI.PetBehavior

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.NestedScrollView
import com.google.ai.client.generativeai.GenerativeModel
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import vcmsa.projects.petcareapp.R
import vcmsa.projects.petcareapp.UI.HealthRecord.HealthRecords
import vcmsa.projects.petcareapp.UI.Home.HomeActivity
import vcmsa.projects.petcareapp.UI.Profile.ProfileActivity
import java.text.SimpleDateFormat
import java.util.*

class PetBehaviorActivity : AppCompatActivity() {

    private lateinit var messageInput: EditText
    private lateinit var sendButton: ImageButton
    private lateinit var chatContainer: LinearLayout
    private lateinit var chatScrollView: NestedScrollView
    private lateinit var loadingProgress: ProgressBar
    private lateinit var hintText: TextView

    private val geminiApiKey = "AIzaSyCp0Pd3zd4cr4dp1i_zOIwvjub92riYSp4" // API key for Gemini model
    private val modelName = "gemini-2.0-flash" // Name of the generative model
    private lateinit var generativeModel: GenerativeModel

    // Firestore variables
    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private var currentChatSessionId: String = ""
    private var currentUserId: String = ""

    companion object {
        const val COLLECTION_CHAT_SESSIONS = "chat_sessions"
        const val COLLECTION_CHAT_MESSAGES = "chat_messages"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pet_behavior)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initializeFirebase()
        initializeViews()
        setupGeminiModel()
        setupClickListeners()
        setupNavigation()
        createNewChatSession()

        //  welcome message
        addBotMessage("Hello! I'm your Pet Health Assistant. I can help you with:\n\n• Vaccination schedules\n• Common pet illnesses\n• Diet and nutrition advice\n• Behavior issues\n• Grooming tips\n• Emergency care guidance\n\nWhat would you like to know about your pet's health?")
    }

    private fun initializeFirebase() {
        firestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        // Get current user ID (handle both authenticated and guest users)
        currentUserId = auth.currentUser?.uid ?: "guest_user_${UUID.randomUUID()}"
    }

    // Initialize UI components
    private fun initializeViews() {
        messageInput = findViewById(R.id.messageInput)
        sendButton = findViewById(R.id.sendButton)
        chatContainer = findViewById(R.id.chatContainer)
        chatScrollView = findViewById(R.id.chatScrollView)
        loadingProgress = findViewById(R.id.loadingProgress)
        hintText = findViewById(R.id.hintText)
    }

    //Set up the AI with your API key and choosen Model
    private fun setupGeminiModel() {
        generativeModel = GenerativeModel(
            modelName = modelName,
            apiKey = geminiApiKey
        )
    }

    private fun createNewChatSession() {
        currentChatSessionId = UUID.randomUUID().toString()

        val chatSession = hashMapOf(
            "sessionId" to currentChatSessionId,
            "userId" to currentUserId,
            "createdAt" to Timestamp.now(),
            "updatedAt" to Timestamp.now(),
            "title" to "New Pet Health Chat",
            "lastMessage" to "Session started",
            "messageCount" to 0,
            "sessionType" to "pet_health"
        )
        // Save the new chat session to Firestore
        firestore.collection(COLLECTION_CHAT_SESSIONS)
            .document(currentChatSessionId)
            .set(chatSession)
            .addOnSuccessListener {
                println("Chat session created successfully: $currentChatSessionId")
            }
            .addOnFailureListener { e ->
                println("Error creating chat session: ${e.message}")
            }
    }

    private fun setupClickListeners() {
        sendButton.setOnClickListener {
            val message = messageInput.text.toString().trim()
            if (message.isNotEmpty()) {
                sendMessage(message)
                messageInput.setText("")
            }
        }

        //  hint suggestions
        hintText.setOnClickListener {
            val hints = arrayOf(
                "What vaccinations does my puppy need?",
                "How often should I bathe my cat?",
                "My dog is scratching constantly, what could be wrong?",
                "What's a healthy diet for an adult cat?",
                "How can I stop my dog from barking too much?",
                "Is my pet overweight? How can I help them lose weight?",
                "What are common signs of illness in dogs?",
                "How much exercise does my cat need daily?"
            )
            val randomHint = hints.random()
            messageInput.setText(randomHint)
            messageInput.setSelection(randomHint.length)
        }

        // Send on enter key
        messageInput.setOnKeyListener { _, keyCode, event ->
            if (event.action == android.view.KeyEvent.ACTION_DOWN && keyCode == android.view.KeyEvent.KEYCODE_ENTER) {
                val message = messageInput.text.toString().trim()
                if (message.isNotEmpty()) {
                    sendMessage(message)
                    messageInput.setText("")
                    return@setOnKeyListener true
                }
            }
            false
        }
    }

    //Bottom Navigation
    private fun setupNavigation() {
        val homeNavItem = findViewById<LinearLayout>(R.id.HomeNavItem)
        val healthRecordsNavItem = findViewById<LinearLayout>(R.id.healthRecordsNavItem)
        val profileNavItem = findViewById<LinearLayout>(R.id.profile_nav_item)

        homeNavItem.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        healthRecordsNavItem.setOnClickListener {
            val intent = Intent(this, HealthRecords::class.java)
            startActivity(intent)
            finish()
        }

        profileNavItem.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    // Handles end-to-end message flow: user input → AI processing → response display & storage
    // Sends user message to AI and handles the response flow
// Updates UI, saves to database
    private fun sendMessage(message: String) {
        addUserMessage(message)
        saveMessageToFirestore(message, "user")
        showLoading(true)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = generativeModel.generateContent(message)
                withContext(Dispatchers.Main) {
                    showLoading(false)
                    val botResponse = response.text ?: "Sorry, I couldn't process your request."
                    addBotMessage(botResponse)
                    saveMessageToFirestore(botResponse, "bot")
                    updateChatSessionSummary(message, botResponse)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    showLoading(false)
                    val errorMessage = "Error: ${e.message}. Please check your connection and try again."
                    addBotMessage(errorMessage)
                    saveMessageToFirestore(errorMessage, "bot")
                    updateChatSessionSummary(message, errorMessage)
                }
            }
        }
    }



    // Saves a chat message to Firestore with a unique ID, sender information, and timestamp
    private fun saveMessageToFirestore(message: String, sender: String) {
        val messageId = UUID.randomUUID().toString()
        // Create a HashMap with message data structure for Firestore
        val chatMessage = hashMapOf(
            "messageId" to messageId,
            "sessionId" to currentChatSessionId,
            "userId" to currentUserId,
            "sender" to sender,
            "message" to message,
            "timestamp" to Timestamp.now(),
            "messageType" to "text"
        )
    // Saves the chat message to Firestore
        firestore.collection(COLLECTION_CHAT_MESSAGES)
            .document(messageId)
            .set(chatMessage)
            .addOnSuccessListener {
                println("Message saved successfully: $messageId")
            }
            .addOnFailureListener { e ->
                println("Error saving message: ${e.message}")

                Toast.makeText(this, "Failed to save message", Toast.LENGTH_SHORT).show()
            }
    }

    private fun updateChatSessionSummary(userMessage: String, botResponse: String) {
        val sessionRef = firestore.collection(COLLECTION_CHAT_SESSIONS).document(currentChatSessionId)

        firestore.runTransaction { transaction ->
            val snapshot = transaction.get(sessionRef)
            val currentCount = snapshot.getLong("messageCount") ?: 0
            val newCount = currentCount + 2 // User + Bot messages

            transaction.update(sessionRef, "messageCount", newCount)
            transaction.update(sessionRef, "lastMessage", userMessage.take(50) + if (userMessage.length > 50) "..." else "")
            transaction.update(sessionRef, "updatedAt", Timestamp.now())

            // Set title based on first user message if its still the default
            val currentTitle = snapshot.getString("title")
            if (currentTitle == "New Pet Health Chat" || currentTitle == "Session started") {
                val title = if (userMessage.length > 30) {
                    userMessage.take(30) + "..."
                } else {
                    userMessage
                }
                transaction.update(sessionRef, "title", title)
            }
        }.addOnSuccessListener {
            println("Chat session updated successfully")
        }.addOnFailureListener { e ->
            println("Error updating chat session: ${e.message}")
        }
    }

    private fun addUserMessage(message: String) {
        val messageLayout = layoutInflater.inflate(R.layout.item_user_message, chatContainer, false)
        val messageText = messageLayout.findViewById<TextView>(R.id.messageText)
        val timeText = messageLayout.findViewById<TextView>(R.id.timeText)

        messageText.text = message
        timeText.text = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())

        chatContainer.addView(messageLayout)
        scrollToBottom()
    }

    private fun addBotMessage(message: String) {
        val messageLayout = layoutInflater.inflate(R.layout.item_bot_message, chatContainer, false)
        val messageText = messageLayout.findViewById<TextView>(R.id.messageText)
        val timeText = messageLayout.findViewById<TextView>(R.id.timeText)

        messageText.text = message
        timeText.text = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())

        chatContainer.addView(messageLayout)
        scrollToBottom()
    }

    private fun scrollToBottom() {
        chatScrollView.post {
            chatScrollView.fullScroll(View.FOCUS_DOWN)
        }
    }

    private fun showLoading(show: Boolean) {
        loadingProgress.visibility = if (show) View.VISIBLE else View.GONE
        sendButton.isEnabled = !show
        if (show) {
            sendButton.alpha = 0.5f
        } else {
            sendButton.alpha = 1.0f
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Clean up if needed
    }
}
//REFERENCES
//Build an AI Chat App With Gemini (Android Tutorial). 2024. YouTube Video, added by Ahmed Guedmioui. [Online]. Available at: https://youtu.be/Xp0wRyO0a9g?si=tBqvk50tcXLsHOj6m [Accessed 07 November 2025].
//Google AI for Developers. (n.d.). Gemini Developer API. [Online]. Available at: https://ai.google.dev/gemini-api/docs [Accessed 07 November 2025].