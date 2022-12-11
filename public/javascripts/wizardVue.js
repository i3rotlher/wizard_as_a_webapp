$(document).ready(function() {

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
    addEventListeners()
})