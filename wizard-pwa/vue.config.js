const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  pwa: {
    name: "Awesome Wizard PWA",
    themeColor: "#debd4f"
  }
})
