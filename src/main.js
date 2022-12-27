
import Vue from "vue";
import wrap from "@vue/web-component-wrapper";

import App from "./App.vue";

const wrappedElement = wrap(Vue, App);

window.customElements.define("v-card", wrappedElement);