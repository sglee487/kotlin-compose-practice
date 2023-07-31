import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

val lightThemeColors = lightColors(
    primary = Color.Blue,
    primaryVariant = Color.Blue,
    secondary = Color.Gray,
    secondaryVariant = Color.Gray,
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black
)

val darkThemeColors = darkColors(
    primary = Color.DarkGray,
    primaryVariant = Color.DarkGray,
    secondary = Color.LightGray,
    secondaryVariant = Color.LightGray,
    background = Color.Black,
    surface = Color.Black,
    onPrimary = Color.Black,
    onSecondary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White
)

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App(
    darkTheme: Boolean = isSystemInDarkTheme()
) {
    MaterialTheme(
        colors = if (darkTheme) darkThemeColors else lightThemeColors
    ) {
        Column {
            HelloButton()
            MessageCard(
                Message(
                    author = "sglee487", body = "I am sg0"
                )
            )
            PreviewConversation()
        }
    }
}

expect fun getPlatformName(): String


@Composable
fun Conversation(messages: List<Message>) {
    LazyColumn {
        items(messages.size) { index ->
            MessageCard(messages[index])
        }
    }
}

@Composable
fun PreviewConversation() {
    Conversation(SampleData.conversationSample)
}

data class Message(val author: String, val body: String)

@OptIn(ExperimentalResourceApi::class)
@Composable
fun HelloButton() {
    var greetingText by remember { mutableStateOf("Hello, World!") }
    var showImage by remember { mutableStateOf(false) }
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = {
            greetingText = "Hello, ${getPlatformName()}"
            showImage = !showImage
        }) {
            Text(greetingText)
        }
        TextField(greetingText, onValueChange = { greetingText = it })
        AnimatedVisibility(showImage) {
            Image(
                painterResource("compose-multiplatform.xml"), null
            )
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun MessageCard(msg: Message) {
    Row(modifier = Modifier.padding(all = 8.dp)) {
        Image(
            painter = painterResource("sglee487.jpg"),
            contentDescription = "Contact profile picture",
            modifier = Modifier
                // Set image size to 40 dp
                .size(40.dp)
                // Clip image to be shaped as a circle
                .clip(CircleShape).border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
        )

        // Add a horizontal space between the image and the column
        Spacer(modifier = Modifier.width(8.dp))

        // We keep track if the message is expanded or not in this
        // variable
        var isExpanded by remember { mutableStateOf(false) }
        // surfaceColor will be updated gradually from one color to the other
        val surfaceColor by animateColorAsState(
            if (isExpanded) MaterialTheme.colors.primary else MaterialTheme.colors.surface
        )

        // We toggle the isExpanded variable when we click on this Column
        Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) {
            Text(
                text = msg.author,
                color = MaterialTheme.colors.secondaryVariant,
                style = MaterialTheme.typography.subtitle2
            )
            // Add a vertical space between the author and message texts
            Spacer(modifier = Modifier.height(4.dp))

            Surface(
                shape = MaterialTheme.shapes.medium,
                elevation = 1.dp,
                // surfaceColor color will be changing gradually from primary to surface
                color = surfaceColor,
                // animateContentSize will change the Surface size gradually
                modifier = Modifier.animateContentSize().padding(1.dp)
            ) {
                Text(
                    text = msg.body,
                    modifier = Modifier.padding(all = 4.dp),
                    // If the message is expanded, we display all its content
                    // otherwise we only display the first line
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                    style = MaterialTheme.typography.body2
                )
            }

        }
    }
}


object SampleData {
    // Sample conversation data
    val conversationSample = listOf(
        Message(
            "Colleague", "Test...Test...Test..."
        ),
        Message(
            "Colleague",
            "List of Android versions:\n" + "Android KitKat (API 19)\n" + "Android Lollipop (API 21)\n" + "Android Marshmallow (API 23)\n" + "Android Nougat (API 24)\n" + "Android Oreo (API 26)\n" + "Android Pie (API 28)\n" + "Android 10 (API 29)\n" + "Android 11 (API 30)\n" + "Android 12 (API 31)\n"
        ),
        Message(
            "Colleague",
            "I think Kotlin is my favorite programming language.\n" + "It's so much fun!"
        ),
        Message(
            "Colleague", "Searching for alternatives to XML layouts..."
        ),
        Message(
            "Colleague",
            "Hey, take a look at Jetpack Compose, it's great!\n" + "It's the Android's modern toolkit for building native UI." + "It simplifies and accelerates UI development on Android." + "Less code, powerful tools, and intuitive Kotlin APIs :)"
        ),
        Message(
            "Colleague", "It's available from API 21+ :)"
        ),
        Message(
            "Colleague",
            "Writing Kotlin for UI seems so natural, Compose where have you been all my life?"
        ),
        Message(
            "Colleague", "Android Studio next version's name is Arctic Fox"
        ),
        Message(
            "Colleague", "Android Studio Arctic Fox tooling for Compose is top notch ^_^"
        ),
        Message(
            "Colleague", "I didn't know you can now run the emulator directly from Android Studio"
        ),
        Message(
            "Colleague",
            "Compose Previews are great to check quickly how a composable layout looks like"
        ),
        Message(
            "Colleague", "Previews are also interactive after enabling the experimental setting"
        ),
        Message(
            "Colleague", "Have you tried writing build.gradle with KTS?"
        ),
    )
}