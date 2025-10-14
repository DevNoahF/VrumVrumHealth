document.getElementById("given_name").focus();
document.getElementById("submit_button").addEventListener("click", prepareToSend);

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
    const telephoneNum = document.getElementById("telephone_num");
    const email = document.getElementById("email");
    const password = document.getElementById("password");
    const birthday = document.getElementById("birthday");
    const street = document.getElementById("street");
    const houseNumber = document.getElementById("house_id");
    const complement = document.getElementById("complement");
    const neighborhood = document.getElementById("neighborhood");

	return [name, telephoneNum, email, password, birthday, street, houseNumber,  complement, neighborhood];
}

function prepareToSend(e) {
    const data = getData();

    if(!isInputValid(data[0].length, MaxSizeEnum.NAME)) {
        window.alert("name invalid");
        e.preventDefault();
    }

    if(isInputValid(data[1].length, MaxSizeEnum.CELLPHONE)) {
        window.alert("cellphone invalid");
        e.preventDefault();
    }

    if(isInputValid(data[2].length, MaxSizeEnum.EMAIL)) {
        window.alert("email invalid");
        e.preventDefault();
    }
	
    if(isInputValid(data[3].length, MaxSizeEnum.PASSWORD)) {
        window.alert("password invalid");
        e.preventDefault();
    }
    
    if(isInputValid(data[4].length, MaxSizeEnum.BIRTHDAY)) {
        window.alert("birthday invalid");
        e.preventDefault();
    }
    
    if(!isInputValid(data[5].length, MaxSizeEnum.STREET)) {
        window.alert("street identification invalid");
        e.preventDefault();
    }

    if(isInputValid(data[6].length, MaxSizeEnum.HOUSE_NUMBER)) {
        window.alert("house identification invalid");
        e.preventDefault();
    }

    if(isInputValid(data[7].length, MaxSizeEnum.COMPLEMENT)) {
        window.alert("complement invalid");
        e.preventDefault();
    }

    if(isInputValid(data[8].length, MaxSizeEnum.NEIGHBORHOOD)) {
        window.alert("neighborhood name is invalid")
        e.preventDefault();
    }
}