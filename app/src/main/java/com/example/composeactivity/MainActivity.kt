package com.example.composeactivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composeactivity.ui.theme.ComposeActivityTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeActivityTheme() {
                Conversation(getMessageList())
            }
        }
    }
}

data class Message(val author: String, val body: String)

@Composable
fun MessageCard(msg: Message) {
    Row(modifier = Modifier.padding(all = 8.dp)) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "Contact prifile picture",
            modifier = Modifier
                .size(40.dp) // Set image size to 40 dp
                .clip(CircleShape) // Clip image to be shaped as a circle
                .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
        )

        Spacer(modifier = Modifier.width(8.dp))

        //We keep track if the message is expanded or not in this
        var isExpanded by remember { mutableStateOf(false) }

        //surfaceColor will be updated gradually from one color to the other
        val surfaceColor by animateColorAsState(
            if (isExpanded) MaterialTheme.colors.primary else MaterialTheme.colors.surface,
        )

        Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) {
            Text(
                text = msg.author,
                color = MaterialTheme.colors.secondaryVariant,
                style = MaterialTheme.typography.subtitle2
            )
            Spacer(modifier = Modifier.height(4.dp))

            Surface(
                shape = MaterialTheme.shapes.medium,
                elevation = 1.dp,
                color = surfaceColor,
                modifier = Modifier.animateContentSize().padding(1.dp)

            ) {

                Text(
                    text = msg.body,
                    modifier = Modifier.padding(all = 4.dp),
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                    style = MaterialTheme.typography.body2
                )
            }
        }
    }
}

@Composable
fun Conversation(messages: List<Message>) {
    LazyColumn() { //화면에 표시되는 요소만 렌더링하므로 긴 목록에 매우 효율적 + LazyRow
        items(messages) { message ->
            MessageCard(message)
        }
    }
}


//@Preview
//@Preview(
//    uiMode = Configuration.UI_MODE_NIGHT_YES,
//    showBackground = true,
//    name = "Dark Mode"
//)
//@Composable
//fun PreviewMessageCard() {
//    ComposeActivityTheme() {
//        MessageCard(Message("Colleague", "Hey, take a look at Jetpack Compose, it's great!"))
//    }
//}

@Preview
@Composable
fun PreviewConversation() {
    ComposeActivityTheme() {
        Conversation(getMessageList())
    }
}

fun getMessageList(): List<Message> {
    val mutable = mutableListOf<Message>()
    for (i in 0..10) {
        mutable.add(Message("Colleague", "Hey, take a look at Jetpack Compose, it's great! Hey, take a look at Jetpack Compose, it's great!Hey, take a look at Jetpack Compose, it's great!Hey, take a look at Jetpack Compose, it's great!Hey, take a look at Jetpack Compose, it's great!Hey, take a look at Jetpack Compose, it's great!Hey, take a look at Jetpack Compose, it's great! $i"))
    }
    return mutable
}