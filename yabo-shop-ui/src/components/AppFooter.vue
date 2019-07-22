<template>
  <footer>
    <span class="footer-text">
      © 2019 Creative team, all rights reserved. <a href="http://yabo.uz">yabo.uz</a>
    </span>
  </footer>
</template>

<style scoped>
footer {
  display: flex;
  flex-direction: column-reverse;
  /*background: #9dee00;*/
  width: 100%;
  /*height: -webkit-fill-available;*/
}

.footer-text{
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 10px;
  background: #1d1f36;
  color: white;
  text-align: center;
  letter-spacing: 0.03em;
  margin-top: 18px;
  width: 100%;
  height: 50px;
}

a,
a:visited,
a:active {
  color: white;
  font-weight: bold;
  text-decoration: none;
  padding-left: 6px;
}
</style>