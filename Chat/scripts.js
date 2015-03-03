function run(){
	var appContainer = document.getElementsByClassName('userArea')[0];

	appContainer.addEventListener('click', delegateEvent);
}

function delegateEvent(evtObj) {
	if(evtObj.type === 'click' && evtObj.target.classList.contains('button')){
		onSendButtonClick(evtObj);
	}
}
function onSendButtonClick(){
	var text = document.getElementById('text');
	addMessage(text.value);
	text.value = '';
}
function addMessage(text){
	var divItem = document.createElement('userMessage');

	divItem.appendChild(document.createTextNode(text));
	var items = document.getElementsByClassName('messageArea')[0];
	items.appendChild(divItem);
	return divItem;
}