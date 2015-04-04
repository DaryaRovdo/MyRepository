var uniqueId = function() {
	var date = Date.now();
	var random = Math.random() * Math.random();

	return Math.floor(date * random).toString();
};

var theMessage = function(name, text, userFlag) {
	return {
		textMessage: text,
		nameMessage: name,
		user: userFlag,
		id: uniqueId()
	};
};

var messageList = [];

function run(){
	var appContainer = document.getElementsByClassName("chat")[0];
	appContainer.addEventListener("click", delegateEvent);
	appContainer.addEventListener("keydown", delegateEvent);
	var allMessages = restore("chat messages") || [ theMessage("Anton", "Hello!", false),
			theMessage("Darya", "Hi, dude!",true),
			theMessage("Anton", "How are you?", false)
		];
	createAllMessages(allMessages);
	var name = restore("chat username") || "";
	setName(name);
	if (name != "")
		document.getElementById("changeNameBtn").innerHTML = "Change name";
}

function createAllMessages(allMessages) {
	for(var i = 0; i < allMessages.length; i++)
		addMessage(allMessages[i]);
}

function setName(name) {
	document.getElementById("name").innerHTML = name;
}

function delegateEvent(evtObj) {
	if(evtObj.type == "click" &&  evtObj.target.getAttribute("id") == "sendBtn") {
		onSendButtonClick(evtObj);
	}
	if(evtObj.type == "click" && evtObj.target.getAttribute("id") == "changeNameBtn") {
		onChangeNameButtonClick(evtObj);
	}
	if(evtObj.type == "click" && evtObj.target.getAttribute("id") == "iconDelete") {
		onIconDeleteClick(evtObj);
	}
	if(evtObj.type == "click" && evtObj.target.getAttribute("id") == "iconEdit") {
		onIconEditClick(evtObj);
	}
	if(evtObj.type == "keydown" && evtObj.keyCode == 13 && document.activeElement.id == "enterName") {
		onChangeNameButtonClick(evtObj);
	}
	if(evtObj.type == "keydown" && evtObj.shiftKey == false && evtObj.keyCode == 13 && document.activeElement.id == "messageText") {
		evtObj.preventDefault();
		onSendButtonClick(evtObj);
	}
}

function onIconDeleteClick(evtObj) {
	var conf = confirm("Confirm removal, please :)");
	if (conf == true)
	{
		var message = evtObj.target.parentNode;
		message.parentNode.removeChild(message);
		var i;
		for (i = 0; i < messageList.length; i++)
			if (messageList[i].id == message.id)
				break;
		messageList.splice(i, 1);
		store("chat messages", messageList);
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
	if (document.getElementById("name").value == "")
		alert("Enter your name, please :)");
	else if (document.getElementById("messageText").value == "")
		alert("Your message is empty! :(");
	else if (document.getElementById("sendBtn").innerHTML != "Edit") {
		addMessage(theMessage(document.getElementById("name").value, document.getElementById("messageText").value, true));
		document.getElementById("messageText").value = "";
		store("chat messages", messageList);
	}
	else {
		document.getElementById("sendBtn").innerHTML = "Send";
		var text = document.getElementById("messageText").value;
		document.getElementById("messageText").value = "";
		messageToEdit.getElementsByTagName("div")[0].getElementsByTagName("span")[1].innerHTML = text;
		var i;
		for (i = 0; i < messageList.length; i++)
			if (messageList[i].id == messageToEdit.id)
				break;
		messageList[i].textMessage = text;
		store("chat messages", messageList);
	}
}

function onChangeNameButtonClick() {
	var name = document.getElementById("enterName")
	if (name != "")
		document.getElementById("changeNameBtn").innerHTML = "Change name";
	setName(name.value);
	store("chat username", name.value);
	name.value = "";
}

function addMessage(message){
	if (message.user == true) {
		var divItem = document.createElement("div");
		var divText = document.createElement("div");
		var spanName = document.createElement("span");
		var spanText = document.createElement("span");
		var deleteImg = document.createElement("img");
		var editImg = document.createElement("img");
		divItem.classList.add("userMessage");
		divItem.id = message.id;
		spanName.classList.add("userName");
		deleteImg.id =  "iconDelete";
		deleteImg.setAttribute("src", "images/delete.png");
		deleteImg.setAttribute("alt", "Delete message");
		editImg.id = "iconEdit";
		editImg.setAttribute("src", "images/edit.png");
		editImg.setAttribute("alt", "Edit message");
		spanName.innerHTML = message.nameMessage + ":&nbsp";
		spanText.innerHTML = message.textMessage;
		divText.appendChild(spanName);
		divText.appendChild(spanText);
		divItem.appendChild(divText);
		divItem.appendChild(deleteImg);
		divItem.appendChild(editImg);
	}
	else {
		var divItem = document.createElement("div");
		var spanName = document.createElement("span");
		var spanText = document.createElement("span");
		divItem.classList.add("friendMessage");
		divItem.id = message.id;
		spanName.classList.add("friendName");
		spanName.innerHTML = message.nameMessage + ":&nbsp";
		spanText.innerHTML = message.textMessage;
		divItem.appendChild(spanName);
		divItem.appendChild(spanText);
	}
	var items = document.getElementsByClassName("messageArea")[0];
	items.appendChild(divItem);
	messageList.push(message);
}

function store(lsName, toSave) {
	if(typeof(Storage) == "undefined") {
		alert("localStorage is not accessible!");
		return;
	}
	localStorage.setItem(lsName, JSON.stringify(toSave));
}

function restore(lsName) {
	if(typeof(Storage) == "undefined") {
		alert("localStorage is not accessible");
		return;
	}
	var item = localStorage.getItem(lsName);
	return item && JSON.parse(item);
}