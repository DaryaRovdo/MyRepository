function run(){
	var appContainer = document.getElementsByClassName("chat")[0];
	appContainer.addEventListener("click", delegateEvent);
}

function delegateEvent(evtObj) {
	if(evtObj.type === "click" && evtObj.target.id.contains("send")) {
		onSendButtonClick(evtObj);
	}
	if(evtObj.type == "click" && evtObj.target.id.contains("change")) {
		onChangeButtonClick(evtObj);
	}
	if(evtObj.type == "click" && evtObj.target.id.contains("iconDelete")) {
		onDeleteClick(evtObj);
	}
	if(evtObj.type == "click" && evtObj.target.id.contains("iconEdit")) {
		onEditClick(evtObj);
	}
}
function onDeleteClick() {
	alert("it works!");
}
function onEditClick() {
	alert("it works!");
}
function onSendButtonClick(){
	if (document.getElementById("change").innerHTML == "Enter name" || document.getElementById("name").value == "")
		alert("Enter your name, please :)");
	else if (document.getElementById("text").value == "")
		alert("Your message is empty! :(");
	else {
		var text = document.getElementById("text");
		addMessage(text.value);
		text.value = "";
	}
}
function onChangeButtonClick() {
	var nameText = document.getElementById("enterName");
	document.getElementById("change").innerHTML = "Change name";
	document.getElementById("name").innerHTML = nameText.value;
	nameText.value = "";
}
function addMessage(text){
	var divItem = document.createElement("div");
	var divText = document.createElement("div");
	var spanItem = document.createElement("span");
	var deleteImg = document.createElement("img");
	var editImg = document.createElement("img");
	divItem.classList.add("userMessage");
	spanItem.classList.add("userName");
	deleteImg.id =  "iconDelete";
	deleteImg.setAttribute("src", "delete.png");
	deleteImg.setAttribute("alt", "Delete message");
	editImg.id = "iconEdit";
	editImg.setAttribute("src", "edit.png");
	editImg.setAttribute("alt", "Edit message");
	spanItem.innerHTML = document.getElementById("name").value + ":&nbsp";
	divText.appendChild(spanItem);
	divText.innerHTML += text;
	divItem.appendChild(divText);
	divItem.appendChild(deleteImg);
	divItem.appendChild(editImg);
	var items = document.getElementsByClassName("messageArea")[0];
	items.appendChild(divItem);
}