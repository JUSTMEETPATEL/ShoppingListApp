package com.example.navigation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

data class ShoppingList(
    val id:Int,
    var name:String,
    var quantity:Int,
    val isEditing:Boolean = false
)

@Composable
fun MainScreen(navController: NavHostController) {
    var sItems by remember { mutableStateOf(listOf<ShoppingList>()) }
    var displayDialog by remember { mutableStateOf(false) }
    var itemName by remember { mutableStateOf("") }
    var itemQuantity by remember { mutableStateOf("") }
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    )
    {
        Button(onClick = { navController.navigate("preview") }) {
            Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = null)
        }


        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.fillMaxSize()
        ) {
            Image(painter = painterResource(id = R.drawable.meet), contentDescription = null,contentScale = ContentScale.Crop, modifier = Modifier
                .size(60.dp)
                .clip(CircleShape))
            Text(text = "Welcome")
            Spacer(modifier = Modifier.padding(5.dp))
            Button(onClick = { displayDialog = true }) {
                Text(text = "Add Items")
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp)
            ) {
                items(sItems){
                    item ->
                    if(item.isEditing){
                        ShoppingListEditor(item = item, onEditComplete = {
                            editName,editQuantity ->
                            sItems = sItems.map{ it.copy(isEditing = false)}
                            val editedItem = sItems.find{ it.id == item.id }
                            editedItem?.let {
                                it.name = editName
                                it.quantity = editQuantity
                            }
                        })
                    }else{
                        ShoppingListItem(item = item, onEditClick = {
                            sItems = sItems.map { it.copy(isEditing = it.id == item.id) }
                        },
                            onDeleteClick = {
                            sItems = sItems-item
                        })
                    }
                }
            }
        }
    }

    if(displayDialog){
        AlertDialog(
            onDismissRequest = { displayDialog = false},
            confirmButton = {
                            Row(modifier = Modifier
                                .fillMaxWidth()
                                .padding(7.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                                Button(onClick = { if(itemName.isNotBlank() && itemQuantity.isNotBlank()){
                                    val newItem = ShoppingList(id = sItems.size + 1,name = itemName, quantity = itemQuantity.toIntOrNull()?:1)
                                    sItems = sItems + newItem
                                    displayDialog = false
                                    itemName = ""
                                    itemQuantity = ""
                                }
                                }) {
                                    Text(text = "Add")
                                }
                                Button(onClick = { displayDialog = false}) {
                                    Text(text = "Cancel")
                                }
                            }
            },
            title = { Text(text = "Add Shopping items") },
            text = {
                Column {
                    OutlinedTextField(value = itemName, onValueChange = { itemName = it }, singleLine = true, modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp))
                    OutlinedTextField(value = itemQuantity, onValueChange = { itemQuantity = it }, singleLine = true, modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp))
                }
            }
            )
    }
}

@Composable
fun ShoppingListItem(item:ShoppingList,onEditClick:()-> Unit,onDeleteClick:()->Unit){
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .border(border = BorderStroke(2.dp, Color.Black), shape = RoundedCornerShape(20)
            )
    ) {
        Text(text = item.name , modifier = Modifier.padding(8.dp))
        Text(text = "Qty: ${ item.quantity }",modifier = Modifier.padding(8.dp))
        Row(modifier = Modifier.padding(8.dp)) {
            IconButton(onClick = onEditClick) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit" )
            }
            IconButton(onClick = onDeleteClick) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Edit" )
            }
        }
    }
}

@Composable
fun ShoppingListEditor(item: ShoppingList, onEditComplete:(String,Int)-> Unit){
    var editedName by remember { mutableStateOf(item.name) }
    var editedQuantity by remember { mutableStateOf(item.quantity.toString()) }
    var isEditing by remember { mutableStateOf(item.isEditing) }

    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
        Column {
            BasicTextField(value = editedName, onValueChange = { editedName = it }, singleLine = true, modifier = Modifier
                .wrapContentSize()
                .padding(8.dp))
            BasicTextField(value = editedQuantity, onValueChange = { editedQuantity = it }, singleLine = true, modifier = Modifier
                .wrapContentSize()
                .padding(8.dp))
        }
        Button(onClick = {
            isEditing = false
            onEditComplete(editedName, editedQuantity.toIntOrNull() ?: 1)
        }) {
            Text(text = "Save")
        }

    }

}