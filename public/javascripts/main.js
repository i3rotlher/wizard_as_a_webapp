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