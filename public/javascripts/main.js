function howToPlay() {
    fetch(`http://localhost:9000/howToPlay`, {
        method: "GET"
    }).then(res => {
        window.location.replace(res.url);
    });
}

function play() {
    fetch(`http://localhost:9000/wizard`, {
        method: "GET"
    }).then(res => {
        window.location.replace(res.url);
    });
}

function sendPlayerCount(amount) {
    fetch(`http://localhost:9000/playerCount?count=${amount}`, {
        method: "POST",
        body: ""
    }).then(res => {
        window.location.replace(res.url);

    });
}

function setName(name) {
    if (this.event.key === "Enter") {
        fetch(`http://localhost:9000/playerName?name=${name}`, {
            method: "POST",
            body: ""
        }).then(res => {
            if(res.redirected) {
                window.location.replace(res.url)
            } else {
                window.location.replace(`http://localhost:9000/playerName`);
            }
        });
    }
}

function playCard(idx) {
    fetch(`http://localhost:9000/playCard?idx=${idx}`, {
        method: "POST",
        body: "",
    }).then((res) => {
        window.location.replace(res.url);
    });
}

function setTricks(tricks) {
    if (this.event.key === "Enter") {
        fetch(`http://localhost:9000/setTrickAmount?amount=${tricks}`, {
            method: "POST",
            body: "",
        }).then((res) => {
            if (res.redirected) {
                window.location.replace(res.url);
            } else {
                window.location.replace(`http://localhost:9000/setTrickAmount`);
            }
        });
    }
}

function proceed() {
    window.location.replace(`http://localhost:9000/setTrickAmount`)
}

function setTrump(color) {
    fetch(`http://localhost:9000/trump?color=${color}`, {
        method: "POST",
        body: ""
    }).then(res => {
        window.location.replace(res.url)
    });
}