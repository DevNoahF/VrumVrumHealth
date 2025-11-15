/** @typedef {Object} Enum */

document.getElementById("given_name").focus();
document.getElementById("submit_button").addEventListener("click", prepareData);

/** @type {Enum} */
const MaxSizeEnum = Object.freeze({
	NAME: 100,
	EMAIL: 100,
	PASSWORD: 255,
	CELLPHONE: 20,
    BIRTHDAY: 10,
	REGISTRATION: 100,
    COMPLEMENT: 30,
	STREET: 100,
	NEIGHBORHOOD: 100,
	HOUSE_NUMBER: 10,
	CPF: 11,
	CEP: 9
});

/**
 * Returns an boolean representing if given length of an input and its
 * max size is valid
 * @param {Number} length - The length of an input field
 * @param {Number} maxSize - The max size of an input
 */
function isInputOverMaxSize(length, maxSize) {
	return (length <= maxSize);
}

/**
 * 
 * @param {Number} length - The input length
 * @returns {boolean} 
 */
function isInputEmpty(length) {
	return (length == 0);
}

/**
 * Returns an boolean representing if given length of an input and its
 * max size is valid
 * @param {Number} inputLength - The length of an input field
 * @param {Number} maxSize - The max size of an input
 */
function isInputValid(inputLength, maxSize) {
	return (!isInputOverMaxSize(inputLength, maxSize) && isInputEmpty(length));
}

/**
 * Returns the inputs in an array as raw HTML elements
 */
function getData() {
	const name = document.getElementById("given_name");
	const email = document.getElementById("email");
	const password = document.getElementById("password");
	const birthday = document.getElementById("birthday");
	const street = document.getElementById("street");
	const houseNumber = document.getElementById("house_id");
	const complement = document.getElementById("complement");
	const cep = document.getElementById("cep");

	return [name, email, password, birthday, street, houseNumber, complement, cep];
}

function prepareData(e) {
	const dataToSend = getData();

	if(isInputValid(dataToSend[0].length, MaxSizeEnum.NAME)) {
		window.alert("name invalid");
		e.preventDefault();
	}

	if(isInputValid(dataToSend[1].length, MaxSizeEnum.EMAIL)) {
		window.alert("emailinvalid");
		e.preventDefault();
	}

	if(isInputValid(dataToSend[2].length, MaxSizeEnum.PASSWORD)) {
		window.alert("password invalid");
		e.preventDefault();
	}

	if(isInputValid(dataToSend[3].length, MaxSizeEnum.BIRTHDAY)) {
		window.alert("birthday invalid");
		e.preventDefault();
	}

	if(isInputValid(dataToSend[4].length, MaxSizeEnum.STREET)) {
		window.alert("street name is invalid");
		e.preventDefault();
	}

	if(isInputValid(dataToSend[5].length, MaxSizeEnum.HOUSE_NUMBER)) {
		window.alert("house number invalid");
		e.preventDefault();
	}

	if(isInputValid(dataToSend[6].length, MaxSizeEnum.COMPLEMENT)) {
		window.alert("complement is invalid");
		e.preventDefault();
	}

	if(isInputValid(dataToSend[7].length, MaxSizeEnum.CEP)) {
		window.alert("CEP is invalid");
		e.preventDefault();
	}

	e.preventDefault();
}