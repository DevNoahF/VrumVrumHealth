function formatTime(date) {
	// 2025-10-20T19:17:36.115Z
	return `${date.getHours()}:${date.getMinutes()}:${date.getSeconds()}`;
}

function formatDate(date) {
    return `${date.getDate()}/${date.getMonth() + 1}/${date.getFullYear()}`;
}

function setOperatorName() {
    const namePlace = document.getElementsByClassName("dynamic_information")[0];
    namePlace.innerText = "john doe";
}

function setCurrentDate() {
    const date = new Date();
    const datePlace = document.getElementsByClassName("dynamic_information")[1];
    const formatedTime = formatTime(date);
    const formatedDate = formatDate(date);
			
    datePlace.innerText = `Horas atuais: ${formatedTime}, ${formatedDate}`;
}

function setContent() {
    setOperatorName();
    setCurrentDate();
}

setContent();