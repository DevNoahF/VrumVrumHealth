document.getElementById("given_name").focus();
document.getElementById("submit_button").addEventListener("click", prepareToSend);

const MaxSizeEnum = Object.freeze({
	NAME: 100,
	EMAIL: 100,
	PASSWORD: 255,
	CELLPHONE: 20,
	REGISTRATION: 100,
	STREET: 100,
	NEIGHBORHOOD: 100,
	HOUSE_NUMBER: 10,
	CPF: 11,
	CEP: 9
});

function isInputOverMaxSize(length, maxSize) {
	return (length <= maxSize);
}

function isInputEmpty(length) {
	return (length == 0);
}

/**
 * Returns an boolean representing if given length of an input and its
 * max size is valid
 * @param {Number} inputLength - The length of an input field
 * @param {Number} fieldMaxSize - The max size of an input
 */
function isInputValid(inputLength, maxSize) {
	return (!isInputOverMaxSize(inputLength, maxSize) && isInputEmpty(length));
}

/**
 * Returns the inputs in an array as raw HTML elements
 */
function getData() {
	const name = document.getElementById("given_name");
	const cellphone = document.getElementById("telephone_num");
	const email = document.getElementById("email");
	const password = document.getElementById("password");
	const registration = document.getElementById("registration");
	const role = document.querySelector('input[name="radio-role"]:checked');

	return [name, email, cellphone, email, password, registration, role];
}

function prepareToSend(e) {
	const dataToSend = getData();

	if(isInputValid(dataToSend[0].length, MaxSizeEnum.NAME)) {
		window.alert("name invalid");
		e.preventDefault();
	}

	if(isInputValid(dataToSend[1].length, MaxSizeEnum.EMAIL)) {
		window.alert("email invalid");
		e.preventDefault();
	}

	if(isInputValid(dataToSend[2].length, MaxSizeEnum.CELLPHONE)) {
		window.alert("phone invalid");
		e.preventDefault();
	}

	if(isInputValid(dataToSend[3].length, MaxSizeEnum.EMAIL)) {
		window.alert("email invalid");
		e.preventDefault();
	}

	if(isInputValid(dataToSend[4].length, MaxSizeEnum.PASSWORD)) {
		window.alert("email invalid");
		e.preventDefault();
	}

	if(isInputValid(dataToSend[5].length, MaxSizeEnum.REGISTRATION)) {
		window.alert("registration invalid");
		e.preventDefault();
	}

	e.preventDefault();
}
