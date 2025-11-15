function setWarningsAndAnnouncements() {
    const placeToWarn = document.getElementsByClassName("log_content")[0];

    /* TODO: logic to get data from database */

    for(let i = 0; i <= 10; ++i) {
        placeToWarn.innerHTML += "<li class='warning announcement'>DD/MM: lorem ipsum</li>";
    }
}

function setSolicitations() {
    const placeToSolicite = document.getElementsByClassName("solicitation_content");

    /* TODO: logic to get data from the database */

    for(let i = 0; i < placeToSolicite.length; ++i) {
        placeToSolicite[i].innerHTML += "XXX"
    }
}

function setLogsContent() {
    setWarningsAndAnnouncements();
    setSolicitations();
}

setLogsContent();