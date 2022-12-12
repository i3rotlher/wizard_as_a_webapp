$(document).ready(function() {
    renderVue()
})

function getCardPath(cardNum, cardCol) {
    var result = "http://localhost:9000/assets//images/card-images/";
    if (cardNum === 0) {
        result += cardCol
        return result + "-fool.png"
    }
    if (cardNum === 14) {
        result += cardCol
        return result + "-wizard.png"
    }
    result += cardCol
    return result + cardNum + ".png"
}

function renderVue()  {
    const PlayerAmount = {
        data() {
            return {
                amounts: [
                    { text: 3 },
                    { text: 4 },
                    { text: 5 },
                    { text: 6 }
                ]
            }
        }
    }

    const amount_app = Vue.createApp(PlayerAmount)
    amount_app.component('player-amount-btn', {
        props: ['title'],
        template: `<button type="button" class="pla">{{title}}</button>`
    })
    amount_app.mount('#player-amount-container')


    const player_name = Vue.createApp()
    player_name.component('player-name-input', {
        props: ['player'],
        template: `<div class="hor">
        <div class="container h-100 playerName">
            <h2 id="playerNameText" value="{{player}}">Player {{player}} please insert your name:</h2>
            <input class="form-control in" type="text" placeholder="your name ..." id="name" style="width:450px">
        </div>
    </div>`
    })
    player_name.mount('#player-name-input-container')


    const top_bar = Vue.createApp()
    top_bar.component('top-bar', {
        props: ['player', 'phase'],
        template: ` <div class="row">
            <div class="col-2">
                <h1 id="playerNameTopLeft">{{player}}'s Turn</h1>
            </div>
            <div class="col-8">
                <h1 class="actionDescription">Phase: {{phase}}</h1>
            </div>
            <div class="col-2">
                    <!-- Button trigger modal -->
                <button type="button" class="showScoreBoardBtn btn btn-secondary" data-bs-toggle="modal" data-bs-target="#scoreBoardModal">
                    <i class="bi bi-table"></i><br>
                    Score
                </button>
            </div>
        </div>`
    })
    top_bar.mount('#top-bar-container')


    const player_cards = Vue.createApp()
    player_cards.component('top-bar', {
        props: ['player', 'phase'],
        template: ` <div class="row">
            <div class="col-2">
                <h1 id="playerNameTopLeft">{{player}}'s Turn</h1>
            </div>
            <div class="col-8">
                <h1 class="actionDescription">Phase: {{phase}}</h1>
            </div>
            <div class="col-2">
                    <!-- Button trigger modal -->
                <button type="button" class="showScoreBoardBtn btn btn-secondary" data-bs-toggle="modal" data-bs-target="#scoreBoardModal">
                    <i class="bi bi-table"></i><br>
                    Score
                </button>
            </div>
        </div>`
    })
    player_cards.mount('#top-bar-container')




    const guessView_app = Vue.createApp()
    guessView_app.component('guess-row', {
        props: ['path'],
        template: ` <div class="trickInsert col-8">
                <h2 style="text-align: start">How many tricks are you going to make?</h2>
                <input
                class="form-control"
                type="number"
                id="tricks"
                value="0"
                min="0"
                    style="width: fit-content"
                />
            </div>
            <div class="trumpCard col-4">
                <h2>Trump Card:</h2>
                <img class="playingCard trump" :src="path"
                />
            </div>`
    })

    guessView_app.component('cards-row', {
        props: ['cards'],
        template: `<div class="col-12">
                <div class="playCardBackground">
                    <div class="animated-card-hand-container">
                        <div id="playerHandCardsTrick" class="animated-card-hand">            
                            <div class="animated-card" v-for="card in cards.cards">
                                <img class="playingCard"
                                :src="card" />
                            </div>                       
                        </div>
                    </div>
                </div>
            </div>`
    })
    guessView_app.mount('#guess-row-container')










    const playView_app = Vue.createApp()
    playView_app.component('played-row', {
        props: ['path', 'cards'],
        template: `<div class="playedCards col-4">
            <h2>Played Cards:</h2>
                <div class="animated-card-hand-container">
                    <div id="playedCardsStack" class="animated-card-hand-played">
                        <div class="animated-card" v-for="card in cards.cards">
                            <img class="playingCard" :src="card" />
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-4"></div>
            <div class="trumpCard col-4">
                <h2>Trump Card:</h2>
                <img class="playingCard trump"
                :src="path"
                />
            </div>`
    })

    playView_app.component('cards-row', {
        props: ['cards'],
        template: `<div class="col-12">
                <div class="playCardBackground">
                    <div class="animated-card-hand-container">
                        <div id="playerHandCards" class="animated-card-hand">            
                            <div class="animated-card" v-for="card in cards.cards">
                                <img class="playingCard"
                                :src="card" />
                            </div>                       
                        </div>
                    </div>
                </div>
            </div>`
    })
    playView_app.mount('#play-row-container')

    addEventListeners()
}