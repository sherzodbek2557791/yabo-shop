module.exports = {
  lintOnSave: true,
  devServer: {
    port: 8080,
    proxy: {
      "/api": {
        target: "http://localhost:8095"
      }
    }
  }
};