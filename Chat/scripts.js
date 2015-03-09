function run(){
	var appContainer = document.getElementsByClassName("chat")[0];
	appContainer.addEventListener("click", delegateEvent);
}

function delegateEvent(evtObj) {
	if(evtObj.type === "click" && evtObj.target.id.contains("sendBtn")) {
		onSendButtonClick(evtObj);
	}
	if(evtObj.type == "click" && evtObj.target.id.contains("changeNameBtn")) {
		onChangeNameButtonClick(evtObj);
	}
	if(evtObj.type == "click" && evtObj.target.id.contains("iconDelete")) {
		onIconDeleteClick(evtObj);
	}
	if(evtObj.type == "click" && evtObj.target.id.contains("iconEdit")) {
		onIconEditClick(evtObj);
	}
}
function onIconDeleteClick(evtObj) {
	var conf = confirm("Confirm removal, please :)");
	if (conf == true)
	{
		evtObj.target.parentNode.parentNode.removeChild(evtObj.target.parentNode);
	}
}
var messageToEdit;
function onIconEditClick(evtObj) {
	var message = evtObj.target.parentNode;
	var messageArea = document.getElementById("messageText");
	messageArea.value = message.getElementsByTagName("div")[0].getElementsByTagName("span")[1].innerHTML;
	var sendButton = document.getElementById("sendBtn");
	sendBtn.innerHTML = "Edit";
	messageToEdit = message;
}
function onSendButtonClick(){
	if (document.getElementById("changeNameBtn").innerHTML == "Enter name" || document.getElementById("name").value == "")
		alert("Enter your name, please :)");
	else if (document.getElementById("messageText").value == "")
		alert("Your message is empty! :(");
	else if (document.getElementById("sendBtn").innerHTML != "Edit") {
		var text = document.getElementById("messageText");
		addMessage(text.value);
		text.value = "";
	}
	else {
		document.getElementById("sendBtn").innerHTML = "Send";
		var text = document.getElementById("messageText").value;
		document.getElementById("messageText").value = "";
		messageToEdit.getElementsByTagName("div")[0].getElementsByTagName("span")[1].innerHTML = text;
	}
}
function onChangeNameButtonClick() {
	var nameText = document.getElementById("enterName");
	document.getElementById("changeNameBtn").innerHTML = "Change name";
	document.getElementById("name").innerHTML = nameText.value;
	nameText.value = "";
}
function addMessage(text){
	var divItem = document.createElement("div");
	var divText = document.createElement("div");
	var spanName = document.createElement("span");
	var spanText = document.createElement("span");
	var deleteImg = document.createElement("img");
	var editImg = document.createElement("img");
	divItem.classList.add("userMessage");
	spanName.classList.add("userName");
	deleteImg.id =  "iconDelete";
	deleteImg.setAttribute("src", "delete.png");
	deleteImg.setAttribute("alt", "Delete message");
	editImg.id = "iconEdit";
	editImg.setAttribute("src", "edit.png");
	editImg.setAttribute("alt", "Edit message");
	spanName.innerHTML = document.getElementById("name").value + ":&nbsp";
	spanText.innerHTML = text;
	divText.appendChild(spanName);
	divText.appendChild(spanText);
	divItem.appendChild(divText);
	divItem.appendChild(deleteImg);
	divItem.appendChild(editImg);
	var items = document.getElementsByClassName("messageArea")[0];
	items.appendChild(divItem);
}